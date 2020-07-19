package de.joachimsohn.collectivity.ui.adapter;

import android.app.Activity;
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
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.fragments.AddCollectionOrStorageLocationFragment;
import de.joachimsohn.collectivity.ui.fragments.EditCollectionOrStorageLocationFragment;
import de.joachimsohn.collectivity.ui.fragments.StorageLocationFragment;

import static de.joachimsohn.collectivity.dbconnector.DataBaseConnector.getInstance;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> data;
    private Activity activity;
    private View.OnClickListener listener;
    private CardView collectionView;

    public CollectionAdapter(Activity activity) {
        this.activity = activity;
    }


    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.recyclerview_item_wide) {
            collectionView = (CardView) inflater.inflate(R.layout.recyclerview_item_wide, parent, false);
        } else {
            collectionView = (CardView) inflater.inflate(R.layout.recyclerview_item_wide_add, parent, false);
        }
        return new CollectionViewHolder(collectionView);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.CollectionViewHolder holder, int position) {
        if (data != null && position < data.size()) {
            holder.bind(data.get(position));
            collectionView.setOnClickListener(e -> NavigationHelper.navigateRight(activity, new StorageLocationFragment(), data.get(position).getId()));
            holder.addDeleteAndEditListener(data.get(position), e -> {
                CacheManager.getManager().setCurrentId(data.get(position).getId());
                NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
            });
        } else {
            holder.addNewCollectionActionListener(e -> NavigationHelper.navigateDown(activity, new AddCollectionOrStorageLocationFragment(), false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (data == null || position == data.size()) ? R.layout.recyclerview_item_wide_add : R.layout.recyclerview_item_wide;
    }

    @Override
    public int getItemCount() {
        return (data != null) ? data.size() + 1 : 0;
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
        private View v;

        public CollectionViewHolder(@NonNull View v) {
            super(v);
            this.v = v;
            title = v.findViewById(R.id.recyclerview_item_wide_title);
            description = v.findViewById(R.id.recyclerview_item_wide_description);
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

        public void addDeleteAndEditListener(Collection collection, View.OnClickListener el) {
            v.setOnLongClickListener(e -> {
                ImageButton delete = v.findViewById(R.id.cardview_delete);
                if (delete.getVisibility() == View.INVISIBLE) {
                    delete.setVisibility(View.VISIBLE);
                    delete.setOnClickListener(dl -> getInstance().delete(collection));
                } else {
                    delete.setVisibility(View.INVISIBLE);
                }
                ImageButton edit = v.findViewById(R.id.cardview_edit);
                if (edit.getVisibility() == View.INVISIBLE) {
                    edit.setVisibility(View.VISIBLE);
                    edit.setOnClickListener(el);
                } else {
                    edit.setVisibility(View.INVISIBLE);
                }
                return true;
            });
        }

        public void addNewCollectionActionListener(View.OnClickListener listener) {
            if (addButton != null) {
                addButton.setOnClickListener(listener);
            }
        }
    }

}
