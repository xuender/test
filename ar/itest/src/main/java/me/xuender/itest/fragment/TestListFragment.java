package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.xuender.itest.TestActivity;
import me.xuender.itest.model.ITest;

/**
 * 测试列表片段
 * Created by ender on 14-4-6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TestListFragment extends AbstractFragment {

    public List<ITest> tests;

    public ITest findTest(int num) {
        return ITest.findTest(tests, num);
    }

    @Override
    protected ArrayAdapter getAdapter(Context context) {
        initTests(context.getAssets());
        return new TestListAdapter(context, tests);
    }

    public void initTests(AssetManager assetManager) {
        if (tests == null) {
            try {
                InputStream is = assetManager.open("tests.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                JSONArray array = new JSONArray(new String(buffer, "UTF-8"));
                tests = new ArrayList<ITest>();
                for (int i = 0; i < array.length(); i++) {
                    tests.add(new ITest(array.getJSONObject(i)));
                }
                ITest.tests = tests;
            } catch (Exception e) {
                EasyTracker.getInstance(getActivity())
                        .send(MapBuilder.createException(new StandardExceptionParser(getActivity(), null)
                                .getDescription(Thread.currentThread().getName(), e), false)
                                .build());
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ITest test = tests.get(position);
        OnStar onStar = (OnStar) getActivity();
        if (onStar.star() >= test.getStar()) {
            Log.d("打开测试", test.getTitle());
            Intent intent = new Intent();
            intent.setClass(getActivity(), TestActivity.class);
            intent.putExtra("test", test);
            startActivityForResult(intent, TestActivity.RESULT_CODE);
            EasyTracker.getInstance(getActivity()).send(
                    MapBuilder.createEvent("list", "test", test.getTitle(), null).build());
        } else {
            Toast.makeText(getActivity(), "需要 " + test.getStar() + " 星,只有 "
                            + onStar.star() + " 星，数量不够不能测试",
                    Toast.LENGTH_SHORT
            ).show();
            EasyTracker.getInstance(getActivity()).send(
                    MapBuilder.createEvent("list", "no_test", test.getTitle(), null).build());
        }
    }
}