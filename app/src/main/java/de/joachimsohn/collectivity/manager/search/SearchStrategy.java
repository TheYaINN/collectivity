package de.joachimsohn.collectivity.manager.search;

import java.util.List;

import de.joachimsohn.collectivity.db.dao.impl.Collection;
import lombok.NonNull;

public interface SearchStrategy {

    @NonNull List<Collection> getResultsFor(String searchValue);

}
