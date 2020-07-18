package de.joachimsohn.collectivity.ui.adapter;

import android.app.Activity;
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
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> data;
    private Activity activity;

    public ItemAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView itemView = (CardView) inflater.inflate(R.layout.item_recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView itemImgView;
        private TextView itemTitleText;
        private TextView itemFirstValue;

        public ItemViewHolder(@NonNull View v) {
            super(v);
            itemImgView = v.findViewById(R.id.item_recyclerview_icon);
            itemTitleText = v.findViewById(R.id.item_recyclerview_title);
            itemFirstValue = v.findViewById(R.id.item_recyclerview_first);
        }

        public void bind(Item item) {
            itemImgView.setImageBitmap(item.getIcon());
            itemTitleText.setText(item.getName());
            itemFirstValue.setText(item.getAllAttributes());
        }
    }
}
