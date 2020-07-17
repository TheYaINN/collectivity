package de.joachimsohn.collectivity.ui.activities;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.Logger;
import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.CollectionAdapter;
import de.joachimsohn.collectivity.ui.CollectionViewModel;
import de.joachimsohn.collectivity.ui.Marker;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollectionAdapter adapter;
    private SubMenu subMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Log.INFO, Marker.MAIN, "Starting App");
        DataBaseConnector.getInstance().init(getApplication());
        initGUI();
    }

    private void initGUI() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collection);
        toolbar = findViewById(R.id.toolbar);
        setUpActionBar();
        recyclerView = findViewById(R.id.collections_recycler_view);
        setUpRecyclerView();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        CollectionViewModel collectionViewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);
        collectionViewModel.getAllCollections().observe(this, adapter::setData);
        adapter = new CollectionAdapter(new ArrayList<>());
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
        Logger.log(Log.INFO, Marker.MAIN, "Closing App");
        //Save data
        super.onDestroy();
    }

    public void addCollection(View view) {
        //TODO: change to collection_create
        System.out.println("TEST");
    }
}
