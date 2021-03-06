package me.xuender.itest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.xuender.itest.model.Answer;
import me.xuender.utils.StringUtils;

/**
 * 答案适配器
 * Created by ender on 14-4-5.
 */
public class AnswerAdapter extends ArrayAdapter<Answer> {
    public AnswerAdapter(Context context) {
        super(context, R.layout.answer);
    }

    public AnswerAdapter(Context context, List<Answer> answers) {
        super(context, R.layout.answer, answers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Answer answer = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.answer_list, null);
        }
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(answer.getTitle());
        TextView summary = (TextView) convertView.findViewById(R.id.summary);
        if (StringUtils.isEmpty(answer.getSummary())) {
            summary.setVisibility(View.GONE);
        } else {
            summary.setVisibility(View.VISIBLE);
        }
        summary.setText(answer.getSummary());
        Log.d("问题", answer.getTitle());
        return convertView;
    }
}
