package de.joachimsohn.collectivity;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.joachimsohn.collectivity.db.AppDataBase;
import de.joachimsohn.collectivity.db.dao.ItemDAO;
import de.joachimsohn.collectivity.db.dao.impl.Item;
import de.joachimsohn.collectivity.dbconnector.DataBaseConnector;

public class ItemViewModel extends AndroidViewModel {

    private final ItemDAO itemDAO;
    private ExecutorService service;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemDAO = AppDataBase.getInstance(application.getApplicationContext()).itemDAO();
        service = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Item>> getAllItems() {
        return DataBaseConnector.getInstance().getAllItems();
    }

    public void insert(Item item) {
        DataBaseConnector.getInstance().insert(item);
    }

    public void update(Item item) {
        DataBaseConnector.getInstance().update(item);
    }

    public void delete(Item item) {
        DataBaseConnector.getInstance().delete(item);
    }

    public void deleteAll() {
        DataBaseConnector.getInstance().deleteAllItems();
    }

}
