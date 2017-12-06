package control.custom.customcontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * 继承ViewGroup模仿viewPager轮播
 * <p>
 * ViewGroup事件的消耗和传递主要由下面三个方法实现
 * 1、dispatchTouchEvent方法
 * 2、onInterceptTouchEvent方法
 * 3、onTouchEvent方法
 * </p>
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
    private float startX;

    private Scroller scroller; //系统的
    private OnPagerChangerListener mOnPagerChangerListener;

    private float downX;
    private float downY;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(final Context context) {
        scroller = new Scroller(context);
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
                        //Toast.makeText(context, "滑动", Toast.LENGTH_SHORT).show();
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

    /**
     * 在这个方法中强制设置每个一级子view的固定大小,并没有考虑自身需要多大
     *
     * @param b
     * @param l
     * @param t
     * @param r
     * @param b1
     */
    @Override
    protected void onLayout(boolean b, int l, int t, int r, int b1) {
        //遍历孩子,给每个子视图指定在屏幕的坐标位置
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i); //获取每个子视图
            //getWidth() 整个空间的宽度
            childView.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight()); //指定每个子视图的位置
        }
    }

    /**
     * dispatchTouchEvent方法用于事件的分发,Android中所有的事件都必须经过这个方法的分发,然后决定是
     * 自身消费当前事件还是继续往下分发给子控件处理。
     * 返回true表示不继续分发,事件没有被消费,
     * 如果是ViewGroup则分发给onInterceptTouchEvent进行判断是否拦截该事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * onInterceptTouchEvent是ViewGroup中才有的方法,view中没有,它的作用是负责事件的拦截,
     * 返回true的时候表示拦截当前事件,不继续往下分发,交给自身的onTouchEvent进行处理,
     * 返回false则不拦截,继续往下传
     * 这是ViewGroup特有的方法
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = false; //默认不拦截
        detector.onTouchEvent(ev); //不把事件传递给手势识别器会出现闪动现象(出现bug)
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("onInterceptTouchEvent==ACTION_DOWN");
                //1、记录起始坐标
                downX = ev.getRawX();
                downY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onInterceptTouchEvent==ACTION_MOVE");
                //2、记录结束值
                float endX = ev.getRawX();
                float endY = ev.getRawY();

                //3、计算绝对值
                float distanceX = Math.abs(endX - downX);
                float distanceY = Math.abs(endY - downY);

                if (distanceX > distanceY && distanceX > 5) { //X轴上必须有移动
                    result = true;
                }

                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onInterceptTouchEvent==ACTION_UP");
                break;
        }
        return result;
    }

    /**
     * 触摸事件
     * <p>
     * onTouchEvent方法用于事件的处理,返回true表示消费处理当前事件,返回false则不处理,交给子控件进行分发
     * </p>
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
                System.out.println("onTouchEvent==ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("onTouchEvent==ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("onTouchEvent==ACTION_UP");
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
    public void scrollToPager(int tempIndex) {
        if (tempIndex < 0) {
            tempIndex = 0;
        }
        if (tempIndex > getChildCount() - 1) {
            tempIndex = getChildCount() - 1;
        }
        //当前页面的下标位置
        currentIndex = tempIndex;
        if (mOnPagerChangerListener != null) {
            mOnPagerChangerListener.onScrollToPager(currentIndex);
        }

        int distanceX = currentIndex * getWidth() - getScrollX();
        //移动到指定的位置
        //scrollTo(currentIndex * getWidth(), 0);
        //scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0);
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, 0, Math.abs(distanceX)); //指定滚动的时长
        //刷新
        invalidate(); //这个方法会执行onDraw();computeScroll()
    }

    @Override
    public void computeScroll() {
        //super.computeScroll();
        if (scroller.computeScrollOffset()) {

            float currX = scroller.getCurrX();
            //System.out.println("偏移==" + currX);
            scrollTo((int) currX, 0);
            //再次刷新
            invalidate();
        }
    }

    /**
     * 监听页面的改变
     */
    public interface OnPagerChangerListener {
        /**
         * 当页面改变时回调这个方法
         *
         * @param position 当前页面的下标
         */
        void onScrollToPager(int position);
    }

    /**
     * 设置页面改变的监听
     *
     * @param listener
     */
    public void setOnPagerChangerListener(OnPagerChangerListener listener) {
        mOnPagerChangerListener = listener;
    }

    /**
     * 1、测量的时候测量多次
     * 2、widthMeasureSpec父层视图给当前视图的宽和模式
     * <p>
     * MeasureSpec:封装了父容器对view的布局上的限制,内部提供了宽高的信息(SpecMode、SpecSize),SpecSize是指在某种SpecMode下的参考尺寸
     * SpecMode模式有三种
     * MeasureSpec.UNSPECIFIED 父容器不对view有任何限制,要多大给多大(通常是包裹)
     * MeasureSpec.EXACTLY 父容器已经检测出view所需的大小
     * MeasureSpec.AT_MOST 父容器指定了一个大小,view的大小不能大于这个值
     * <p>
     * 系统的onMeasure()中所干的事
     * 1、根据widthMeasureSpec求得宽度width和服view给的模式
     * 2、根据自身的宽度width和自身的padding值,相减,求得子view可以拥有的宽度newWidth
     * 3、根据newWidth和模式求得一个新的MeasureSpec值:MeasureSpec.makeMeasureSpec(newSize,newMode);
     * 4、用新的MeasureSpec计算子view
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        System.out.println("widthMeasureSpec==" + widthMeasureSpec + "\tmodeWidth==" + modeWidth + "\tsizeWidth==" + sizeWidth);
        System.out.println("heightMeasureSpec==" + heightMeasureSpec + "\tmodeHeight==" + modeHeight + "\tsizeHeight==" + sizeHeight);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
