package me.xuender.itest;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
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

import me.xuender.itest.fragment.HistoryFragment;
import me.xuender.itest.fragment.OnHistory;
import me.xuender.itest.fragment.OnStar;
import me.xuender.itest.fragment.TestListFragment;
import me.xuender.itest.model.ITest;

/**
 * 测试主界面
 */
public class MainActivity extends ActionBarActivity
        implements OnHistory, OnStar, ActionBar.TabListener {
    //    private FragmentManager fragmentManager;
    private List<ButtonItem> buttons = new ArrayList<ButtonItem>();
    private SharedPreferences testSp;
    private Set<Integer> stars = new HashSet<Integer>();
    private TextView starView;
    private SoundPool soundPool;
    private SharedPreferences prefs;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        testSp = this.getSharedPreferences("test", Context.MODE_PRIVATE);
        readTestNums();
        getActionBar().show();
        starView = (TextView) findViewById(R.id.star);
        starView.setText(String.valueOf(stars.size()));
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.star, 1);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        buttons.add(new ButtonItem(new TestListFragment(), "测试"));
        buttons.add(new ButtonItem(new HistoryFragment(), "记录"));
//        buttons.add(new ButtonItem(new SettingFragment(), findViewById(R.id.layout_setting),
//                (ImageView) findViewById(R.id.img_setting), (TextView) findViewById(R.id.text_setting)));
//
//        fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        for (ButtonItem bi : buttons) {
//            transaction.add(R.id.content, bi.getFragment());
//            bi.getLayout().setOnClickListener(this);
//        }
//        transaction.commit();
//        setTabSelection(buttons.get(0));
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
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
        stars.clear();
        SharedPreferences.Editor editor = testSp.edit();
        editor.putString("testNums", "[]");
        editor.commit();
        starView.setText(String.valueOf(stars.size()));
        Toast.makeText(this, "星星数量清零", Toast.LENGTH_SHORT).show();
//        for (ButtonItem bi : buttons) {
//            if (bi.getFragment() instanceof TestListFragment) {
//                ((AbstractFragment) bi.getFragment()).reset();
//            }
//        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
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
}