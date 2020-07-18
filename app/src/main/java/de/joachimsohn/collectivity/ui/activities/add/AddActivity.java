package de.joachimsohn.collectivity.ui.activities.add;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.activities.MainActivity;

public class AddActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        setContentView(R.layout.default_add);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_save == item.getItemId()) {
            EditText tfName = findViewById(R.id.textfield_name);
            EditText tfDescription = findViewById(R.id.textfield_description);
            String name = tfName.getText().toString().trim();
            String description = tfDescription.getText().toString().trim();
            if (!name.isEmpty()) {
                if (CacheManager.getManager().getCurrentCacheLevel() == SearchType.STORAGELOCATION) {
                    StorageLocation storageLocation = new StorageLocation(name, description);
                    storageLocation.setCollectionId(CacheManager.getManager().getCurrentId());
                    DataBaseConnector.getInstance().insert(storageLocation);
                } else {
                    DataBaseConnector.getInstance().insert(new Collection(name, description));
                }
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
