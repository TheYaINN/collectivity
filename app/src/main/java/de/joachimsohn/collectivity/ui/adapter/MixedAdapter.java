package de.joachimsohn.collectivity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.fragments.EditCollectionOrStorageLocationFragment;

public class MixedAdapter extends RecyclerView.Adapter<MixedAdapter.MixedViewHolder> {

    private FragmentActivity activity;

    @Nullable
    private List<Collection> collectionData;

    private List<StorageLocation> storageLocationData = new ArrayList<>();

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
        view = (CardView) inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new MixedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MixedAdapter.MixedViewHolder holder, int position) {
        if (getItemViewType(position) == SearchType.COLLECTION.ordinal()) {
            if (collectionData != null) {
                holder.bind(collectionData.get(position));
                view.setOnClickListener(e -> {
                    CacheManager.getManager().setCurrentId(collectionData.get(position).getId());
                    NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
                });
            }
        } else if (getItemViewType(position) == SearchType.STORAGELOCATION.ordinal()) {
            if (storageLocationData != null) {
                holder.bind(storageLocationData.get(position - (collectionData != null ? collectionData.size() : 0)));
                view.setOnClickListener(e -> {
                    CacheManager.getManager().setLevel(CacheManager.Direction.DOWN);
                    CacheManager.getManager().setCurrentId(storageLocationData.get(position - (collectionData != null ? collectionData.size() : 0)).getId());
                    NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
                });
            }
        } else {
            if (itemData != null) {
                holder.bind(itemData.get(position));
                view.setOnClickListener(e -> {
                    System.out.println("YOUUUUUUUUUUUUUUUUUUUUUUUUUU CLIK MÄH"); //TODO:
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (collectionData != null && position < collectionData.size()) {
            return SearchType.COLLECTION.ordinal();
        } else if (storageLocationData != null && collectionData != null && position - collectionData.size() < storageLocationData.size()) {
            return SearchType.STORAGELOCATION.ordinal();
        } else if (itemData != null) {
            return SearchType.ITEM.ordinal();
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (collectionData != null) {
            size += collectionData.size();
        }
        if (storageLocationData != null) {
            size += storageLocationData.size();
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

    public void setStorageLocations(List<StorageLocation> newStorageLocationData) {
        newStorageLocationData.addAll(storageLocationData);
        storageLocationData = newStorageLocationData
                .stream()
                .filter(StorageLocation.distinctByKey(StorageLocation::getId))
                .collect(Collectors.toList());
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
        private ImageView itemIcon;

        @Nullable
        private TextView itemTitle;

        @Nullable
        private TextView itemAttributes;

        public MixedViewHolder(@NonNull View v) {
            super(v);
            itemIcon = v.findViewById(R.id.recyclerview_item_icon);
            itemTitle = v.findViewById(R.id.recyclerview_item_title);
            itemAttributes = v.findViewById(R.id.recyclerview_item_wide_description);
        }

        void bind(@NonNull Collection collection) {
            if (itemTitle != null) {
                itemTitle.setText(collection.getName());
            }
            if (itemAttributes != null) {
                itemAttributes.setText(collection.getDescription());
            }
        }

        void bind(@NonNull StorageLocation storageLocation) {
            if (itemTitle != null) {
                itemTitle.setText(storageLocation.getName());
            }
            if (itemAttributes != null) {
                itemAttributes.setText(storageLocation.getDescription());
            }
        }

        public void bind(@NonNull Item item) {
            if (itemIcon != null) {
                itemIcon.setImageBitmap(item.getIcon());
            }
            if (itemTitle != null) {
                itemTitle.setText(item.getName());
            }
            if (itemAttributes != null) {
                itemAttributes.setText(item.getAllAttributes());
            }
        }
    }
}
