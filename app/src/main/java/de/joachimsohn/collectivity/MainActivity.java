package de.joachimsohn.collectivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.loader.CollectionLoader;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;
import de.joachimsohn.collectivity.manager.impl.SearchManager;
import de.joachimsohn.collectivity.ui.tabs.DetailedItemOverview;
import de.joachimsohn.collectivity.ui.tabs.FastItemOverview;
import de.joachimsohn.collectivity.ui.tabs.TabAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ItemViewModel itemViewModel;


    private void startup() {
        //Authentication of uer was successful

        //On starting the app after logging in, the collections are loaded from DB and given to the CollectionManager
        CollectionManager.getManager().setCollection(CollectionLoader.getInstance().loadAndGetCollection());
    }

    private void running() {
        SearchManager.getManager().search("A");

    }

    private void shutdown() {
        //Save the updated Objects from the CollectionManager to DB
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FastItemOverview(), "Tab 1");
        adapter.addFragment(new DetailedItemOverview(), "Tab 2");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        //setupAddItemButton();
        //setupRecyclerView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.fast_overview_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        recyclerView.setHasFixedSize(true);

        final ItemAdapter adapter = new ItemAdapter();
        recyclerView.setAdapter(adapter);

        //updating recyclerview on change
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        itemViewModel.getAllItems().observe(this, new Observer<List<Item>>() {

            @Override
            public void onChanged(List<Item> items) {
                adapter.setItems(items);
            }
        });
    }

    private void setupAddItemButton() {
        FloatingActionButton buttonAddItem = findViewById(R.id.add_item);
        buttonAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActitvity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddItemActitvity.EXTRA_TITLE);
            String value = data.getStringExtra(AddItemActitvity.EXTRA_VALUE);

            //TODO: remove this and replace with reading from extra
            Bitmap bmp = Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888);


            // Item item = new Item(title, OffsetDateTime.now(), null, bmp, null, value);
            //itemViewModel.insert(item);
            Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Item not saved", Toast.LENGTH_SHORT).show();

        }
    }
}
