package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.xuender.itest.R;
import me.xuender.itest.TestActivity;
import me.xuender.itest.model.ITest;

/**
 * 带测试列表的可进行测试的片段
 * Created by ender on 14-4-6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class AbstractFragment extends Fragment
        implements AdapterView.OnItemClickListener {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View listLayout = inflater.inflate(R.layout.list_layout,
                container, false);
        listView = (ListView) listLayout.findViewById(R.id.list);
        listView.setAdapter(getAdapter(listView.getContext()));
        listView.setOnItemClickListener(this);
        return listLayout;
    }

    protected abstract ArrayAdapter getAdapter(Context context);

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case TestActivity.RESULT_CODE:
                Bundle bundle = data.getExtras();//取得来自B页面的数据，并显示到画面
                int conclusion = bundle.getInt("conclusion");
                ITest test = (ITest) bundle.getSerializable("test");
                ((OnHistory) getActivity()).add(test, conclusion);
                Log.d("测试", String.valueOf(test));
                Log.d("测试完成", String.valueOf(conclusion));
                Log.d("count:", "" + listView.getAdapter().getCount());
                reset();

        }
    }

    public void reset() {
        listView.invalidateViews();
    }
}