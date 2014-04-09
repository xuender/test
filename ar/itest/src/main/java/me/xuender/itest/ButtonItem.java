package me.xuender.itest;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 按钮相关对象
 * Created by ender on 14-4-6.
 */
public class ButtonItem {
    private Fragment fragment;
    private String title;

    public ButtonItem(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
