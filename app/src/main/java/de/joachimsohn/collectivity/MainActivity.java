package de.joachimsohn.collectivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import de.joachimsohn.collectivity.loader.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;
import de.joachimsohn.collectivity.ui.Marker;
import de.joachimsohn.collectivity.ui.tabs.TabAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ItemViewModel itemViewModel;

    private void initGUI() {
        setContentView(R.layout.collection_view);

        //viewPager = findViewById(R.id.viewPager);
        //tabLayout = findViewById(R.id.tabLayout);
        //adapter = new TabAdapter(getSupportFragmentManager());
        // adapter.addFragment(new FastItemOverview(), "Tab 1");
        //adapter.addFragment(new DetailedItemOverview(), "Tab 2");
        //viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);

        //setupAddItemButton();
        // setupRecyclerView();
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
