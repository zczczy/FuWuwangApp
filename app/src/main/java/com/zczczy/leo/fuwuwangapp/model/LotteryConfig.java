package com.zczczy.leo.fuwuwangapp.model;

import java.io.Serializable;

/**
 * Created by leo on 2016/3/8.
 */
public class LotteryConfig implements Serializable {

    /**
     * ConfigId : 1
     * BigImgUrl : sample string 2
     * HandImgUrl : sample string 3
     * IsQueue : sample string 4
     * AllLotteryLv : 5
     * DefaultLotteryBase : 6
     */

    public int ConfigId;
    public String BigImgUrl;
    public String HandImgUrl;
    public String IsQueue;
    public int AllLotteryLv;
    public int DefaultLotteryBase;
    public String AppLotteryImgUrl;
    public String AppHomeIsShow;
}
