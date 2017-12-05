package control.custom.customcontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * 继承ViewGroup模仿viewPager轮播
 * Created by Administrator on 2017/12/2 0002.
 */

public class MyViewPager extends ViewGroup {

    /**
     * 手势识别器
     * 1、定义
     * 2、实例化- 重写想要的方法
     * 3、在onTouchEvent()把事件传递给手势识别器
     */
    private GestureDetector detector;
    /**
     * 当前页面的下标位置
     */
    private int currentIndex;

    private MyScroller scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new MyScroller();
        //2、实例化手势识别器
        detector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                        Toast.makeText(context, "长按", Toast.LENGTH_SHORT).show();
                    }

                    /**
                     * @param e1 刚开始滑动的时候
                     * @param e2 滑动结束离开的时候
                     * @param distanceX 滑动在X轴上移动的距离
                     * @param distanceY 滑动在Y轴上移动的距离
                     * @return
                     */
                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        Toast.makeText(context, "滑动", Toast.LENGTH_SHORT).show();
                        /**
                         * 根据当前的位置移动
                         * 要求只是水平方向滑动,所以Y方向设置为getScrollY()(起始值)或者0
                         * x: 要在X轴上移动的距离
                         * y: 要在Y轴上移动端距离
                         */
                        scrollBy((int) distanceX, 0);
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        Toast.makeText(context, "双击", Toast.LENGTH_SHORT).show();
                        return super.onDoubleTap(e);
                    }
                });
    }


    @Override
    protected void onLayout(boolean b, int l, int t, int r, int b1) {
        //遍历孩子,给每个子视图指定在屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i); //获取每个子视图
            //getWidth() 整个空间的宽度
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight()); //指定每个子视图的位置
        }
    }

    private float startX;

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        //3、把事件传递给手势识别器
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1、记录坐标
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                //2、拿到新的坐标
                float endX = event.getX();

                //下标位置
                int tempIndex = currentIndex;
                if ((startX - endX) > getWidth() / 3) {
                    //显示下一个页面
                    tempIndex++;
                } else if ((endX - startX) > getWidth() / 3) {
                    //显示上一个页面
                    tempIndex--;
                }
                //根据下标位置移动到指定页面
                scrollToPager(tempIndex);
                break;
        }
        return true;
    }

    /**
     * 屏蔽非法值(下标越界),根据位置移动到指定页面
     *
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;

        int distanceX = currentIndex * getWidth() - getScrollX();
        //移动到指定的位置
        //scrollTo(currentIndex * getWidth(), 0);
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);

        //刷新
        invalidate(); //这个方法会执行onDraw();computeScroll()
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (scroller.coputeScrollOffset()) {

            float currX = scroller.getCurrX();

            scrollTo((int) currX, 0);
            //再次刷新
            invalidate();
        }
    }
}
