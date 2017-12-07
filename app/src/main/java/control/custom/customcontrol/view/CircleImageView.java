package control.custom.customcontrol.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import control.custom.customcontrol.R;

/**
 * 在View类中有四个构造函数，涉及到多个参数，
 * Context：上线文，这个不用多说
 * AttributeSet attrs： 从xml中定义的参数
 * int defStyleAttr ：主题中优先级最高的属性
 * int defStyleRes  ： 优先级次之的内置于View的style
 * 在android中的属性可以在多个地方进行赋值，涉及到的优先级排序为：
 * Xml直接定义 > xml中style引用 > defStyleAttr > defStyleRes > theme直接定义
 * <p>
 * Created by Administrator on 2017/12/7 0007.
 */

public class CircleImageView extends ImageView {

    /**
     * 外圆的颜色
     */
    private int outCircleColor = Color.WHITE;
    /**
     * 外圆的宽度
     */
    private int outCircleWidth;
    /**
     * 背景画笔
     */
    private Paint mPaint;

    private int viewWidth;
    private int viewHeight;

    /**
     * 在代码中new CircleImageView()的时候调用这个函数
     *
     * @param context
     */
    public CircleImageView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    /**
     * 在xml中引用时调用
     *
     * @param context
     * @param attrs
     */
    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);

    }

    /**
     * 使用系统工具获取自定义的属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            for (int i = 0; i < typedArray.getIndexCount(); i++) {
                int index = typedArray.getIndex(i);
                switch (index) {
                    case R.styleable.CircleImageView_outCircleColor:
                        this.outCircleColor = typedArray.getColor(index, Color.WHITE);
                        break;
                    case R.styleable.CircleImageView_outCircleWidth:
                        this.outCircleWidth = (int) typedArray.getDimension(index, 5);
                        break;
                }
            }
        }
        mPaint = new Paint();
        mPaint.setColor(outCircleColor);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY){}

        return result;
    }
}
