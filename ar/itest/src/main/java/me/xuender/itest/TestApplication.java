package me.xuender.itest;

import android.app.Application;

import me.xuender.itest.fragment.OnHistory;
import me.xuender.itest.fragment.OnStar;

/**
 * Created by ender on 14-4-12.
 */
public class TestApplication extends Application {
    private OnHistory onHistory;
    private OnStar onStar;

    public OnHistory getOnHistory() {
        return onHistory;
    }

    public void setOnHistory(OnHistory onHistory) {
        this.onHistory = onHistory;
    }

    public OnStar getOnStar() {
        return onStar;
    }

    public void setOnStar(OnStar onStar) {
        this.onStar = onStar;
    }
}
