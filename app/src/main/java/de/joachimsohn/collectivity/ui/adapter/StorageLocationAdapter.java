package de.joachimsohn.collectivity.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.db.dao.impl.StorageLocation;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.ui.activities.NavigationHelper;
import de.joachimsohn.collectivity.ui.fragments.AddCollectionOrStorageLocationFragment;
import de.joachimsohn.collectivity.ui.fragments.EditCollectionOrStorageLocationFragment;
import de.joachimsohn.collectivity.ui.fragments.ItemFragment;

import static de.joachimsohn.collectivity.dbconnector.DataBaseConnector.getInstance;

public class StorageLocationAdapter extends RecyclerView.Adapter<StorageLocationAdapter.StorageLocationViewHolder> {

    private List<StorageLocation> data;
    private Activity activity;
    private CardView storageLocationView;

    public StorageLocationAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public StorageLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == R.layout.recyclerview_item_wide) {
            storageLocationView = (CardView) inflater.inflate(R.layout.recyclerview_item_wide, parent, false);
        } else {
            storageLocationView = (CardView) inflater.inflate(R.layout.recyclerview_item_wide_add, parent, false);
        }
        return new StorageLocationViewHolder(storageLocationView);
    }


    @Override
    public void onBindViewHolder(@NonNull StorageLocationViewHolder holder, int position) {
        if (data != null && position < data.size()) {
            holder.bind(data.get(position));
            storageLocationView.setOnClickListener(e -> NavigationHelper.navigateRight(activity, new ItemFragment(), data.get(position).getId()));
            holder.addDeleteAndEditListener(data.get(position), e -> {
                CacheManager.getManager().setCurrentId(data.get(position).getId());
                NavigationHelper.navigateDown(activity, new EditCollectionOrStorageLocationFragment(), false);
            });
        } else {
            holder.addNewStorageLocationActionListener(e -> NavigationHelper.navigateDown(activity, new AddCollectionOrStorageLocationFragment(), false));
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

    public void setData(List<StorageLocation> newData) {
        newData = newData
                .stream()
                .filter(nd -> nd.getId() == CacheManager.getManager().getCurrentId())
                .collect(Collectors.toList());
        if (data != null) {
            data.clear();
            data.addAll(newData);
        } else {
            data = newData;
        }
        notifyDataSetChanged();
    }

    public static class StorageLocationViewHolder extends RecyclerView.ViewHolder {


        @Nullable
        ImageView addButton;
        private View v;
        @Nullable
        private TextView title;
        @Nullable
        private TextView description;

        public StorageLocationViewHolder(@NonNull View v) {
            super(v);
            title = v.findViewById(R.id.recyclerview_item_wide_title);
            description = v.findViewById(R.id.recyclerview_item_wide_description);
            addButton = v.findViewById(R.id.recyclerview_item_wide_add);
            this.v = v;
        }

        void bind(@NonNull StorageLocation storageLocation) {
            if (title != null) {
                title.setText(storageLocation.getName());
            }
            if (description != null) {
                description.setText(storageLocation.getDescription());
            }
        }

        void addDeleteAndEditListener(StorageLocation storageLocation, View.OnClickListener el) {
            v.setOnLongClickListener(e -> {
                ImageButton delete = v.findViewById(R.id.cardview_delete);
                if (delete.getVisibility() == View.INVISIBLE) {
                    delete.setVisibility(View.VISIBLE);
                    delete.setOnClickListener(dl -> getInstance().delete(storageLocation));
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

        public void addNewStorageLocationActionListener(@NonNull View.OnClickListener listener) {
            if (addButton != null) {
                addButton.setOnClickListener(listener);
            }
        }
    }
}
