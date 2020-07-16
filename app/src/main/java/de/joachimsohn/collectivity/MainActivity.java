package de.joachimsohn.collectivity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.joachimsohn.collectivity.loader.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;
import de.joachimsohn.collectivity.ui.Marker;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    private void initGUI() {
        toolbar = findViewById(R.id.toolbar);
        //TODO: setContentView(R.layout.collection_view);
        setContentView(R.layout.collection_view);
        //setSupportActionBar(toolbar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log(Log.INFO, Marker.MAIN, "Starting App");
        initGUI();
        CollectionManager.getManager().setCollection(DataBaseConnector.getInstance().loadAndGetCollection(getApplication()));
    }

    @Override
    protected void onDestroy() {
        Logger.log(Log.INFO, Marker.MAIN, "Closing App");
        CollectionManager.getManager().saveUserState();
        super.onDestroy();
    }

}
