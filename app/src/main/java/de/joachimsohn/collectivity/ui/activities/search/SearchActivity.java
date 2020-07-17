package de.joachimsohn.collectivity.ui.activities.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;
import de.joachimsohn.collectivity.manager.search.SearchType;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchType type;
    private RecyclerView recyclerView;
    private CombinedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        type = (SearchType) getIntent().getSerializableExtra(SearchType.EXTRA);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        recyclerView = findViewById(R.id.collections_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CombinedAdapter();
        DataBaseConnector.getInstance().getAllUIObjects().observe(this, adapter::setData);
        recyclerView.setAdapter(adapter);
    }
}
