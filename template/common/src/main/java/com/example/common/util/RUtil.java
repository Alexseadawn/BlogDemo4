package com.example.common.util;

/**
 * Created by 舍长 on 2018/12/15
 * describe:随机数工具类
 */
public class RUtil {
    /**
     * 传入最小值，最大值，返回范围内的一个整数
     */
    private int getRange(int min, int max) {
        int result = (int) (min + 1 + Math.random() * (max + 1));
        return result;
    }
}
