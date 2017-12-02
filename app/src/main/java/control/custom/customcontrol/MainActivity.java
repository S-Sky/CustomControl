package control.custom.customcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import control.custom.customcontrol.activity.AutoAttributeActivity;
import control.custom.customcontrol.activity.MyViewPagerActivity;
import control.custom.customcontrol.activity.ToggleButtonActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        findViewById(R.id.slide_switch).setOnClickListener(this);
        findViewById(R.id.auto_attribute).setOnClickListener(this);
        findViewById(R.id.my_view_pager).setOnClickListener(this);
    }

    @Override
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
        }
    }
}
