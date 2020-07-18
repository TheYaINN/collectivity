package de.joachimsohn.collectivity.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.Extra;
import de.joachimsohn.collectivity.ui.activities.MainActivity;
import de.joachimsohn.collectivity.ui.activities.collection.AddCollectionActivity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> data;
    private Activity activity;
    private CardView collectionView;

    public CollectionAdapter(Activity activity) {
        this.activity = activity;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.recyclerciew_item_wide) {
            collectionView = (CardView) inflater.inflate(R.layout.recyclerciew_item_wide, parent, false);
        } else {
            collectionView = (CardView) inflater.inflate(R.layout.recyclerciew_item_wide_add, parent, false);
        }
        return new CollectionViewHolder(collectionView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.CollectionViewHolder holder, int position) {
        if (data == null || position == getItemCount()) {
            holder.addActionListener(e -> {
                Intent intent = new Intent(activity, AddCollectionActivity.class);
                activity.startActivity(intent);
            });
        } else if (position < data.size()) {
            holder.bind(data.get(position));
            collectionView.setOnClickListener(e -> {
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

        @Nullable
        private TextView title;

        @Nullable
        private TextView description;

        @Nullable
        private ImageButton addButton;

        public CollectionViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.collection_item_title);
            description = v.findViewById(R.id.collection_item_description);
            addButton = v.findViewById(R.id.recyclerview_item_wide_add);
        }

        void bind(@NonNull Collection collection) {
            if (title != null) {
                title.setText(collection.getName());
            }
            if (description != null) {
                description.setText(collection.getDescription());
            }
        }

        public void addActionListener(View.OnClickListener listener) {
            if (addButton != null) {
                addButton.setOnClickListener(listener);
            }
        }
    }

}
