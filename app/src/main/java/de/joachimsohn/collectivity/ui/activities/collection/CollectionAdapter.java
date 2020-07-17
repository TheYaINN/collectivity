package de.joachimsohn.collectivity.ui.activities.collection;

import android.app.Activity;
import android.content.Intent;
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
    private Activity activity;

    public CollectionAdapter(Activity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardView collectionView;
        if (viewType == R.layout.collection_recyclerview_item) {
            collectionView = (CardView) inflater.inflate(R.layout.collection_recyclerview_item, parent, false);
        } else {
            collectionView = (CardView) inflater.inflate(R.layout.recyclerciew_add, parent, false);
        }
        return new CollectionViewHolder(collectionView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.CollectionViewHolder holder, int position) {
        if (data == null || position == getItemCount()) {
            holder.addActionListener(e -> {
                Intent intent = new Intent(activity, AddCollectionActivity.class);
                intent.putExtra("some", "some");
                activity.startActivity(intent);
            });
        } else if (position < data.size()) {
            holder.bind(data.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (data == null || position == data.size()) ? R.layout.recyclerciew_add : R.layout.collection_recyclerview_item;
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() + 1 : 1;
    }

    public void setData(List<Collection> newData) {
        if (data != null) {
            data.clear();
            data.addAll(newData);
            notifyDataSetChanged();
        } else {
            data = newData;
        }
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageButton addButton;

        public CollectionViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.collection_item_title);
            description = v.findViewById(R.id.collection_item_description);
            addButton = v.findViewById(R.id.action_collection_add);
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
