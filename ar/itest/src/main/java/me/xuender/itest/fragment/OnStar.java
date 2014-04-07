package me.xuender.itest.fragment;

/**
 * 星标接口
 * Created by ender on 14-4-7.
 */
public interface OnStar {
    /**
     * 判断是否测试过
     *
     * @param testNum
     * @return
     */
    boolean isStar(int testNum);

    /**
     * 获取星星数量
     *
     * @return
     */
    int star();

    /**
     * 星星清零
     */
    void zero();
}
