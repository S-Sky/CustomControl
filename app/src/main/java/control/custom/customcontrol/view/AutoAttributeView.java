package control.custom.customcontrol.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/12/1 0001.
 * 自定义属性
 */

public class AutoAttributeView extends View {

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
        String my_age = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_age");
        String my_name = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_name");
        String my_bg = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "my_bg");
        System.out.println("my_age==>" + my_age + "\tmy_name==>" + my_name + "\tmy_bg==>" + my_bg);
        //2、遍历属性集合
        //3、使用系统工具获取属性
    }
}
