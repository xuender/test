package me.xuender.itest;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.xuender.itest.fragment.AbstractFragment;
import me.xuender.itest.fragment.HistoryFragment;
import me.xuender.itest.fragment.OnHistory;
import me.xuender.itest.fragment.OnStar;
import me.xuender.itest.fragment.SettingFragment;
import me.xuender.itest.fragment.TestListFragment;
import me.xuender.itest.model.ITest;

/**
 * 测试主界面
 */
public class MainActivity extends FragmentActivity
        implements View.OnClickListener, OnHistory, OnStar {
    private FragmentManager fragmentManager;
    private List<ButtonItem> buttons = new ArrayList<ButtonItem>();
    private SharedPreferences testSp;
    private Set<Integer> stars = new HashSet<Integer>();
    private TextView starView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testSp = this.getSharedPreferences("test", Context.MODE_PRIVATE);
        readTestNums();
        starView = (TextView) findViewById(R.id.star);
        starView.setText(String.valueOf(stars.size()));
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

    private void readTestNums() {
        String testNumsStr = testSp.getString("testNums", "[]");
        try {
            JSONArray ja = new JSONArray(testNumsStr);
            for (int i = 0; i < ja.length(); i++) {
                stars.add(ja.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        int size = stars.size();
        stars.add(test.getNum());
        if (size < stars.size()) {
            SharedPreferences.Editor editor = testSp.edit();
            JSONArray ja = new JSONArray();
            for (Integer num : stars) {
                ja.put(num);
            }
            Log.d("保存", ja.toString());
            editor.putString("testNums", ja.toString());
            editor.commit();
            starView.setText(String.valueOf(stars.size()));
            Toast.makeText(this, "获得一个星星", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isStar(int testNum) {
        for (Integer i : stars) {
            Log.d("star", String.valueOf(i));
        }
        Log.d("isStar", String.valueOf(testNum));
        return stars.contains(testNum);
    }

    @Override
    public int star() {
        return stars.size();
    }

    @Override
    public void zero() {
        stars.clear();
        SharedPreferences.Editor editor = testSp.edit();
        editor.putString("testNums", "[]");
        editor.commit();
        starView.setText(String.valueOf(stars.size()));
        Toast.makeText(this, "星星数量清零", Toast.LENGTH_SHORT).show();
        for (ButtonItem bi : buttons) {
            if (bi.getFragment() instanceof TestListFragment) {
                ((AbstractFragment) bi.getFragment()).reset();
            }
        }
    }
}