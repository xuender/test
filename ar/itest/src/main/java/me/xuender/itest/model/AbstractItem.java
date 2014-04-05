package me.xuender.itest.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 抽象条目
 * Created by ender on 14-4-5.
 */
public abstract class AbstractItem {
    private int num;
    private String title;
    private String context;
    private String summary;
    private ITest test;

    public AbstractItem(JSONObject obj) throws JSONException {
        if (obj.has("num")) {
            setNum(obj.getInt("num"));
        }
        if (obj.has("title")) {
            setTitle(obj.getString("title"));
        }
        if (obj.has("context")) {
            setContext(obj.getString("context"));
        }
        if (obj.has("summary")) {
            setSummary(obj.getString("summary"));
        }
    }

    public AbstractItem(JSONObject obj, ITest test) throws JSONException {
        this(obj);
        this.test = test;
    }

    public ITest getTest() {
        return test;
    }

    public void setTest(ITest test) {
        this.test = test;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
