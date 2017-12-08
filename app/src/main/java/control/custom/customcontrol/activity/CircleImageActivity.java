package control.custom.customcontrol.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import control.custom.customcontrol.R;
import control.custom.customcontrol.view.ActionSheetDialog;
import control.custom.customcontrol.view.CircleImageView;

/**
 * 仿iPhone头像选择效果
 * Created by Administrator on 2017/12/8 0008.
 */

public class CircleImageActivity extends AppCompatActivity {

    @BindView(R.id.circle_image)
    CircleImageView circleImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle_image);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.circle_image)
    public void onClick(View view) {
        new ActionSheetDialog(this).builder()
                .addSheetItem("拍照", null, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(CircleImageActivity.this, "当前下标是:" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .addSheetItem("打开相册", ActionSheetDialog.SheetItemColor.RED, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Toast.makeText(CircleImageActivity.this, "当前下标是:" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .setCanceledOnTouchOutside(false).show();
    }
}
