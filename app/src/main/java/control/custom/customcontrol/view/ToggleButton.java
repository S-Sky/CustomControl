package control.custom.customcontrol.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import control.custom.customcontrol.R;

/**
 * 自定义开关
 * Created by Administrator on 2017/12/1 0001.
 * 一个视图从创建到显示过程中的主要方法
 * 1、构造方法实例化类
 * 2、测量-measure(int,int) -->onMeasure();
 * 如果当前View是一个viewGroup,还需测量子控件,子控件可以建议父控件多高或者多宽
 * 3、指定控件位置-layout() -->onLayout(); (一般view不用这个方法,viewGroup的时候使用)
 * 4、绘制视图--draw()-->onDraw(canvas)
 * 根据上面两个方法的参数,进入绘制
 */

public class ToggleButton extends View implements View.OnClickListener {

    private Bitmap backgroundBitmap;
    private Bitmap slidingBitmap;
    private int slidLeftMax; //按钮距离左边最大的值(背景的宽度-按钮的宽度)
    private Paint mPaint;

    private boolean isOpen = false; //默认是关
    private int slideLeft; //距离左边的距离
    private float startX; //开始滑动时的x位置
    private float lastX; //按钮的起始值
    /**
     * true:点击事件生效,滑动事件不生效
     * false:点击事件不生效,滑动事件生效
     */
    private boolean isEnableClick = true;

    /**
     * 如果我们在布局文件使用该类,将会用这个构造方法实例该类
     *
     * @param context
     * @param attrs
     */
    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true); //设置抗锯齿
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_1);
        slidingBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.toggle_2);
        slidLeftMax = backgroundBitmap.getWidth() - slidingBitmap.getWidth();

        this.setOnClickListener(this);
    }

    /**
     * 视图的测量
     * <p>
     * 视图的大小将在这个方法中确定,也就是说measure只是对onMeasure的
     * 一个包装,子类可以覆写onMeasure()方法实现自己的计算视图大小的方式,
     * 并通过setMeasuredDimension(width,height)保存计算结果
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //保存显示视图的大小
        setMeasuredDimension(backgroundBitmap.getWidth(), backgroundBitmap.getHeight());
    }

    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        /**
         * 第一个参数,绘制bitmap
         * 第二个参数:距左边的距离
         * 第三个参数:距顶部的距离
         * 第四个参数:画笔
         */
        canvas.drawBitmap(backgroundBitmap, 0, 0, mPaint);
        canvas.drawBitmap(slidingBitmap, slideLeft, 0, mPaint);
    }

    @Override
    public void onClick(View view) {
        if (isEnableClick) {
            isOpen = !isOpen; //点击之后改变状态
            flushView();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event); //执行父类的方法
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //1、记录按下的坐标
                lastX = startX = event.getX();
                isEnableClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                //2、计算结束值
                float endX = event.getX();
                //3、计算偏移量
                float distanceX = endX - startX;
                slideLeft += distanceX;
                //4、屏蔽非法值
                if (slideLeft < 0) {
                    slideLeft = 0;
                } else if (slideLeft > slidLeftMax) {
                    slideLeft = slidLeftMax;
                }
                //5、刷新
                invalidate();
                //6、数据还原
                startX = event.getX();

                if (Math.abs(endX - lastX) > 5) {
                    //滑动
                    isEnableClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isEnableClick) {
                    if (slideLeft <= slidLeftMax / 2) { //显示按钮关
                        isOpen = false;
                    } else {
                        isOpen = true;
                    }
                    flushView();
                }
                break;
        }
        return true;
    }

    private void flushView() {
        if (isOpen) { //开关为开的时候
            slideLeft = slidLeftMax;
        } else {
            slideLeft = 0;
        }
        invalidate(); //这个方法会导致onDraw()执行
    }
}
