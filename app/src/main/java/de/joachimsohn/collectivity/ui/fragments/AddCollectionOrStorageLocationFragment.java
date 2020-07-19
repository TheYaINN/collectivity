package de.joachimsohn.collectivity.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;

public class AddCollectionOrStorageLocationFragment extends Fragment {

    private Toolbar toolbar;
    private EditText tfName;
    private EditText tfDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_add, container, false);
        setHasOptionsMenu(true);
        tfName = view.findViewById(R.id.textfield_name);
        tfDescription = view.findViewById(R.id.textfield_description);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return goBack();
            case R.id.action_save:
                if (hasInsertedData()) {
                    return goBack();
                } else {
                    Toast.makeText(getContext(), R.string.missing_name, Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean hasInsertedData() {
        String name = tfName.getText().toString().trim();
        String description = tfDescription.getText().toString().trim();
        if (!name.isEmpty()) {
            if (description.isEmpty()) {
                description = null;
            }
            if (CacheManager.getManager().getCurrentCacheLevel() == SearchType.STORAGELOCATION) {
                StorageLocation storageLocation = new StorageLocation(name, description);
                storageLocation.setCollectionId(CacheManager.getManager().getCurrentId());
                return DataBaseConnector.getInstance().insert(storageLocation);
            } else {
                return DataBaseConnector.getInstance().insert(new Collection(name, description));
            }
        }
        return false;
    }

    private boolean goBack() {
        if (CacheManager.getManager().getCurrentCacheLevel() == SearchType.STORAGELOCATION) {
            return NavigationHelper.navigateUp(getActivity(), new StorageLocationFragment(), false);
        } else {
            return NavigationHelper.navigateUp(getActivity(), new CollectionFragment(), true);
        }
    }
}
