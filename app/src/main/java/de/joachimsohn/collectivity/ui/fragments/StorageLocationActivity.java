package de.joachimsohn.collectivity.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.annotation.Nullable;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;

public class StorageLocationActivity extends Activity {

    private SubMenu subMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
