package me.xuender.itest.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ender on 14-4-5.
 */
public class ITest extends AbstractItem {
    private String[] tags;
    private List<Question> questions = new ArrayList<Question>();
    private List<Conclusion> conclusions = new ArrayList<Conclusion>();

    public ITest(JSONObject obj) throws JSONException {
        super(obj);
        if (obj.has("tags")) {
            JSONArray tag = obj.getJSONArray("tags");
            List<String> ts = new ArrayList<String>();
            for (int i = 0; i < tag.length(); i++) {
                ts.add(tag.getString(i));
            }
            tags = ts.toArray(new String[ts.size()]);
        }
        if (obj.has("conclusions")) {
            JSONArray cs = obj.getJSONArray("conclusions");
            for (int i = 0; i < cs.length(); i++) {
                conclusions.add(new Conclusion(cs.getJSONObject(i), this));
            }
        }
        if (obj.has("questions")) {
            JSONArray qs = obj.getJSONArray("questions");
            for (int i = 0; i < qs.length(); i++) {
                questions.add(new Question(qs.getJSONObject(i), this));
            }
        }
    }

    /**
     * 根据编号查找问题
     *
     * @param num
     * @return
     */
    public Question findQuestion(int num) {
        for (Question q : questions) {
            if (q.getNum() == num) {
                return q;
            }
        }
        return questions.get(0);
    }

    /**
     * 根据编号查找结论
     *
     * @param num
     * @return
     */
    public Conclusion findConclusion(int num) {
        for (Conclusion q : conclusions) {
            if (q.getNum() == num) {
                return q;
            }
        }
        return conclusions.get(0);
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Conclusion> getConclusions() {
        return conclusions;
    }

    public void setConclusions(List<Conclusion> conclusions) {
        this.conclusions = conclusions;
    }
}
