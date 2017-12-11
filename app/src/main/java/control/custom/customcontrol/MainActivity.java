package control.custom.customcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import control.custom.customcontrol.activity.AutoAttributeActivity;
import control.custom.customcontrol.activity.CircleImageActivity;
import control.custom.customcontrol.activity.GesturePasswordActivity;
import control.custom.customcontrol.activity.MyViewPagerActivity;
import control.custom.customcontrol.activity.ToggleButtonActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.slide_switch, R.id.auto_attribute, R.id.my_view_pager, R.id.custom_head_image, R.id.gesture_pw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.slide_switch:
                startActivity(new Intent(this, ToggleButtonActivity.class));
                break;
            case R.id.auto_attribute:
                startActivity(new Intent(this, AutoAttributeActivity.class));
                break;
            case R.id.my_view_pager:
                startActivity(new Intent(this, MyViewPagerActivity.class));
                break;
            case R.id.custom_head_image:
                startActivity(new Intent(this, CircleImageActivity.class));
                break;
            case R.id.gesture_pw:
                startActivity(new Intent(this,GesturePasswordActivity.class));
                break;
        }
    }
}
