package de.joachimsohn.collectivity.ui.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.adapter.StorageLocationAdapter;

public class StorageLocationFragment extends Fragment {

    private SubMenu subMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheManager.getManager().setStorageLocations(DataBaseConnector.getInstance().getAllStorageLocationsForID(CacheManager.getManager().getCurrentId()));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_wide, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_wide_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        StorageLocationAdapter adapter = new StorageLocationAdapter(getActivity());
        CacheManager.getManager().getStorageLocations().observe(requireActivity(), adapter::setData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.collection_search_sort, menu);
        subMenu = menu.getItem(1).getSubMenu();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavigationHelper.navigateUpToFragment(getActivity(), new CollectionFragment());
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
