package control.custom.customcontrol;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by Administrator on 2017/12/9 0009.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * android 7.0系统解决拍照的问题 android.os.FileUriExposedException
         * {至于为什么现在还没弄明白}
         * VM会忽略文件URI曝光
         * 方法builder.detectFileUriExposure()启用文件曝光检测,如果我们没有设置VmPolicy,这也是默认行为
         */
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
