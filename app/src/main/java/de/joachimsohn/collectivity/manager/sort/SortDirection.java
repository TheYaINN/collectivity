package de.joachimsohn.collectivity.manager.sort;

import de.joachimsohn.collectivity.R;

public enum SortDirection {

    NONE("Aufsteigend", 0), //TODO: add empty icon
    ASCENDING("Aufsteigend", R.drawable.ic_arrow_up_black),
    DESCENDING("Absteigend", R.drawable.ic_arrow_down_black);

    //TODO: add getter and setter from lombok
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
