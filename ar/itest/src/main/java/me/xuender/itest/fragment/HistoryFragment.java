package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.xuender.itest.TestActivity;
import me.xuender.itest.model.History;
import me.xuender.itest.model.ITest;

/**
 * 测试历史片段
 * Created by ender on 14-4-6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HistoryFragment extends AbstractFragment implements OnHistory {
    private List<History> histories = new ArrayList<History>();
    private HistoryAdapter historyAdapter;
    private SharedPreferences testSp;

    @Override
    protected ArrayAdapter getAdapter(Context context) {
        if (historyAdapter == null) {
            testSp = context.getSharedPreferences("test", Context.MODE_PRIVATE);
            String json = testSp.getString("histories", "[]");
            Log.d("读取", json);
            try {
                JSONArray ja = new JSONArray(json);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    histories.add(new History(jo.getInt("test"), jo.getInt("conclusion")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            historyAdapter = new HistoryAdapter(context, histories);
        }
        return historyAdapter;
    }

    @Override
    public void clean() {
        histories.clear();
        saveHistory();
        Toast.makeText(getActivity(), "测试记录已经被清空.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void add(ITest test, int conclusion) {
        historyAdapter.insert(new History(test, conclusion), 0);
        saveHistory();
    }

    private void saveHistory() {
        SharedPreferences.Editor editor = testSp.edit();
        JSONArray ja = new JSONArray();
        try {
            for (History h : histories) {
                JSONObject jo = new JSONObject();
                jo.put("test", h.getTest().getNum());
                jo.put("conclusion", h.getConclusion().getNum());
                ja.put(jo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("保存", ja.toString());
        editor.putString("histories", ja.toString());
        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        History history = histories.get(position);
        Log.d("打开历史测试", history.getTest().getTitle());
        Intent intent = new Intent();
        intent.setClass(getActivity(), TestActivity.class);
        intent.putExtra("test", history.getTest());
        startActivityForResult(intent, TestActivity.RESULT_CODE);
    }
}