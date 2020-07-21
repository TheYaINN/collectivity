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
import de.joachimsohn.collectivity.db.dao.impl.Tag;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.search.SearchType;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.fragments.AddItemFragment;
import de.joachimsohn.collectivity.ui.fragments.EditCollectionOrStorageLocationFragment;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheDirection.DOWN;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.COLLECTION;
import static de.joachimsohn.collectivity.manager.impl.CacheManager.getManager;

public class MixedAdapter extends RecyclerView.Adapter<MixedAdapter.MixedViewHolder> {

    private FragmentActivity activity;

    private List<Collection> collectionData = new ArrayList<>();
    private List<StorageLocation> storageLocationData = new ArrayList<>();
    private List<Item> itemData = new ArrayList<>();
    private List<Tag> tagData = new ArrayList<>();
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
            if (collectionData != null && collectionData.size() > 0) {
                Collection collection = collectionData.get(position);
                holder.bind(collection);
                view.setOnClickListener(e -> {
                    CacheManager.getManager().setIdForCacheLevel(COLLECTION, collection.getId());
                    NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
                });
            }
        } else if (getItemViewType(position) == SearchType.STORAGELOCATION.ordinal()) {
            if (storageLocationData != null && storageLocationData.size() > 0) {
                StorageLocation storageLocation = storageLocationData.get(position - (collectionData != null ? collectionData.size() : 0));
                holder.bind(storageLocation);
                view.setOnClickListener(e -> {
                    CacheManager.getManager().setCacheLevel(DOWN, storageLocation.getId());
                    NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
                });
            }
        } else {
            if (itemData != null && itemData.size() > 0) {
                int subtract = (collectionData != null ? collectionData.size() : 0) + (storageLocationData != null ? storageLocationData.size() : 0);
                Item item = itemData.get(position - subtract);
                holder.bind(item);
                view.setOnClickListener(e -> {
                    CacheManager.getManager().setCacheLevel(DOWN, item.getId());
                    NavigationHelper.navigateDown(activity, new AddItemFragment(), false);
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
        size += collectionData.size();
        size += storageLocationData.size();
        size += itemData.size();
        return size;
    }

    private void setCollectionData(List<Collection> newCollection) {
        collectionData.clear();
        collectionData.addAll(newCollection);
    }

    private void setStorageLocationData(List<StorageLocation> newStorageLocationData) {
        storageLocationData.clear();
        storageLocationData.addAll(newStorageLocationData);
    }

    private void setItemData(List<Item> newItemData) {
        itemData.clear();
        itemData.addAll(newItemData);
    }

    private void clearData() {
        collectionData.clear();
        storageLocationData.clear();
        itemData.clear();
    }

    public void search(String searchValue) {
        getManager().loadDataForSearch();
        //TODO: map tags to storagelocation or item
        switch (getManager().getCurrentCacheLevel()) {
            case COLLECTION:
                addCollectionDataObserver(searchValue);
            case STORAGELOCATION:
                addStorageLocationObserver(searchValue);
            case ITEM:
                addItemObserver(searchValue);
            default:
                break;
        }
    }

    private void filterData(String searchValue) {
        collectionData = collectionData.stream().filter(c -> c.getSearchString().contains(searchValue)).collect(Collectors.toList());
        storageLocationData = storageLocationData.stream().filter(s -> s.getSearchString().contains(searchValue)).collect(Collectors.toList());
        tagData = tagData.stream().filter(t -> t.getSearchString().contains(searchValue)).collect(Collectors.toList());
        itemData = itemData.stream().filter(i -> i.getSearchString().contains(searchValue)).collect(Collectors.toList());
    }

    private void addCollectionDataObserver(String searchValue) {
        CacheManager.getManager().getCollectionCache().observe(activity, c -> {
            setCollectionData(c);
            filterData(searchValue);
        });
        notifyDataSetChanged();
    }

    private void addStorageLocationObserver(String searchValue) {
        CacheManager.getManager().getStorageLocationCache().observe(activity, st -> {
            setStorageLocationData(st);
            filterData(searchValue);
        });
        notifyDataSetChanged();
    }

    private void addItemObserver(String searchValue) {
        CacheManager.getManager().getItemCache().observe(activity, i -> {
            setItemData(i);
            filterData(searchValue);
        });
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
