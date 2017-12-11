package control.custom.customcontrol.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class DensityUtils {


    private DensityUtils() {
          /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转换为px
     *
     * @param context
     * @param dp
     * @return
     */
    public static float dpToPx(Context context, float dp) {
        float px = getAbsValue(context, dp, TypedValue.COMPLEX_UNIT_DIP);
        return px;
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param px
     * @return
     */
    public static float pxToDp(Context context, float px) {
        float dp = (px / context.getResources().getDisplayMetrics().density);
        return dp;
    }

    /**
     * sp转px
     *
     * @param context
     * @param sp
     * @return
     */
    public static float spToPx(Context context, float sp) {
        float px = getAbsValue(context, sp, TypedValue.COMPLEX_UNIT_SP);
        return px;
    }

    private static float getAbsValue(Context context, float value, int unit) {
        return TypedValue.applyDimension(unit, value, context.getResources().getDisplayMetrics());
    }
}
