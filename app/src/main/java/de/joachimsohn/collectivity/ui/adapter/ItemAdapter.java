package de.joachimsohn.collectivity.ui.adapter;

import android.app.Activity;
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
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.impl.SortManager;
import de.joachimsohn.collectivity.manager.sort.SortType;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.fragments.AddItemFragment;
import de.joachimsohn.collectivity.ui.fragments.EditItemFragment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> data;
    private Activity activity;

    private CardView itemView;

    public ItemAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.recyclerview_item) {
            itemView = (CardView) inflater.inflate(R.layout.recyclerview_item, parent, false);
        } else {
            itemView = (CardView) inflater.inflate(R.layout.recyclerview_item_add, parent, false);
        }
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (data != null && position < data.size()) {
            holder.bind(data.get(position));
            itemView.setOnClickListener(e -> {
                CacheManager.getManager().setIdForCacheLevel(de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.ITEM, data.get(position).getId());
                NavigationHelper.navigateDown(activity, new EditItemFragment(), false);
            });
        } else {
            holder.addNewItemActionListener(e -> NavigationHelper.navigateDown(activity, new AddItemFragment(), false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (data == null || position == data.size()) ? R.layout.recyclerview_item_add : R.layout.recyclerview_item;
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() + 1 : 0;
    }

    public void setData(List<Item> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
        notifyDataSetChanged();
    }

    public boolean sortBy(SortType sortType) {
        List<Item> storageLocations = SortManager.getManager().sortItemsBy(sortType);
        if (storageLocations != null) {
            data = storageLocations;
            notifyDataSetChanged();
        }
        return true;
    }

    public long getParent() {
        if (data.size() > 0) {
            return data.get(0).getId();
        }
        return -1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private @Nullable
        ImageView itemImgView;
        private @Nullable
        TextView itemTitleText;
        private @Nullable
        TextView attributes;
        private @Nullable
        ImageView addButton;

        public ItemViewHolder(@NonNull View v) {
            super(v);
            itemImgView = v.findViewById(R.id.recyclerview_item_icon);
            itemTitleText = v.findViewById(R.id.recyclerview_item_title);
            attributes = v.findViewById(R.id.recyclerview_item_attributes);
            addButton = v.findViewById(R.id.recyclerview_item_icon_add);
        }

        public void bind(Item item) {
            if (itemTitleText != null) {
                itemTitleText.setText(item.getName());
            }
            if (attributes != null) {
                attributes.setText(item.getAllAttributes());
            }
        }

        public void addNewItemActionListener(View.OnClickListener listener) {
            if (addButton != null) {
                addButton.setOnClickListener(listener);
            }
        }
    }
}
