package me.xuender.itest;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import me.xuender.itest.model.ITest;

/**
 * 测试列表
 */
public class TestListActivity extends ListActivity {
    private final static int REQUEST_CODE = 1;
    static List<ITest> tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTests();
        setListAdapter(new TestListAdapter(this, tests));
    }

    private void initTests() {
        if (tests == null) {
            try {
                InputStream is = getAssets().open("tests.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                JSONArray array = new JSONArray(new String(buffer, "UTF-8"));
                tests = new ArrayList<ITest>();
                for (int i = 0; i < array.length(); i++) {
                    tests.add(new ITest(array.getJSONObject(i)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("打开测试", tests.get(position).getTitle());
        Intent intent = new Intent();
        intent.setClass(this, TestActivity.class);
        intent.putExtra("num", position);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle bundle = data.getExtras();//取得来自B页面的数据，并显示到画面
                boolean isEnd = bundle.getBoolean("end");
                Log.d("测试完成", String.valueOf(isEnd));
        }
    }
}
