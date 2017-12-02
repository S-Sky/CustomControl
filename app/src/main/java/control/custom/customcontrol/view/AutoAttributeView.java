package control.custom.customcontrol.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import control.custom.customcontrol.R;

/**
 * Created by Administrator on 2017/12/1 0001.
 * 自定义属性
 */

public class AutoAttributeView extends View {

    private int age;
    private String name;
    private Bitmap bg;

    /**
     * 带有两个参数的构造方法在布局中使用
     *
     * @param context
     * @param attrs
     */
    public AutoAttributeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取属性三种方式
        //1、用命名空间去获取
//        String my_age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
//        String my_name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
//        String my_bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
//        System.out.println("my_age==>" + my_age + "\tmy_name==>" + my_name + "\tmy_bg==>" + my_bg);
        //2、遍历属性集合
//        for(int i = 0; i < attrs.getAttributeCount(); i++){
//            System.out.println(attrs.getAttributeName(i) + "==>" + attrs.getAttributeValue(i));
//        }
        //3、使用系统工具获取属性(推荐使用这种)
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoAttributeView);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.AutoAttributeView_my_age:
                    age = typedArray.getInt(index, 0);
                    break;
                case R.styleable.AutoAttributeView_my_name:
                    name = typedArray.getString(index);
                    break;
                case R.styleable.AutoAttributeView_my_bg:
                    Drawable drawable = typedArray.getDrawable(index);
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bg = bitmapDrawable.getBitmap();
                    break;
            }
        }
        typedArray.recycle(); //回收
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint();
        mPaint.setTextSize(50);
        canvas.drawText(name + "---" + age + "---", 50, 50, mPaint);
        canvas.drawBitmap(bg, 50, 50, mPaint);
    }
}
