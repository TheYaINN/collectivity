package de.joachimsohn.collectivity.ui.activities;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.CacheManager;

import static de.joachimsohn.collectivity.manager.CacheManager.CacheDirection.DOWN;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheDirection.UP;
import static de.joachimsohn.collectivity.manager.CacheManager.CacheLevel.COLLECTION;

public class NavigationHelper {

    public static boolean onStartReplace(Activity activity, Fragment newFragment) {
        CacheManager.getManager().loadCollectionsOnStartup();
        return navigateToFragment(activity, newFragment, 0, 0);
    }

    private static boolean navigateToFragment(Activity activity, Fragment newFragment, int inAnim, int outAnim) {
        FragmentManager fragmentManager = ((MainActivity) activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(inAnim, outAnim);
        fragmentTransaction.replace(R.id.default_container, newFragment);
        fragmentTransaction.addToBackStack(newFragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        return true;
    }

    public static boolean navigateDown(Activity activity, Fragment newFragment, boolean isHome) {
        if (isHome) {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        } else {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        }
        return navigateToFragment(activity, newFragment, R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    public static boolean navigateUp(Activity activity, Fragment newFragment, boolean isHome) {
        if (isHome) {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        } else {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        }
        return navigateToFragment(activity, newFragment, R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    public static boolean navigateRight(Activity activity, Fragment newFragment, long id) {
        CacheManager.getManager().setCacheLevel(DOWN, id);
        updateToolbar(activity);
        return navigateToFragment(activity, newFragment, R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static boolean navigateLeft(Activity activity, Fragment newFragment, long id) {
        CacheManager.getManager().setCacheLevel(UP, id);
        updateToolbar(activity);
        return navigateToFragment(activity, newFragment, R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private static void updateToolbar(Activity activity) {
        if (CacheManager.getManager().getCurrentCacheLevel() == COLLECTION) {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        } else {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        }
    }

}
