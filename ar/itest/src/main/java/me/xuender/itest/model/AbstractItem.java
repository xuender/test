package me.xuender.itest.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 抽象条目
 * Created by ender on 14-4-5.
 */
public abstract class AbstractItem implements Serializable {
    private int num;
    private String title;
    private String content;
    private String summary;
    private ITest test;

    public AbstractItem(JSONObject obj) throws JSONException {
        if (obj.has("num")) {
            setNum(obj.getInt("num"));
        }
        if (obj.has("title")) {
            setTitle(obj.getString("title"));
        }
        if (obj.has("content")) {
            setContent(obj.getString("content"));
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
