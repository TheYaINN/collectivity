package de.joachimsohn.collectivity.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> data;

    public CollectionAdapter(List<Collection> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView collectionView;
        if (viewType == R.layout.collection_item) {
            collectionView = (CardView) inflater.inflate(R.layout.collection_item, parent, false);
        } else {
            collectionView = (CardView) inflater.inflate(R.layout.collection_item_add, parent, false);
        }
        return new CollectionViewHolder(collectionView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.CollectionViewHolder holder, int position) {
        if (position == getItemCount()) {
            holder.addActionListener(e -> System.out.println("TEST"));
        } else {
            holder.bind(data.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == data.size()) ? R.layout.collection_item_add : R.layout.collection_item;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void setData(List<Collection> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
        notifyDataSetChanged();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageButton addButton;

        public CollectionViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.collection_item_title);
            description = v.findViewById(R.id.collection_item_description);
        }

        void bind(@NonNull Collection collection) {
            title.setText(collection.getName());
            description.setText(collection.getDescription());
        }

        public void addActionListener(View.OnClickListener listener) {
            addButton.setOnClickListener(listener);
        }
    }

}
