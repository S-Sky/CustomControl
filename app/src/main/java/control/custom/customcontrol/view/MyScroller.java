package control.custom.customcontrol.view;

import android.os.SystemClock;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class MyScroller {

    /**
     * 起始坐标
     */
    private float startX;
    private float startY;
    /**
     * 移动的距离
     */
    private int distanceX;
    private int distanceY;
    /**
     * 开始的时间
     */
    private long startTime;
    /**
     * 总时间设置为500ms
     */
    private long totalTime = 500;
    /**
     * 是否完成移动
     * false: 没有移动完成
     * true: 移动完成
     */
    private boolean isFinish;

    private float currX;

    /**
     * 得到在X轴移动的距离
     *
     * @return
     */
    public float getCurrX() {
        return currX;
    }

    /**
     * 刚开始移动
     *
     * @param startX    起始X
     * @param startY    起始Y
     * @param distanceX 偏移X
     * @param distanceY 偏移Y
     */
    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX = startX;
        this.startY = startY;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
        this.startTime = SystemClock.uptimeMillis(); //系统开机时间
        this.isFinish = false;
    }

    /**
     * 速度
     * 求移动一小段的距离
     * 求移动一小段对应的坐标
     * 求移动一小段对应的时间
     * true: 正在移动
     * false:移动结束
     *
     * @return
     */
    public boolean coputeScrollOffset() {

        if (isFinish) {
            return false;
        }

        //结束时间
        long endTime = SystemClock.uptimeMillis();
        //移动开始到结束所花的时间
        long passTime = endTime - startTime;

        if (passTime < totalTime) {
            //移动未结束
            //计算移动的平均速度
            //float volecity = distanceX / totalTime;
            //移动这一个小段对应的距离
            float distanceSamllX = passTime * (distanceX / totalTime);

            currX = distanceSamllX + startX;
        } else {
            //移动结束
            isFinish = true;
            currX = startX + distanceX;
        }

        return true;
    }
}
