package de.joachimsohn.collectivity.ui;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;

public class CollectionAdapter extends Adapter {

    LiveData<List<Collection>> dataSet;

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        //TODO: see https://developer.android.com/guide/topics/ui/layout/recyclerview
        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public CollectionAdapter(LiveData<List<Collection>> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //TODO
    }

    @Override
    public int getItemCount() {
        return (dataSet.getValue() != null) ? dataSet.getValue().size() : 0;
    }
}
