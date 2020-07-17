package de.joachimsohn.collectivity.ui.activities.storagelocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;

public class StorageLocationAdapter extends RecyclerView.Adapter<StorageLocationAdapter.StorageLocationViewHolder> {

    private List<StorageLocation> data;


    @NonNull
    @Override
    public StorageLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView storageLocationView = (CardView) inflater.inflate(R.layout.storagelocation_recyclerview_item, parent, false);
        return new StorageLocationViewHolder(storageLocationView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageLocationViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<StorageLocation> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
        notifyDataSetChanged();
    }

    public static class StorageLocationViewHolder extends RecyclerView.ViewHolder {

        public StorageLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            //TODO
        }

        void bind(StorageLocation storageLocation) {
            //TODO
        }
    }
}
