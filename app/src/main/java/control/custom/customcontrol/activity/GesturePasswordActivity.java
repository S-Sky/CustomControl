package control.custom.customcontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import control.custom.customcontrol.R;
import control.custom.customcontrol.utils.PrefUtils;
import control.custom.customcontrol.view.GesturePasswordView;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class GesturePasswordActivity extends AppCompatActivity {

    @BindView(R.id.gesture_pw)
    GesturePasswordView gesturePasswordView;
    @BindView(R.id.tv_state)
    TextView tv_state;

    String gesturePsw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_password);
        ButterKnife.bind(this);

        if (!PrefUtils.getBoolean(GesturePasswordActivity.this, "isSetting", false)) { //false说明还没有设置密码
            gesturePasswordView.setLockState(true);
        } else {
            tv_state.setText("请验证手势密码");
            gesturePasswordView.setPassword(PrefUtils.getString(GesturePasswordActivity.this, "gesturePsw", ""));
        }


        gesturePasswordView.setViewListener(new GesturePasswordView.PatternViewListener() {
            @Override
            public void onSuccess() {
                //tv_state.setText("手势密码验证成功");
                Toast.makeText(GesturePasswordActivity.this, "手势密码验证成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onSet(String psw) {
                if (!TextUtils.isEmpty(psw)) {
                    if (TextUtils.isEmpty(gesturePsw)) {
                        gesturePsw = psw;
                        tv_state.setText("请再次输入手势");
                    } else {
                        if (gesturePsw.equals(psw)) {
                            tv_state.setText("设置成功");
                            PrefUtils.setString(GesturePasswordActivity.this, "gesturePsw", gesturePsw);
                            PrefUtils.setBoolean(GesturePasswordActivity.this, "isSetting", true);
                            onBackPressed();
                        } else {
                            gesturePsw = "";
                            tv_state.setText("两次手势不一致");
                        }
                    }
                }
            }

            @Override
            public void onError() {
                //Toast.makeText(GesturePasswordActivity.this, "手势密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                tv_state.setText("手势密码错误，请重新输入");
            }
        });

    }

    @OnClick(R.id.btn_reset)
    public void onClick(View view) {
        tv_state.setText("请输入手势密码");
        PrefUtils.setString(GesturePasswordActivity.this, "gesturePsw", "");
        PrefUtils.setBoolean(GesturePasswordActivity.this, "isSetting", false);
        gesturePasswordView.setLockState(true);
    }
}
