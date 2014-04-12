package me.xuender.utils;

/**
 * Created by ender on 14-4-12.
 */
public final class StringUtils {
    private StringUtils() {
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}