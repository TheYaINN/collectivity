package de.joachimsohn.collectivity.ui.fragments;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortCriteria;
import de.joachimsohn.collectivity.manager.sort.SortDirection;

public class ItemActivity extends Activity {

    private SubMenu subMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_search_sort, menu);
        subMenu = menu.getItem(1).getSubMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dropdown_name:
                SortDirection directionName = SortManager.getManager().sortItemsBy(SortCriteria.NAME);
                subMenu.getItem(0).setIcon(directionName.getIcon());
                return true;
            case R.id.action_dropdown_amount:
                SortDirection directionAmount = SortManager.getManager().sortItemsBy(SortCriteria.AMOUNT);
                subMenu.getItem(1).setIcon(directionAmount.getIcon());
                return true;
            case R.id.action_dropdown_description:
                SortDirection directionDescription = SortManager.getManager().sortItemsBy(SortCriteria.DESCRIPTION);
                subMenu.getItem(2).setIcon(directionDescription.getIcon());
                return true;
            case R.id.action_dropdown_ean:
                SortDirection directionEAN = SortManager.getManager().sortItemsBy(SortCriteria.EAN);
                subMenu.getItem(3).setIcon(directionEAN.getIcon());
                return true;
            case R.id.action_dropdown_value:
                SortDirection directionValue = SortManager.getManager().sortItemsBy(SortCriteria.VALUE);
                subMenu.getItem(4).setIcon(directionValue.getIcon());
                return true;
            case R.id.action_dropdown_insertiondate:
                SortDirection directionInsertionDate = SortManager.getManager().sortItemsBy(SortCriteria.INSERTION_DATE);
                subMenu.getItem(5).setIcon(directionInsertionDate.getIcon());
                return true;
            case R.id.action_dropdown_buydate:
                SortDirection directionBuyDate = SortManager.getManager().sortItemsBy(SortCriteria.BUY_DATE);
                subMenu.getItem(6).setIcon(directionBuyDate.getIcon());
                return true;
            case R.id.action_dropdown_condition:
                SortDirection directionCondition = SortManager.getManager().sortItemsBy(SortCriteria.CONDITION);
                subMenu.getItem(7).setIcon(directionCondition.getIcon());
                return true;
            case R.id.action_dropdown_position:
                SortDirection directionPosition = SortManager.getManager().sortItemsBy(SortCriteria.POSITION);
                subMenu.getItem(8).setIcon(directionPosition.getIcon());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
