package me.xuender.itest.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题
 * Created by ender on 14-4-5.
 */
public class Question extends AbstractItem {
    private List<Answer> answers = new ArrayList<Answer>();

    public Question(JSONObject obj, ITest test) throws JSONException {
        super(obj, test);
        if (obj.has("answers")) {
            JSONArray as = obj.getJSONArray("answers");
            for (int i = 0; i < as.length(); i++) {
                answers.add(new Answer(as.getJSONObject(i), test));
            }
        }
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
