package me.xuender.itest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import me.xuender.itest.model.AbstractItem;
import me.xuender.itest.model.Answer;
import me.xuender.itest.model.Conclusion;
import me.xuender.itest.model.ITest;
import me.xuender.itest.model.Question;

/**
 * 测试页面
 * Created by ender on 14-4-5.
 */
public class TestActivity extends Activity {
    public static final int RESULT_CODE = 1;
    private ITest test;
    private TextView title;
    private TextView context;
    private TextView summary;
    private ListView answers;
    private Button run;
    private Button close;
    private int questionNum = 0;
    private AnswerAdapter answerAdapter;
    private int conclusionNum;
    private AdView adView;
    private AdRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
        initListener();

        readItem(test);
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    private void initListener() {
        answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Answer answer = answerAdapter.getItem(position);
                if (answer.getJump() != null) {
                    Question question = test.findQuestion(answer.getJump());
                    readItem(question);
                }
                if (answer.getConclusion() != null) {
                    readItem(answer.getConclusion());
                }
                EasyTracker.getInstance(view.getContext()).send(
                        MapBuilder.createEvent("answer", answer.getTest().getTitle(),
                                answer.getTitle(), null).build()
                );

            }
        });
        run.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = test.getQuestions().get(questionNum);
                readItem(question);
            }
        });
        close.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();
            }
        });
    }

    private void init() {
        Bundle bundle = this.getIntent().getExtras();
        test = (ITest) bundle.getSerializable("test");
        title = (TextView) findViewById(R.id.title);
        context = (TextView) findViewById(R.id.content);
        summary = (TextView) findViewById(R.id.summary);
        answers = (ListView) findViewById(R.id.answers);
        request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB")
                .build();
        adView = (AdView) findViewById(R.id.adView);
        //adView.setVisibility(View.GONE);
//        adView.setAdUnitId("a15349041ec2786");
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                Log.d("广告", "收到广告");
                //adView.setVisibility(View.VISIBLE);
                EasyTracker.getInstance(getParent()).send(
                        MapBuilder.createEvent("ad", "ok", "ok", null).build());
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("广告异常", "" + errorCode);
                //adView.setVisibility(View.GONE);
                EasyTracker.getInstance(getParent()).send(
                        MapBuilder.createEvent("ad", "error", "code_" + errorCode, null).build());
            }
        });
        adView.loadAd(request);
        run = (Button) findViewById(R.id.run);
        close = (Button) findViewById(R.id.close);
        answerAdapter = new AnswerAdapter(this);
        answers.setAdapter(answerAdapter);
        this.setTitle(test.getTitle());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void readItem(AbstractItem item) {
        Log.d("readItem", item.getTitle());
        title.setText(item.getTitle());
        context.setText(item.getContent());
        summary.setText(item.getSummary());
        context.setVisibility(View.GONE);
        answers.setVisibility(View.GONE);
        run.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
        adView.setVisibility(View.GONE);
        if (item instanceof ITest) {
            context.setVisibility(View.VISIBLE);
            run.setVisibility(View.VISIBLE);
        }
        if (item instanceof Question) {
            Question question = (Question) item;
            adView.setVisibility(View.VISIBLE);
            answers.setVisibility(View.VISIBLE);
            answerAdapter.clear();
            answerAdapter.addAll(question.getAnswers());
            Log.d("答案数量", String.valueOf(answerAdapter.getCount()));
//            adView.loadAd(request);
        }
        if (item instanceof Conclusion) {
            context.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
            conclusionNum = item.getNum();
        }
    }

    private void end() {
        Log.d("end", test.getNum() + "");
        Intent intent = this.getIntent();
        intent.putExtra("conclusion", conclusionNum);
        intent.putExtra("test", test);
        EasyTracker.getInstance(this).send(
                MapBuilder.createEvent("test", "on", "end", null).build());
        this.setResult(RESULT_CODE, intent);//给上一个Activity返回结果
        this.finish();
    }
}