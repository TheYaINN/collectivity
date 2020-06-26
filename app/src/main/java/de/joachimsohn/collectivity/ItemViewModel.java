package de.joachimsohn.collectivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.joachimsohn.collectivity.db.ItemRepository;
import de.joachimsohn.collectivity.db.dao.impl.Item;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository repository;
    private LiveData<List<Item>> allItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);

        repository = new ItemRepository(application);
        allItems = repository.getAllItems();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(Item item) {
        repository.update(item);
    }

    public void delete(Item item) {
        repository.delete(item);
    }

    public void deleteAll() {
        repository.deleteAllItems();
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }
}
