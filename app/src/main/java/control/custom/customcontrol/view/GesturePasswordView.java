package control.custom.customcontrol.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import control.custom.customcontrol.data.GesturePoint;
import control.custom.customcontrol.utils.DensityUtils;

/**
 * 仿微信手势密码
 * Created by Administrator on 2017/12/11 0011.
 */

public class GesturePasswordView extends View {

    private Paint outPaint; //每一个圆最外层画笔
    private Paint inPaint; //每一个圆内层画笔
    private Paint centerPanit; //中心圆画笔

    private int outRadius; //外圆半径
    private int centerRadius; //中心圆半径

    private int viewWidth; //整个视图的宽高
    private int viewHeight;

    private int centerX; //第一个圆X轴坐标
    private int centerY; //第一个圆Y轴坐标
    private float startX;
    private float startY;
    private float currentX;
    private float currentY;
    private float stopX;
    private float stopY;


    private ArrayList<GesturePoint> circleList; //存放所有圆心
    private ArrayList<GesturePoint> selectList; //存放密码位置

    private boolean isFirstHit = false; //第一次点击时是否是九点之一,如果是,继续操作
    private String input = ""; //记录密码点下标
    private String password = ""; //设置成功后的密码
    private boolean isSetting = false;//默认是解锁状态，为true时是设置状态
    private PatternViewListener patternListener;

    public GesturePasswordView(Context context) {
        super(context);
        init(context);
    }

    public GesturePasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GesturePasswordView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setColor(Color.BLUE);

        inPaint = new Paint();
        inPaint.setAntiAlias(true);
        inPaint.setColor(Color.WHITE);

        centerPanit = new Paint();
        centerPanit.setAntiAlias(true);
        centerPanit.setColor(Color.BLUE);

        outRadius = (int) DensityUtils.dpToPx(context, 30);
        centerRadius = (int) DensityUtils.dpToPx(context, 10);

        circleList = new ArrayList<GesturePoint>();
        selectList = new ArrayList<GesturePoint>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        if (width < height) {
            viewWidth = width;
            viewHeight = width;
            centerX = width / 4;

        }
        if (height < width) {
            viewWidth = height;
            viewHeight = height;
            centerX = height / 4;
        }
        centerY = centerX - outRadius;
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 9; i++) { //第一行
            if (i < 3) {//第一层
                canvas.drawCircle((i + 1) * centerX, centerY, outRadius, outPaint);
                canvas.drawCircle((i + 1) * centerX, centerY, outRadius - 2, inPaint);
                circleList.add(new GesturePoint((i + 1) * centerX, centerY));
            }
            if (i >= 3 && i < 6) { //第二行
                canvas.drawCircle((i - 2) * centerX, centerY + centerX, outRadius, outPaint);
                canvas.drawCircle((i - 2) * centerX, centerY + centerX, outRadius - 2, inPaint);
                circleList.add(new GesturePoint((i - 2) * centerX, centerY + centerX));
            } else if (i >= 6) { //第三层
                canvas.drawCircle((i - 5) * centerX, centerY + 2 * centerX, outRadius, outPaint);
                canvas.drawCircle((i - 5) * centerX, centerY + 2 * centerX, outRadius - 2, inPaint);
                circleList.add(new GesturePoint((i - 5) * centerX, centerY + 2 * centerX));
            }
        }

        if (input.length() > 0) { //说明有设置密码点
            for (int i = 0; i < input.length(); i++) {
                GesturePoint gesturePoint = selectList.get(i);
                canvas.drawCircle(gesturePoint.getCenterX(), gesturePoint.getCenterY(), centerRadius, centerPanit);
                if (i == (input.length() - 1)) { //说明是最后一个点
                    if (stopX > 0) {
                        canvas.drawLine(gesturePoint.getCenterX(), gesturePoint.getCenterY(), stopX, stopY, centerPanit);
                    }
                } else {
                    GesturePoint gesturePoint1 = selectList.get(i + 1);
                    canvas.drawLine(gesturePoint.getCenterX(), gesturePoint.getCenterY(), gesturePoint1.getCenterX(), gesturePoint1.getCenterY(), centerPanit);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                for (int i = 0; i < circleList.size(); i++) {
                    GesturePoint gesturePoint = circleList.get(i);
                    if (gesturePoint.isTouchMe(startX, startY, outRadius)) {
                        if (input.length() == 0) { //长度为0说明还没有设置密码
                            isFirstHit = true;
                            input += i;
                            selectList.add(gesturePoint);
                            invalidate();
                        }
                        break;
                    }
                }
            case MotionEvent.ACTION_MOVE:
                if (isFirstHit) {
                    currentX = event.getX();
                    currentY = event.getY();
                    for (int i = 0; i < circleList.size(); i++) {
                        GesturePoint gesturePoint = circleList.get(i);
                        if (gesturePoint.isTouchMe(currentX, currentY, outRadius)) {
                            if (!input.contains("" + i)) {
                                selectList.add(gesturePoint);
                                input += i;
                                invalidate();
                            }
                            break;
                        } else {
                            stopX = currentX;
                            stopY = currentY;
                            invalidate();
                        }
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:

                if (isSetting) { //设置密码时
                    if (input != null && input.length() > 3) {
                        String result = input;
                        resetPatternView();
                        patternListener.onSet(result);
                    } else {
                        resetPatternView();
                    }
                } else { //输入密码时
                    if (input != null && input.length() > 0) {
                        if (password.equals(input)) {
                            patternListener.onSuccess();
                            resetPatternView();
                        } else {
                            resetPatternView();
                            patternListener.onError();
                        }
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                return true;
        }
        return false;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewWidth;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = viewHeight;
        }
        return result;
    }

    /*************************************************************/

    /**
     * 设置手势密码
     * true:设置密码
     * false:解码状态
     *
     * @param isSetting
     */
    public void setLockState(boolean isSetting) {
        this.isSetting = isSetting;
    }

    /**
     * 设置密码
     *
     * @param pwd 密码
     */
    public void setPassword(String pwd) {
        this.password = pwd;
    }


    /**
     * 重置view
     */
    private void resetPatternView() {
        stopX = -1;
        stopY = -1;
        selectList.clear();
        input = "";
        isFirstHit = false;
        invalidate();
    }

    /**
     * 设置图案控件监听器
     *
     * @param listner listner
     */
    public void setViewListener(PatternViewListener listner) {
        this.patternListener = listner;
    }

    public interface PatternViewListener {
        void onSuccess();

        void onSet(String psw);

        void onError();
    }
}
