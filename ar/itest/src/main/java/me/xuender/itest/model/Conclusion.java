package me.xuender.itest.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 结论
 * Created by ender on 14-4-5.
 */
public class Conclusion extends AbstractItem {
    public Conclusion(JSONObject obj, ITest test) throws JSONException {
        super(obj, test);
    }
}
