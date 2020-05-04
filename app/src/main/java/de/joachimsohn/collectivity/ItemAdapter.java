package de.joachimsohn.collectivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {


    private List<Item> items = new ArrayList<>();

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fast_overview_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item currentNode = items.get(position);
        holder.itemViewTitle.setText(currentNode.getTitle());
        holder.itemViewIcon.setImageBitmap(currentNode.getIcon());
        holder.itemViewWorth.setText(String.valueOf(currentNode.getWorth()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private TextView itemViewTitle;
        private TextView itemViewWorth;
        private ImageView itemViewIcon;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            itemViewTitle = itemView.findViewById(R.id.fast_overview_item_title);
            itemViewIcon = itemView.findViewById(R.id.fast_overview_item_icon);
            itemViewWorth = itemView.findViewById(R.id.fast_overview_item_worth);
        }
    }
}
