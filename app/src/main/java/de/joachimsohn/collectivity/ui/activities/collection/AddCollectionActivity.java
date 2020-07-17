package de.joachimsohn.collectivity.ui.activities.collection;

import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;

public class AddCollectionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_add);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_save == item.getItemId()) {
            EditText name = findViewById(R.id.textfield_name);
            EditText description = findViewById(R.id.textfield_description);
            DataBaseConnector.getInstance().insert(new Collection(name.getText().toString().trim(), description.getText().toString().trim()));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
