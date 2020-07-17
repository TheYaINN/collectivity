package de.joachimsohn.collectivity.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Timer;

import de.joachimsohn.collectivity.Logger;
import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.loader.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.CollectionAdapter;
import de.joachimsohn.collectivity.ui.Marker;

public class MainActivity extends AppCompatActivity {

    /* ====== UI ====== */
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter collectionAdapter;
    private RecyclerView.LayoutManager collectionLayoutManager;
    private SubMenu subMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Log.INFO, Marker.MAIN, "Starting App");
        //Load DB, this is an async task
        CollectionManager.getManager().setCollection(DataBaseConnector.getInstance().loadAndGetCollection(getApplication()));
        //setup UI
        initGUI();
    }

    private void initGUI() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.collection);


        //Setting up the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Load Recycler view and fill it with data from DB
        recyclerView = findViewById(R.id.collections_recycler_view);
        recyclerView.setHasFixedSize(true);
        collectionLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(collectionLayoutManager);

        collectionAdapter = new CollectionAdapter(CollectionManager.getManager().getCollection());
        recyclerView.setAdapter(collectionAdapter);

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
        try {
            wait(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onOptionsMenuClosed(menu);
    }

    @Override
    protected void onDestroy() {
        Logger.log(Log.INFO, Marker.MAIN, "Closing App");
        CollectionManager.getManager().saveUserState();
        super.onDestroy();
    }

    public void addCollection(View view) {
        System.out.println("TEST");
    }
}
