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
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortDirection;
import de.joachimsohn.collectivity.ui.adapter.CollectionAdapter;

import static de.joachimsohn.collectivity.manager.sort.SortType.DESCRIPTION;
import static de.joachimsohn.collectivity.manager.sort.SortType.NAME;

public class CollectionFragment extends Fragment {

    private CollectionAdapter adapter;
    private SubMenu subMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_wide, container, false);
        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new CollectionAdapter(getActivity());
        CacheManager.getManager().getCollectionCache().observe(requireActivity(), adapter::setData);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.collection_search_sort, menu);
        subMenu = menu.getItem(1).getSubMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dropdown_name:
                adapter.sortBy(NAME);
                SortDirection directionName = SortManager.getManager().getCollectionSortStateMemory().get(NAME);
                switch (directionName) {
                    case ASCENDING:
                        subMenu.getItem(0).setIcon(R.drawable.ic_arrow_up);
                        break;
                    case DESCENDING:
                        subMenu.getItem(0).setIcon(R.drawable.ic_arrow_down);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(0).setIcon(R.drawable.empty_icon);
                        break;
                }
                SortManager.getManager().getCollectionSortStateMemory().get(NAME);
                return true;
            case R.id.action_dropdown_description:
                adapter.sortBy(DESCRIPTION);
                SortDirection directionDesc = SortManager.getManager().getCollectionSortStateMemory().get(DESCRIPTION);
                switch (directionDesc) {
                    case ASCENDING:
                        subMenu.getItem(1).setIcon(R.drawable.ic_arrow_up);
                        break;
                    case DESCENDING:
                        subMenu.getItem(1).setIcon(R.drawable.ic_arrow_down);
                        break;
                    case NONE:
                    default:
                        subMenu.getItem(1).setIcon(R.drawable.empty_icon);
                        break;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
