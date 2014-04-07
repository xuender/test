package me.xuender.itest.fragment;

import me.xuender.itest.model.ITest;

/**
 * 历史操作接口
 * Created by ender on 14-4-7.
 */
public interface OnHistory {
    /**
     * 清除测试记录
     */
    void clean();

    /**
     * 增加测试记录
     *
     * @param test
     * @param conclusion
     */
    void add(ITest test, int conclusion);
}
