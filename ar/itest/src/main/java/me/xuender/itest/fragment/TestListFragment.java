package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
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
    protected ListAdapter getAdapter(Context context) {
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
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ITest test = tests.get(position);
        Log.d("打开测试", test.getTitle());
        Intent intent = new Intent();
        intent.setClass(getActivity(), TestActivity.class);
        intent.putExtra("test", test);
        startActivityForResult(intent, TestActivity.RESULT_CODE);
    }
}