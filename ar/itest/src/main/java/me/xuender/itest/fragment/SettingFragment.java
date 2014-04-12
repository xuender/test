package me.xuender.itest.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import me.xuender.itest.R;
import me.xuender.itest.TestApplication;

/**
 * 设置片段
 * Created by ender on 14-4-6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingFragment extends PreferenceFragment implements
        Preference.OnPreferenceClickListener {
    private Preference clean;
    private Preference star;
    private TestApplication testApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        clean = findPreference("clean");
        clean.setOnPreferenceClickListener(this);
        star = findPreference("star");
        star.setOnPreferenceClickListener(this);
        testApplication = (TestApplication) getActivity().getApplication();
    }

    @Override
    public boolean onPreferenceClick(final Preference preference) {
        if ("clean".equalsIgnoreCase(preference.getKey())) {
            new AlertDialog.Builder(preference.getContext())
                    .setTitle("清空记录")
                    .setMessage("请问您是否要清空测试记录?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            testApplication.getOnHistory().clean();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
        if ("star".equalsIgnoreCase(preference.getKey())) {
            new AlertDialog.Builder(preference.getContext())
                    .setTitle("重置星星")
                    .setMessage("请问您是否要将星星数量清零?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            testApplication.getOnStar().zero();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
        return false;
    }
}