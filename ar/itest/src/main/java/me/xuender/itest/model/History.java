package me.xuender.itest.model;

import java.util.Date;

/**
 * 测试历史
 * Created by ender on 14-4-6.
 */
public class History {
    private Date createAt;
    private ITest test;
    private Conclusion conclusion;

    public History(ITest test, int conclusion) {
        this.test = test;
        this.conclusion = this.test.findConclusion(conclusion);
        createAt = new Date(System.currentTimeMillis());
    }

    public History(int test, int conclusion) {
        this.test = ITest.findTest(test);
        this.conclusion = this.test.findConclusion(conclusion);
        createAt = new Date(System.currentTimeMillis());
    }

    public Date getCreateAt() {
        return createAt;
    }

    public ITest getTest() {
        return test;
    }

    public Conclusion getConclusion() {
        return conclusion;
    }
}