package me.xuender.itest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;

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
import me.xuender.itest.fragment.SettingActivity;
import me.xuender.itest.fragment.TestListFragment;
import me.xuender.itest.model.ITest;

/**
 * 测试主界面
 */
public class MainActivity extends ActionBarActivity
        implements OnHistory, OnStar, ActionBar.TabListener {
    private List<ButtonItem> buttons = new ArrayList<ButtonItem>();
    private SharedPreferences testSp;
    private Set<Integer> stars = new HashSet<Integer>();
    private SoundPool soundPool;
    private SharedPreferences prefs;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private ActionBar actionBar;
    private MenuItem star;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        数据初始化();
        声音初始化();
        滑动初始化();
        actionBar.setDisplayShowTitleEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.star:
                Toast.makeText(this, "当前共有" + stars.size() + "颗星", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Intent intent = new Intent();
                intent.setClass(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        star = menu.getItem(0);
        star.setTitle(String.valueOf(stars.size()));
        return super.onCreateOptionsMenu(menu);
    }

    private void 数据初始化() {
        testSp = this.getSharedPreferences("test", Context.MODE_PRIVATE);
        readTestNums();
        buttons.add(new ButtonItem(new TestListFragment(), getResources().getString(R.string.test)));
        buttons.add(new ButtonItem(new HistoryFragment(), getResources().getString(R.string.history)));
    }

    private void 声音初始化() {
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.star, 1);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void 滑动初始化() {
        getActionBar().show();
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), buttons);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(pagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    private void readTestNums() {
        String testNumsStr = testSp.getString("testNums", "[]");
        try {
            JSONArray ja = new JSONArray(testNumsStr);
            for (int i = 0; i < ja.length(); i++) {
                stars.add(ja.getInt(i));
            }
            EasyTracker.getInstance(this).send(
                    MapBuilder.createEvent("list", "star", "num", Long.valueOf(stars.size()))
                            .build()
            );
        } catch (JSONException e) {
            EasyTracker.getInstance(this)
                    .send(MapBuilder.createException(new StandardExceptionParser(this, null)
                            .getDescription(Thread.currentThread().getName(), e), false)
                            .build());
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
        EasyTracker.getInstance(this).send(
                MapBuilder.createEvent("setting", "on", "clean", null).build());
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
            star.setTitle(String.valueOf(stars.size()));
            Toast.makeText(this, "获得一个星星", Toast.LENGTH_SHORT).show();
            if (prefs.getBoolean("sound", true)) {
                soundPool.play(1, 1, 1, 0, 0, 1);
            }
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
        int count = stars.size();
        stars.clear();
        SharedPreferences.Editor editor = testSp.edit();
        editor.putString("testNums", "[]");
        editor.commit();
        star.setTitle(String.valueOf(stars.size()));
        Toast.makeText(this, "星星数量清零", Toast.LENGTH_SHORT).show();
        for (ButtonItem bi : buttons) {
            if (bi.getFragment() instanceof TestListFragment) {
                ((AbstractFragment) bi.getFragment()).reset();
            }
        }
        EasyTracker.getInstance(this).send(
                MapBuilder.createEvent("setting", "on", "zero", Long.valueOf(count)).build());
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }
}