package control.custom.customcontrol.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 继承ViewGroup模仿viewPager轮播
 * Created by Administrator on 2017/12/2 0002.
 */

public class MyViewPager extends ViewGroup {

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
