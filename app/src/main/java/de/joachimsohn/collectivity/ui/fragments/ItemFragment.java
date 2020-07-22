package de.joachimsohn.collectivity.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return NavigationHelper.navigateLeft(getActivity(), new StorageLocationFragment(), ITEM, adapter.getParent());
            case R.id.action_dropdown_name:
                return adapter.sortBy(NAME);
            case R.id.action_dropdown_amount:
                return adapter.sortBy(AMOUNT);
            case R.id.action_dropdown_description:
                return adapter.sortBy(DESCRIPTION);
            case R.id.action_dropdown_value:
                return adapter.sortBy(VALUE);
            case R.id.action_dropdown_insertiondate:
                return adapter.sortBy(INSERTION_DATE);
            case R.id.action_dropdown_buydate:
                return adapter.sortBy(BUY_DATE);
            case R.id.action_dropdown_condition:
                return adapter.sortBy(CONDITION);
            case R.id.action_dropdown_position:
                return adapter.sortBy(POSITION);

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
