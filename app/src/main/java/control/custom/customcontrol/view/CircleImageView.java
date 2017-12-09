package control.custom.customcontrol.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
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

    private Bitmap image;

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
        /**
         * 初始化画笔
         */
        mPaint = new Paint();
        mPaint.setColor(outCircleColor);
        mPaint.setAntiAlias(true);
    }

    /**
     * 测量视图的大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        viewWidth = width - (outCircleWidth * 2);
        viewHeight = height - (outCircleWidth * 2);

        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        loadImg();
        if (image != null) {
            int min = Math.min(viewWidth, viewHeight);
            System.out.println("min" + min);
            int radius = min / 2;

            image = Bitmap.createScaledBitmap(image, min, min, false);
            canvas.drawCircle(radius, radius, radius, mPaint);
            canvas.drawBitmap(createCircleBitmap(image, min), outCircleWidth, outCircleWidth, null);
        }
    }

    /**
     * 绘制显示的图片
     *
     * @param image
     * @param min
     * @return
     */
    private Bitmap createCircleBitmap(Bitmap image, int min) {
        Bitmap bitmap = null;

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        bitmap = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(min / 2 - outCircleWidth, min / 2 - outCircleWidth, min / 2 - outCircleWidth, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(image, 0, 0, paint);

        return bitmap;
    }

    /**
     * 获取显示的图片
     */
    private void loadImg() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();
        if (bitmapDrawable != null) {
            image = bitmapDrawable.getBitmap();
        }
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
}
