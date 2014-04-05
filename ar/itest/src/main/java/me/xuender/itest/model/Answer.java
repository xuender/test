package me.xuender.itest.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 备选答案
 * Created by ender on 14-4-5.
 */
public class Answer extends AbstractItem {
    private Conclusion conclusion;
    private Integer jump;

    public Answer(JSONObject obj, ITest test) throws JSONException {
        super(obj, test);
        if (obj.has("jump")) {
            jump = obj.getInt("jump");
        }
        if (obj.has("conclusion")) {
            conclusion = getTest().findConclusion(obj.getInt("conclusion"));
        }
    }

    public Integer getJump() {
        return jump;
    }

    public void setJump(Integer jump) {
        this.jump = jump;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }

    public void setConclusion(Conclusion conclusion) {
        this.conclusion = conclusion;
    }
}
