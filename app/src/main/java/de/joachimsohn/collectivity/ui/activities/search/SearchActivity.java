package de.joachimsohn.collectivity.ui.activities.search;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.impl.SearchManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.Marker;
import de.joachimsohn.collectivity.ui.adapter.StorageLocationAdapter;
import de.joachimsohn.collectivity.util.logging.Logger;
import de.joachimsohn.collectivity.util.logging.Priority;
import lombok.NonNull;

public class SearchActivity extends AppCompatActivity {

    private @Nullable
    Toolbar toolbar;

    private @Nullable
    SearchType type;

    private @Nullable
    RecyclerView recyclerView;

    private @Nullable
    EditText tfSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
        setContentView(R.layout.search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerview_wide);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        findViewById(R.id.recyclervew_item_wide_add_cardview).setVisibility(View.GONE);
        setUpSearch();
    }

    private void setUpSearch() {
        tfSearch = findViewById(R.id.textfield_search);
        type = (SearchType) getIntent().getSerializableExtra(SearchType.EXTRA);
        if (type == null || tfSearch == null) {
            return;
        }

        switch (type) {
            case COLLECTION:
                if (recyclerView != null) {
                    break;
                }
                StorageLocationAdapter adapter = new StorageLocationAdapter(this);
                DataBaseConnector.getInstance().getAllStorageLocations().observe(this, adapter::setData);
                recyclerView.setAdapter(adapter);
                tfSearch.addTextChangedListener(new SearchTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable editable) {
                        Logger.log(Priority.DEBUG, Marker.SEARCH, editable.toString());
                        SearchManager.getManager().searchForStorageLocation(editable.toString());
                    }
                });
            case STORAGELOCATION:
                //TODO: change to recyclerview.getSearchValue; (observable value)
                @NonNull List<Object> objects = SearchManager.getManager().searchForStorageLocation(null);
            case ITEM:
                @NonNull List<Object> objects2 = SearchManager.getManager().searchForItem(null);
            default:
                //TODO: make combined search for multiple ENUM value
                break;
        }
    }
}
