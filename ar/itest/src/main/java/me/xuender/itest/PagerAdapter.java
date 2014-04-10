package me.xuender.itest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ender on 14-4-10.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<ButtonItem> buttons;

    public PagerAdapter(android.support.v4.app.FragmentManager fm, List<ButtonItem> buttons) {
        super(fm);
        this.buttons = buttons;
    }

    @Override
    public Fragment getItem(int position) {
        return buttons.get(position).getFragment();
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return buttons.get(position).getTitle();
    }
}