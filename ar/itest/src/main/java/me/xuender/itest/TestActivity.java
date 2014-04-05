package me.xuender.itest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import me.xuender.itest.model.AbstractItem;
import me.xuender.itest.model.Answer;
import me.xuender.itest.model.Conclusion;
import me.xuender.itest.model.ITest;
import me.xuender.itest.model.Question;

/**
 * 测试
 * Created by ender on 14-4-5.
 */
public class TestActivity extends Activity {
    private ITest test;
    private TextView title;
    private TextView context;
    private TextView summary;
    private ListView answers;
    private Button run;
    private Button close;
    private int questionNum = 0;
    private AnswerAdapter answerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Bundle bundle = this.getIntent().getExtras();
        int num = bundle.getInt("num");
        test = TestListActivity.tests.get(num);

        this.setTitle(test.getTitle());
        title = (TextView) findViewById(R.id.title);
        context = (TextView) findViewById(R.id.context);
        summary = (TextView) findViewById(R.id.summary);
        answers = (ListView) findViewById(R.id.answers);
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
            }
        });
        run = (Button) findViewById(R.id.run);
        run.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Question question = test.getQuestions().get(questionNum);
                readItem(question);
            }
        });
        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();
            }
        });
        answerAdapter = new AnswerAdapter(this);
        answers.setAdapter(answerAdapter);
        readItem(test);
    }

    private void readItem(AbstractItem item) {
        Log.d("readItem", item.getTitle());
        title.setText(item.getTitle());
        context.setText(item.getContext());
        summary.setText(item.getSummary());
        context.setVisibility(View.GONE);
        answers.setVisibility(View.GONE);
        run.setVisibility(View.GONE);
        close.setVisibility(View.GONE);
        if (item instanceof ITest) {
            context.setVisibility(View.VISIBLE);
            run.setVisibility(View.VISIBLE);
        }
        if (item instanceof Question) {
            Question question = (Question) item;
            answers.setVisibility(View.VISIBLE);
            answerAdapter.clear();
            answerAdapter.addAll(question.getAnswers());
            Log.d("答案数量", String.valueOf(answerAdapter.getCount()));
        }
        if (item instanceof Conclusion) {
            context.setVisibility(View.VISIBLE);
            close.setVisibility(View.VISIBLE);
        }
    }

    private void end() {
        this.setResult(RESULT_OK, this.getIntent().putExtra("end", true));//给上一个Activity返回结果
        this.finish();
    }
}
