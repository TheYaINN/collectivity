package de.joachimsohn.collectivity.db;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import de.joachimsohn.collectivity.db.dao.Condition;
import de.joachimsohn.collectivity.db.dao.impl.Tag;

public class Converter {

    @TypeConverter
    public Calendar toCalendar(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    @TypeConverter
    public String fromCalendar(Calendar date) {
        return date.toString();
    }

    @TypeConverter
    public byte[] fromBitmap(Bitmap bmp) {
        if (bmp == null) {
            return new byte[0];
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

    @TypeConverter
    public Bitmap toBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }

    @TypeConverter
    public BigDecimal fromLong(Long value) {
        return value == null ? null : new BigDecimal(value).divide(new BigDecimal(100));
    }

    @TypeConverter
    public Long toLong(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        } else {
            return bigDecimal.multiply(new BigDecimal(100)).longValue();
        }
    }

    @TypeConverter
    public Condition toCondition(String condition) {
        return Arrays.stream(Condition.values()).filter(b -> b.toString().equalsIgnoreCase(condition)).findFirst().get();
    }

    @TypeConverter
    public String fromCondition(Condition condition) {
        return condition.toString();
    }

    @TypeConverter
    public String fromTag(Tag tag) {
        return tag.toString();
    }

    @TypeConverter
    public Tag toTag(String tag) {
        return new Tag(tag);
    }
}
