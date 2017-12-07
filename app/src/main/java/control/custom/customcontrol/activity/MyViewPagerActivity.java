package control.custom.customcontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import control.custom.customcontrol.R;
import control.custom.customcontrol.view.MyViewPager;

/**
 * Created by Administrator on 2017/12/2 0002.
 */

public class MyViewPagerActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private RadioGroup rg;
    private int[] ids = {R.drawable.landscape, R.drawable.xingqiu, R.drawable.jiqiren, R.drawable.main_top, R.drawable.xingqiu};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_my_view_pager);
        myViewPager = findViewById(R.id.myviewpager);
        rg = findViewById(R.id.rg);

        //添加页面
        for (int i = 0; i < ids.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);
            //添加到MyViewGroup这个view中
            myViewPager.addView(imageView);
        }

        //添加测试页面
        View testView = View.inflate(this, R.layout.test, null);
        myViewPager.addView(testView, 2);

        for (int i = 0; i < myViewPager.getChildCount(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setId(i); //0-4

            if (i == 0) {
                radioButton.setChecked(true);
            }

            //添加到RadioGroup
            rg.addView(radioButton);
        }
        //设置RadioGroup选中状态的变化
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             *
             * @param group
             * @param checkedId 0-4
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //用MyViewPager
                myViewPager.scrollToPager(checkedId); //根据下标位置定位到具体的某个菜单
            }
        });
        //设置监听页面的改变
        myViewPager.setOnPagerChangerListener(new MyViewPager.OnPagerChangerListener() {
            /**
             *
             * @param position 当前页面的下标
             */
            @Override
            public void onScrollToPager(int position) {
                System.out.println("MyViewPagerActivity==" + "onScrollToPager");
                rg.check(position);
            }
        });
    }
}
