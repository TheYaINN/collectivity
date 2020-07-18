package de.joachimsohn.collectivity.ui.activities.storagelocation;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.Extra;
import de.joachimsohn.collectivity.ui.activities.collection.MainActivity;

public class StorageLocationAdapter extends RecyclerView.Adapter<StorageLocationAdapter.StorageLocationViewHolder> {

    private List<StorageLocation> data;
    private Activity activity;
    private CardView storageLocationView;

    public StorageLocationAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public StorageLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.recyclerciew_item_wide) {
            storageLocationView = (CardView) inflater.inflate(R.layout.recyclerciew_item_wide, parent, false);
        } else {
            storageLocationView = (CardView) inflater.inflate(R.layout.recyclerciew_item_wide_add, parent, false);
        }
        return new StorageLocationViewHolder(storageLocationView);
    }

    @Override
    public void onBindViewHolder(@NonNull StorageLocationViewHolder holder, int position) {
        if (data == null || position == getItemCount()) {
            holder.addNewStorageLocationListener(e -> {
                Intent intent = new Intent(activity, AddStorageLocationActivity.class);
                activity.startActivity(intent);
            });
        } else if (position < data.size()) {
            holder.bind(data.get(position));
            storageLocationView.setOnClickListener(e -> {
                //TODO: find another way to implement this
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra(Extra.ID.getValue(), data.get(position).getId());
                CacheManager.getManager().setLevel(CacheManager.Direction.DOWN);
                activity.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (data == null || position == data.size()) ? R.layout.recyclerciew_item_wide_add : R.layout.recyclerciew_item_wide;
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() + 1 : 1;
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

        @Nullable
        ImageView addButton;
        @Nullable
        private TextView title;
        @Nullable
        private TextView description;

        public StorageLocationViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.collection_item_title);
            description = v.findViewById(R.id.collection_item_description);
            addButton = v.findViewById(R.id.recyclerview_item_wide_add);
        }

        void bind(@NonNull StorageLocation storageLocation) {
            if (title != null) {
                title.setText(storageLocation.getName());
            }
            if (description != null) {
                description.setText(storageLocation.getDescription());
            }
        }

        public void addNewStorageLocationListener(@NonNull View.OnClickListener listener) {
            if (addButton != null) {
                addButton.setOnClickListener(listener);
            }
        }
    }
}
