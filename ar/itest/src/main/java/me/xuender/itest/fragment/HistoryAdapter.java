package me.xuender.itest.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import me.xuender.itest.R;
import me.xuender.itest.model.History;
import me.xuender.itest.model.ITest;

/**
 * 测试历史适配器
 * Created by ender on 14-4-5.
 */
public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(Context context, List<History> histories) {
        super(context, R.layout.test_list, histories);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        History history = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView conclusion = (TextView) convertView.findViewById(R.id.conclusion);
        TextView createAt = (TextView) convertView.findViewById(R.id.createAt);
        title.setText(history.getTest().getTitle());
        conclusion.setText(history.getConclusion().getTitle());
        createAt.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(history.getCreateAt()));
        return convertView;
    }
}
