package de.joachimsohn.collectivity.ui.activities;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import de.joachimsohn.collectivity.R;
import de.joachimsohn.collectivity.manager.impl.CacheManager;
import de.joachimsohn.collectivity.manager.search.SearchType;

public class NavigationHelper {


    public static boolean navigateToFragment(Activity activity, Fragment newFragment) {
        FragmentManager fragmentManager = ((MainActivity) activity).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.default_container, newFragment);
        fragmentTransaction.addToBackStack(newFragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
        return true;
    }

    public static boolean navigateToFragmentWithBackOption(Activity activity, Fragment newFragment) {
        return navigateToFragment(activity, newFragment);
    }

    public static boolean navigateDownToFragment(Activity activity, Fragment newFragment, long id) {
        CacheManager.getManager().setLevel(CacheManager.Direction.DOWN);
        CacheManager.getManager().setCurrentId(id);
        updateToolbar(activity);
        return navigateToFragment(activity, newFragment);
    }

    public static boolean navigateDownToFragment(Activity activity, Fragment newFragment) {
        CacheManager.getManager().setLevel(CacheManager.Direction.DOWN);
        updateToolbar(activity);
        return navigateToFragment(activity, newFragment);
    }

    public static boolean navigateUpToFragment(Activity activity, Fragment newFragment) {
        CacheManager.getManager().setLevel(CacheManager.Direction.UP);
        updateToolbar(activity);
        return navigateToFragment(activity, newFragment);
    }

    private static void updateToolbar(Activity activity) {
        if (CacheManager.getManager().getCurrentCacheLevel() == SearchType.COLLECTION) {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        } else {
            ((MainActivity) activity).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        }
    }

}
