package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.xuender.itest.R;

/**
 * 设置片段
 * Created by ender on 14-4-6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.list_layout,
                container, false);
        return settingLayout;
    }
}
