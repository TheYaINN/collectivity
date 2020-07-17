package de.joachimsohn.collectivity.ui.activities.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocationWithItems;

public class CombinedAdapter extends RecyclerView.Adapter<CombinedAdapter.CombinedViewHolder> {

    List<StorageLocationWithItems> data;

    @NonNull
    @Override
    public CombinedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView combinedView;
        if (viewType == R.layout.item) {
            combinedView = (CardView) inflater.inflate(R.layout.item_recyclerview_item, parent, false);
        } else {
            combinedView = (CardView) inflater.inflate(R.layout.storagelocation_recyclerview_item, parent, false);
        }
        return new CombinedViewHolder(combinedView);
    }

    @Override
    public void onBindViewHolder(@NonNull CombinedViewHolder holder, int position) {
        holder.bindStorageLocation(data.get(position).getStorageLocation());
        data.get(position).getItems().forEach(holder::bindItem);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<StorageLocationWithItems> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
        notifyDataSetChanged();
    }

    public static class CombinedViewHolder extends RecyclerView.ViewHolder {

        //ITEM
        ImageView itemImgView;
        TextView itemTitleText;
        TextView itemFirstValue;

        //STORAGELOCATION

        public CombinedViewHolder(@NonNull View v) {
            super(v);
            itemImgView = v.findViewById(R.id.item_recyclerview_icon);
            itemTitleText = v.findViewById(R.id.item_recyclerview_title);
            itemFirstValue = v.findViewById(R.id.item_recyclerview_first);

            //TODO: make storagelocation_recyclerview_item
        }

        void bindItem(@NonNull Item item) {
            itemImgView.setImageBitmap(item.getIcon());
            itemTitleText.setText(item.getName());
            itemFirstValue.setText(item.getAllAttributes());
        }

        void bindStorageLocation(@NonNull StorageLocation storageLocation) {

        }
    }
}
