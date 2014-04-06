package me.xuender.itest;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 按钮相关对象
 * Created by ender on 14-4-6.
 */
public class ButtonItem {
    private ImageView image;
    private TextView text;
    private View layout;
    private Fragment fragment;

    public ButtonItem(Fragment fragment, View layout, ImageView image, TextView text) {
        this.fragment = fragment;
        this.layout = layout;
        this.image = image;
        this.text = text;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public View getLayout() {
        return layout;
    }

    public void setLayout(View layout) {
        this.layout = layout;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
