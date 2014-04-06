package me.xuender.itest.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.xuender.itest.R;
import me.xuender.itest.model.ITest;

/**
 * 测试列表适配器
 * Created by ender on 14-4-5.
 */
public class TestListAdapter extends ArrayAdapter<ITest> {
    public TestListAdapter(Context context, List<ITest> tests) {
        super(context, R.layout.test_list, tests);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ITest test = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView summary = (TextView) convertView.findViewById(R.id.summary);
        title.setText(test.getTitle());
        summary.setText(test.getSummary());
        Log.d("测试", test.getTitle());
        return convertView;
    }
}
