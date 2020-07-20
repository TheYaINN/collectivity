package de.joachimsohn.collectivity.manager.sort;

import de.joachimsohn.collectivity.R;
import lombok.Getter;

@Getter
public enum SortDirection {

    NONE("Aufsteigend", R.drawable.empty_icon),
    ASCENDING("Aufsteigend", R.drawable.ic_arrow_up),
    DESCENDING("Absteigend", R.drawable.ic_arrow_down);

    private String representableName;
    private int icon;

    SortDirection(String representableName, int icon) {
        this.representableName = representableName;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getRepresentableName() {
        return representableName;
    }

    public void setRepresentableName(String representableName) {
        this.representableName = representableName;
    }

}
