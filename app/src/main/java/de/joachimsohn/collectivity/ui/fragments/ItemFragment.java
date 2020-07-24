package de.joachimsohn.collectivity.ui.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.adapter.ItemAdapter;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.ITEM;
import static de.joachimsohn.collectivity.manager.sort.SortType.AMOUNT;
import static de.joachimsohn.collectivity.manager.sort.SortType.BUY_DATE;
import static de.joachimsohn.collectivity.manager.sort.SortType.CONDITION;
import static de.joachimsohn.collectivity.manager.sort.SortType.DESCRIPTION;
import static de.joachimsohn.collectivity.manager.sort.SortType.INSERTION_DATE;
import static de.joachimsohn.collectivity.manager.sort.SortType.NAME;
import static de.joachimsohn.collectivity.manager.sort.SortType.POSITION;
import static de.joachimsohn.collectivity.manager.sort.SortType.VALUE;

public class ItemFragment extends Fragment {

    private @NonNull
    ItemAdapter adapter;
    private SubMenu subMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        adapter = new ItemAdapter(getActivity());
        CacheManager.getManager().getItemCache().observe(requireActivity(), adapter::setData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.item_search_sort, menu);
        subMenu = menu.getItem(1).getSubMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return NavigationHelper.navigateLeft(getActivity(), new StorageLocationFragment(), ITEM, adapter.getParent());
            case R.id.action_dropdown_name:
                adapter.sortBy(NAME);
                SortDirection directionName = SortManager.getManager().getStorageLocationStateMemory().get(NAME);
                switch (directionName) {
                    case ASCENDING:
                        subMenu.getItem(0).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(0).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(0).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(0).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(NAME);
            case R.id.action_dropdown_amount:
                SortDirection directionAmount = SortManager.getManager().getStorageLocationStateMemory().get(AMOUNT);
                switch (directionAmount) {
                    case ASCENDING:
                        subMenu.getItem(1).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(1).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(1).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(1).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(1).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(1).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(AMOUNT);
            case R.id.action_dropdown_description:
                SortDirection directionDesc = SortManager.getManager().getStorageLocationStateMemory().get(DESCRIPTION);
                switch (directionDesc) {
                    case ASCENDING:
                        subMenu.getItem(2).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(2).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(2).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(2).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(2).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(2).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(DESCRIPTION);
            case R.id.action_dropdown_value:
                SortDirection directionValue = SortManager.getManager().getStorageLocationStateMemory().get(VALUE);
                switch (directionValue) {
                    case ASCENDING:
                        subMenu.getItem(3).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(3).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(3).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(3).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(3).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(3).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(VALUE);
            case R.id.action_dropdown_insertiondate:
                SortDirection directionInsertionDate = SortManager.getManager().getStorageLocationStateMemory().get(INSERTION_DATE);
                switch (directionInsertionDate) {
                    case ASCENDING:
                        subMenu.getItem(4).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(4).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(4).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(4).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(4).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(4).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(INSERTION_DATE);
            case R.id.action_dropdown_buydate:
                SortDirection directionBuyDate = SortManager.getManager().getStorageLocationStateMemory().get(BUY_DATE);
                switch (directionBuyDate) {
                    case ASCENDING:
                        subMenu.getItem(5).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(5).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(5).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(5).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(6).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(6).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(BUY_DATE);
            case R.id.action_dropdown_condition:
                SortDirection directionCondition = SortManager.getManager().getStorageLocationStateMemory().get(CONDITION);
                switch (directionCondition) {
                    case ASCENDING:
                        subMenu.getItem(7).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(7).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(7).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(7).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(7).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(7).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(CONDITION);
            case R.id.action_dropdown_position:
                SortDirection directionPosition = SortManager.getManager().getStorageLocationStateMemory().get(POSITION);
                switch (directionPosition) {
                    case ASCENDING:
                        subMenu.getItem(8).setIcon(R.drawable.ic_arrow_up);
                        subMenu.getItem(8).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case DESCENDING:
                        subMenu.getItem(8).setIcon(R.drawable.ic_arrow_down);
                        subMenu.getItem(8).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(8).setIcon(R.drawable.empty_icon);
                        subMenu.getItem(8).getIcon().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
                        break;
                }
                return adapter.sortBy(POSITION);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
