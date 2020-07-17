package de.joachimsohn.collectivity.ui.activities.collection;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import de.joachimsohn.collectivity.R;

public class CollectionFragment extends FragmentActivity {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_overview);

    }

}
