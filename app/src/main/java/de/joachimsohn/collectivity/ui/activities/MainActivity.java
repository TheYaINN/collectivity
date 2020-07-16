package de.joachimsohn.collectivity.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.Logger;
import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;
import de.joachimsohn.collectivity.ui.CollectionAdapter;
import de.joachimsohn.collectivity.ui.Marker;

public class MainActivity extends AppCompatActivity {

    /* ====== UI ====== */
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter collectionAdapter;
    private RecyclerView.LayoutManager collectionLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Log.INFO, Marker.MAIN, "Starting App");

        //Setting up the actionbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //Load main view
        recyclerView = findViewById(R.id.collections_recycler_view);
        recyclerView.setHasFixedSize(true);
        collectionLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(collectionLayoutManager);

        collectionAdapter = new CollectionAdapter(CollectionManager.getManager().getCollection());
        recyclerView.setAdapter(collectionAdapter);



        //TODO: load everything from DB CollectionManager.getManager().setCollection(DataBaseConnector.getInstance().loadAndGetCollection(getApplication()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        Logger.log(Log.INFO, Marker.MAIN, "Closing App");
        CollectionManager.getManager().saveUserState();
        super.onDestroy();
    }

}
