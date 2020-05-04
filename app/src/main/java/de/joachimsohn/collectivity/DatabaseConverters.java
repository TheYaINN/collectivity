package de.joachimsohn.collectivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.time.OffsetDateTime;

public class DatabaseConverters {


    @TypeConverter
    public static OffsetDateTime toOffsetDateTime(String time) {
        if (time.isEmpty()) {
            return OffsetDateTime.now();
        }
        return OffsetDateTime.parse(time);
    }

    @TypeConverter
    public static String fromOffsetDateTime(@Nullable OffsetDateTime date) {
        if (date == null) {
            return "";
        }
        return date.toString();
    }

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bmp) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] img) {
        return BitmapFactory.decodeByteArray(img, 0, img.length);
    }


}
