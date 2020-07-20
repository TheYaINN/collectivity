package de.joachimsohn.collectivity.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.stream.Collectors;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.SearchManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.SearchTextWatcher;
import de.joachimsohn.collectivity.ui.adapter.MixedAdapter;

import static de.joachimsohn.collectivity.manager.impl.CacheManager.getManager;
import static de.joachimsohn.collectivity.ui.activities.NavigationHelper.navigateDown;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);

        setHasOptionsMenu(true);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setHasFixedSize(true);

        MixedAdapter adapter = new MixedAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        EditText tfSearch = view.findViewById(R.id.textfield_search);
        tfSearch.addTextChangedListener(new SearchTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                switch (getManager().getCurrentCacheLevel()) {
                    case COLLECTION:
                        adapter.setCollectionData(SearchManager.getManager().searchForCollection(charSequence.toString()));
                    case STORAGELOCATION:
                        SearchManager
                                .getManager()
                                .searchForStorageLocation()
                                .observe(requireActivity(), storageLocations -> adapter
                                        .setStorageLocations(storageLocations
                                                .stream()
                                                .filter(s -> s.getSearchString()
                                                        .contains(charSequence.toString()))
                                                .collect(Collectors.toList())));
                    case ITEM:
                        //TODO adapter.setItemData(SearchManager.getManager().searchForItem(charSequence.toString()));
                    default:
                }
            }
        });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getManager().getCurrentCacheLevel() == SearchType.COLLECTION) {
                navigateDown(getActivity(), new CollectionFragment(), true);
            } else if (getManager().getCurrentCacheLevel() == SearchType.STORAGELOCATION) {
                navigateDown(getActivity(), new StorageLocationFragment(), false);
            } else {
                navigateDown(getActivity(), new ItemFragment(), false);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
