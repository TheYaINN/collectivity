package de.joachimsohn.collectivity.manager.sort;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import de.joachimsohn.collectivity.manager.impl.CollectionManager;

public class SorterV1 implements SortStrategy {


    @Override
    public void sort(SortCriteria sortCriteria, SortDirection direction, SortableObject sortable) {
        List<Collection> collection = CollectionManager.getManager().getCollection();
        for (Collection c : collection) {

        }
        //TODO: sort the shit out the collections
    }
}
