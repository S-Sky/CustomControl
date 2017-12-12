package control.custom.customcontrol.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

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

    private static final int REQUESTCODE_PIC = 1; //相机
    private static final int REQUESTCODE_CAM = 2; //相册
    private static final int REQUESTCODE_CUT = 3; //图片裁剪

    private Bitmap headBitmap;
    private File file;

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
                        openCamera();
                    }
                })
                .addSheetItem("打开相册", ActionSheetDialog.SheetItemColor.RED, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        openPic();
                    }
                })
                .setCanceledOnTouchOutside(false).show();
    }

    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            file = new File(outDir, System.currentTimeMillis() + "jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, REQUESTCODE_CAM);
        } else {
            Log.e("CAMERA", "请确认已经插入SD卡");
        }
    }

    private void openPic() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_CAM:
                    startPhotoZoom(Uri.fromFile(file));
                    break;
                case REQUESTCODE_PIC:
                    if (data == null || data.getData() == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case REQUESTCODE_CUT:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            //这块可以文件上传
            headBitmap = extras.getParcelable("data");
            circleImage.setImageBitmap(headBitmap);
        }
    }

    /**
     * 调用系统裁剪功能
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUT);
    }
}
