package de.joachimsohn.collectivity.db;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import de.joachimsohn.collectivity.db.dao.Condition;

public class Converter {

    @TypeConverter
    public Date toDate(String date) {
        Date parse = Date.from(Instant.now());
        try {
            parse = DateFormat.getInstance().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    @TypeConverter
    public String fromDate(Date date) {
        return date.toString();
    }

    @TypeConverter
    public byte[] fromBitmap(Bitmap bmp) {
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
        return Condition.valueOf(condition);
    }

    @TypeConverter
    public String fromCondition(Condition condition) {
        return condition.toString();
    }

}
