package control.custom.customcontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import control.custom.customcontrol.R;
import control.custom.customcontrol.view.MyViewPager;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class MyViewPagerActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private int[] ids = {R.drawable.landscape, R.drawable.xingqiu, R.drawable.jiqiren, R.drawable.main_top, R.drawable.xingqiu};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_my_view_pager);
        myViewPager = findViewById(R.id.myviewpager);

        //添加页面
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            //添加到MyViewGroup这个view中
            myViewPager.addView(imageView);
        }
    }
}
