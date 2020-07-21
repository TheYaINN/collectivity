package de.joachimsohn.collectivity.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.ui.fragments.CollectionFragment;
import de.joachimsohn.collectivity.ui.fragments.SearchFragment;
import de.joachimsohn.collectivity.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DataBaseConnector.getInstance().init(getApplication());
        setContentView(R.layout.default_overview);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        NavigationHelper.onStartReplace(this, new CollectionFragment());
    }

    @Override
    protected void onDestroy() {
        Logger.log(Logger.Priority.DEBUG, Logger.Marker.MAIN, "Closing App");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //Must be implemented empty to prevent unwanted behaviour
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return NavigationHelper.navigateUp(this, new SearchFragment(), false);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
