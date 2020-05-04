package de.joachimsohn.collectivity.ui.views;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class LoginView implements Views {

    @Override
    public RelativeLayout getview(@NonNull Context context) {
        return createView(context);
    }

    private RelativeLayout createView(Context context) {
        RelativeLayout root = new RelativeLayout(context);

        return root;
    }
}
