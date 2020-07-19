package de.joachimsohn.collectivity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;

public class MixedAdapter extends RecyclerView.Adapter<MixedAdapter.MixedViewHolder> {

    private FragmentActivity activity;

    @Nullable
    private List<Collection> collectionData;

    @Nullable
    private List<StorageLocation> storageData;

    @Nullable
    private List<Item> itemData;
    private CardView view;


    public MixedAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public MixedAdapter.MixedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = (CardView) inflater.inflate(R.layout.recyclerview_item_wide, parent, false);
        return new MixedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MixedAdapter.MixedViewHolder holder, int position) {
        if (collectionData != null) {
            holder.bind(collectionData.get(position));
        }
        if (storageData != null) {
            holder.bind(storageData.get(position));
        }
        if (itemData != null) {
            holder.bind(itemData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (collectionData != null) {
            size += collectionData.size();
        }
        if (storageData != null) {
            size += storageData.size();
        }
        if (itemData != null) {
            size += itemData.size();
        }
        return size;
    }

    public void setCollectionData(List<Collection> newCollection) {
        if (collectionData != null) {
            collectionData.clear();
            collectionData.addAll(newCollection);
        } else {
            collectionData = newCollection;
        }
        notifyDataSetChanged();
    }

    public void setStorageLocationData(List<StorageLocation> newStorageLocationData) {
        if (storageData != null) {
            storageData.clear();
            storageData.addAll(newStorageLocationData);
        } else {
            storageData = newStorageLocationData;
        }
        notifyDataSetChanged();
    }

    public void setItemData(List<Item> newItemData) {
        if (itemData != null) {
            itemData.clear();
            itemData.addAll(newItemData);
        } else {
            itemData = newItemData;
        }
        notifyDataSetChanged();
    }

    public static class MixedViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private TextView title;

        @Nullable
        private TextView description;

        //TODO: item

        public MixedViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.recyclerview_item_wide_title);
            description = v.findViewById(R.id.recyclerview_item_wide_description);
        }

        void bind(@NonNull Collection collection) {
            if (title != null) {
                title.setText(collection.getName());
            }
            if (description != null) {
                description.setText(collection.getDescription());
            }
        }

        void bind(@NonNull StorageLocation storageLocation) {
            if (title != null) {
                title.setText(storageLocation.getName());
            }
            if (description != null) {
                description.setText(storageLocation.getDescription());
            }
        }

        public void bind(Item item) {

        }
    }
}
