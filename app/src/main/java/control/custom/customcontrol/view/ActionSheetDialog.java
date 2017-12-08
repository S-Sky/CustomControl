package control.custom.customcontrol.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import control.custom.customcontrol.R;

import static android.view.ViewGroup.LayoutParams;

/**
 * 自定义dialog
 * Created by Administrator on 2017/12/8 0008.
 */

public class ActionSheetDialog {

    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private ScrollView sLayout_content;
    private boolean isShowTitle = false; //控制是否显示title
    private Display display; //用于拿到屏宽
    private List<SheetItem> sheetItemList;

    public ActionSheetDialog(Context context) {
        this.context = context;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = manager.getDefaultDisplay();
    }

    /**
     * 创建dialog
     *
     * @return
     */
    public ActionSheetDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);
        view.setMinimumWidth(display.getWidth());

        txt_title = view.findViewById(R.id.txt_title);
        txt_cancel = view.findViewById(R.id.txt_cancel);
        lLayout_content = view.findViewById(R.id.lLayout_content);
        sLayout_content = view.findViewById(R.id.sLayout_content);

        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog = new Dialog(context, R.style.ActionSheetDialog);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public ActionSheetDialog setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            isShowTitle = true;
            txt_title.setText(title);
            txt_title.setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 点击窗口外时是否取消对话框
     *
     * @param cancel
     * @return
     */
    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 添加显示选项
     *
     * @param name
     * @param color
     * @param listener
     * @return
     */
    public ActionSheetDialog addSheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(name, color, listener));
        return this;
    }

    public void show() {
        setSheetItem();
        dialog.show();
    }

    /**
     * 添加子选项
     */
    private void setSheetItem() {
        if (sheetItemList == null || sheetItemList.size() < 1) {
            return;
        }

        int size = sheetItemList.size();
        if (size > 6) { //控制显示的高度
            LayoutParams params = sLayout_content.getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }
        for (int i = 1; i <= size; i++) {
            final int index = i - 1;
            final SheetItem sheetItem = sheetItemList.get(i - 1);

            TextView textView = new TextView(context);
            textView.setText(sheetItem.name);
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER);
            //设置是否使用含有弧形的背景
            if (size == 1) {
                if (isShowTitle) {
                    //显示标题:标题上有弧形,内容下有弧形
                    textView.setBackgroundResource(R.drawable.circle_bottom);
                } else {
                    //不显示标题:内容上下都有弧形
                    textView.setBackgroundResource(R.drawable.circle_all);
                }
            } else {
                if (isShowTitle) { //显示标题
                    if (i >= 1 && i < size) {
                        //标题上有弧形,
                        textView.setBackgroundResource(R.drawable.no_circle);
                    } else {
                        textView.setBackgroundResource(R.drawable.circle_bottom);
                    }
                } else { //不显示标题:
                    if (i == 1) {
                        textView.setBackgroundResource(R.drawable.circle_top);
                    } else if (i < size) {
                        textView.setBackgroundResource(R.drawable.no_circle);
                    } else {
                        textView.setBackgroundResource(R.drawable.circle_bottom);
                    }
                }
            }
            if (sheetItem.color != null) {
                textView.setTextColor(Color.parseColor(sheetItem.color.getName()));
            } else {
                textView.setTextColor(Color.parseColor(SheetItemColor.BLUE.getName()));
            }
            float scale = context.getResources().getDisplayMetrics().density;

            int height = (int) (45 * scale + 0.5);

            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sheetItem.listener.onClick(index);
                    dialog.dismiss();
                }
            });
            lLayout_content.addView(textView);
        }
    }

    /**
     * 设置需要显示的每一项的属性
     */
    private class SheetItem {
        String name;
        OnSheetItemClickListener listener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color, OnSheetItemClickListener listener) {
            this.name = name;
            this.listener = listener;
            this.color = color;
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int position);
    }

    /**
     * 颜色枚举
     */
    public enum SheetItemColor {
        BLUE("#037BFF"), RED("#FD4A2E");

        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}



