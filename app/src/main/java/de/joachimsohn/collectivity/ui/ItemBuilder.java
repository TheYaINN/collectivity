package de.joachimsohn.collectivity.ui;

import android.graphics.Bitmap;

import java.math.BigDecimal;
import java.util.Calendar;

import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.db.dao.impl.Item;

public class ItemBuilder {

    Item item;

    public ItemBuilder(String name, Condition condition, String position, long storageLocationId) {
        item = new Item(name, Calendar.getInstance(), condition, position, storageLocationId);
    }

    public ItemBuilder addAmount(String amount) {
        if (!amount.isEmpty()) {
            item.setAmount(Integer.parseInt(amount));
        }
        return this;
    }

    public Item build() {
        return item;
    }

    public ItemBuilder addDescription(String description) {
        if (!description.isEmpty()) {
            item.setDescription(description);
        }
        return this;
    }

    public ItemBuilder addEAN(String ean) {
        if (!ean.isEmpty()) {
            item.setEan(ean);
        }
        return this;
    }

    public ItemBuilder addIcon(Bitmap icon) {
        if (icon != null) {
            item.setIcon(icon);
        }
        return this;
    }

    public ItemBuilder addValue(String value) {
        if (!value.isEmpty()) {
            BigDecimal bigDecimal = BigDecimal.valueOf(Double.parseDouble(value));
            if (bigDecimal.compareTo(BigDecimal.ZERO) > 0) {
                item.setValue(bigDecimal);
            }
        }
        return this;
    }

    public ItemBuilder addBuyDate(Calendar buyDate) {
        item.setBuyDate(buyDate);
        return this;
    }
}
