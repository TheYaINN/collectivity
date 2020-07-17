package de.joachimsohn.collectivity.ui.activities.collection;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.util.logging.Logger;
import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.Marker;
import de.joachimsohn.collectivity.util.logging.Priority;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollectionAdapter adapter;
    private SubMenu subMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Priority.DEBUG, Marker.MAIN, "Starting App");
        DataBaseConnector.getInstance().init(getApplication());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collection_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.collections_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CollectionAdapter(this);
        CollectionViewModel collectionViewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);
        collectionViewModel.getAllCollections().observe(this, adapter::setData);
        recyclerView.setAdapter(adapter);
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
