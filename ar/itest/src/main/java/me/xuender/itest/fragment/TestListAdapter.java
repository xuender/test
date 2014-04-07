package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ITest test = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.test_list, null);
        }
        OnStar onStar = (OnStar) getContext();
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView star = (TextView) convertView.findViewById(R.id.star);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
        if (test.getStar() <= onStar.star()) {
            convertView.setBackgroundColor(Color.TRANSPARENT);
            //title.setTextAlignment(R.style.testTitle);
            //title.setTextColor(R.color.title);
        } else {
            convertView.setBackgroundColor(R.color.titleOff);
            //title.setTextAlignment(R.style.testTitleOff);
            // title.setTextColor(R.color.titleOff);
        }
        if (onStar.isStar(test.getNum())) {
            image.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            image.setImageResource(android.R.drawable.btn_star_big_off);
        }
        title.setText(test.getTitle());
        star.setText(String.valueOf(test.getStar()));
        Log.d("测试", test.getTitle());
        return convertView;
    }
}
