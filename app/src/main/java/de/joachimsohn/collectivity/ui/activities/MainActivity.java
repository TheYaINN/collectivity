package de.joachimsohn.collectivity.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.Marker;
import de.joachimsohn.collectivity.ui.activities.search.SearchActivity;
import de.joachimsohn.collectivity.ui.adapter.CollectionAdapter;
import de.joachimsohn.collectivity.ui.adapter.ItemAdapter;
import de.joachimsohn.collectivity.ui.adapter.StorageLocationAdapter;
import de.joachimsohn.collectivity.util.logging.Logger;
import de.joachimsohn.collectivity.util.logging.Priority;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private SubMenu subMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Priority.DEBUG, Marker.MAIN, "Starting App");
        DataBaseConnector.getInstance().init(getApplication());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_overview);

        //Setting up the UI and binding the data from the CollectionManager to the Recyclerview
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview_wide);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        loadViewItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_search_sort, menu);
        subMenu = menu.getItem(1).getSubMenu();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            case R.id.home:
                CacheManager.getManager().setLevel(CacheManager.Direction.UP);
                loadViewItems();
                return true;
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra(SearchType.EXTRA, SearchType.COLLECTION);
                startActivity(intent);
                return true;
            case R.id.action_dropdown_name:
                SortDirection directionName = SortManager.getManager().sortCollectionsBy(SortCriteria.NAME);
                subMenu.getItem(0).setIcon(directionName.getIcon());
                return true;
            case R.id.action_dropdown_description:
                SortDirection directionDescription = SortManager.getManager().sortCollectionsBy(SortCriteria.DESCRIPTION);
                subMenu.getItem(1).setIcon(directionDescription.getIcon());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadViewItems() {
        switch (CacheManager.getManager().getCurrentCacheLevel()) {
            case COLLECTION:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                CacheManager.getManager().setCollections(DataBaseConnector.getInstance().getAllCollections());
                adapter = new CollectionAdapter(this);
                CacheManager.getManager().getCollections().observe(this, ((CollectionAdapter) adapter)::setData);
                recyclerView.setAdapter((RecyclerView.Adapter) adapter);
                break;
            case STORAGELOCATION:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                CacheManager.getManager().setStorageLocations(DataBaseConnector.getInstance().getAllStorageLocations());
                adapter = new StorageLocationAdapter(this);
                CacheManager.getManager().getStorageLocations().observe(this, ((StorageLocationAdapter) adapter)::setData);
                recyclerView.setAdapter((RecyclerView.Adapter) adapter);
                break;
            case ITEM:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                CacheManager.getManager().setItems(DataBaseConnector.getInstance().getAllItems());
                adapter = new StorageLocationAdapter(this);
                CacheManager.getManager().getItems().observe(this, ((ItemAdapter) adapter)::setData);
                recyclerView.setAdapter((RecyclerView.Adapter) adapter);
                break;
        }
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    protected void onDestroy() {
        Logger.log(Priority.DEBUG, Marker.MAIN, "Closing App");
        super.onDestroy();
    }
}
