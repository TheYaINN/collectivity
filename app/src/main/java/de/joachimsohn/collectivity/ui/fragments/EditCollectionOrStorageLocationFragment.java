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
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.COLLECTION;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.STORAGELOCATION;

public class EditCollectionOrStorageLocationFragment extends Fragment {

    private EditText tfName;
    private EditText tfDescription;
    private EditText tfTags;

    private Optional<StorageLocation> currentStorageLocation = Optional.empty();

    private Optional<Collection> currentCollection = Optional.empty();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_add, container, false);
        setHasOptionsMenu(true);
        tfName = view.findViewById(R.id.textfield_name);
        tfDescription = view.findViewById(R.id.textfield_description);
        tfTags = view.findViewById(R.id.textfield_tags);
        if (CacheManager.getManager().getCurrentCacheLevel() == STORAGELOCATION) {
            tfTags.setVisibility(View.VISIBLE);
            CacheManager.getManager().getStorageLocationCache().observe(requireActivity(), stl -> {
                currentStorageLocation = stl.stream().filter(s -> s.getId() == CacheManager.getManager().getIdForCacheLevel(COLLECTION)).findFirst();
                if (currentStorageLocation.isPresent()) {
                    tfName.setText(currentStorageLocation.get().getName());
                    tfDescription.setText(currentStorageLocation.get().getDescription());
                }
            });
            CacheManager.getManager().getTags().observe(requireActivity(), tl -> tfTags
                    .setText(tl
                            .stream()
                            .map(Tag::toString)
                            .collect(Collectors.joining(", "))));
        } else {
            CacheManager.getManager().getCollectionCache().observe(requireActivity(), cl -> {
                currentCollection = cl.stream().filter(c -> c.getId() == CacheManager.getManager().getIdForCacheLevel(COLLECTION)).findFirst();
                if (currentCollection.isPresent()) {
                    tfName.setText(currentCollection.get().getName());
                    tfDescription.setText(currentCollection.get().getDescription());
                }
            });
        }
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
                if (isValidData()) {
                    return goBack();
                } else {
                    Toast.makeText(getContext(), R.string.missing_name, Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isValidData() {
        String name = tfName.getText().toString().trim();
        if (!name.isEmpty()) {
            if (CacheManager.getManager().getCurrentCacheLevel() == STORAGELOCATION) {
                if (currentStorageLocation.isPresent()) {
                    currentStorageLocation.get().setName(name);
                    currentStorageLocation.get().setDescription(tfDescription.getText().toString().trim());
                    currentStorageLocation.get().setTags(Arrays.stream(tfTags.getText().toString().split(",")).map(Tag::new).collect(Collectors.toList()));
                    return DataBaseConnector.getInstance().update(currentStorageLocation.get());
                }
            } else {
                if (currentCollection.isPresent()) {
                    currentCollection.get().setName(name);
                    currentCollection.get().setDescription(tfDescription.getText().toString().trim());
                    return DataBaseConnector.getInstance().update(currentCollection.get());
                }
            }
        }
        return false;
    }

    private boolean goBack() {
        if (CacheManager.getManager().getCurrentCacheLevel() == STORAGELOCATION) {
            return NavigationHelper.navigateUp(getActivity(), new StorageLocationFragment(), false);
        } else {
            return NavigationHelper.navigateUp(getActivity(), new CollectionFragment(), true);
        }
    }

}
