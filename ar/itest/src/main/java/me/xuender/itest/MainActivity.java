package me.xuender.itest;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.xuender.itest.fragment.HistoryFragment;
import me.xuender.itest.fragment.OnHistory;
import me.xuender.itest.fragment.SettingFragment;
import me.xuender.itest.fragment.TestListFragment;
import me.xuender.itest.model.ITest;

/**
 * 测试主界面
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, OnHistory {
    private FragmentManager fragmentManager;
    private List<ButtonItem> buttons = new ArrayList<ButtonItem>();

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        buttons.add(new ButtonItem(new TestListFragment(), findViewById(R.id.layout_test),
                (ImageView) findViewById(R.id.img_test), (TextView) findViewById(R.id.text_test)));
        buttons.add(new ButtonItem(new HistoryFragment(), findViewById(R.id.layout_history),
                (ImageView) findViewById(R.id.img_history), (TextView) findViewById(R.id.text_history)));
        buttons.add(new ButtonItem(new SettingFragment(), findViewById(R.id.layout_setting),
                (ImageView) findViewById(R.id.img_setting), (TextView) findViewById(R.id.text_setting)));

        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (ButtonItem bi : buttons) {
            transaction.add(R.id.content, bi.getFragment());
            bi.getLayout().setOnClickListener(this);
        }
        transaction.commit();
        setTabSelection(buttons.get(0));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setTabSelection(ButtonItem bi) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(bi.getFragment());
        bi.getText().setTextAppearance(this, R.style.SelectedText);
        bi.getLayout().setBackgroundColor(Color.TRANSPARENT);
        for (ButtonItem item : buttons) {
            if (item != bi) {
                transaction.hide(item.getFragment());
                item.getText().setTextAppearance(this, R.style.UnSelectedText);
                item.getLayout().setBackgroundColor(R.color.button);
            }
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        for (ButtonItem bi : buttons) {
            if (bi.getLayout() == v) {
                setTabSelection(bi);
            }
        }
    }

    private HistoryFragment findHistoryFragment() {
        for (ButtonItem bi : buttons) {
            if (bi.getFragment() instanceof HistoryFragment) {
                return (HistoryFragment) bi.getFragment();
            }
        }
        return null;
    }

    @Override
    public void clean() {
        findHistoryFragment().clean();
    }

    @Override
    public void add(ITest test, int conclusion) {
        findHistoryFragment().add(test, conclusion);
    }
}