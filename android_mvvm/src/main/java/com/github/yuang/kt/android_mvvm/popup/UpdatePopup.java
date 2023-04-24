package com.github.yuang.kt.android_mvvm.popup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.yuang.kt.android_mvvm.R;
import com.lxj.xpopup.core.CenterPopupView;


//App 更新 提示框
public class UpdatePopup extends CenterPopupView {

    TextView versionName, remark;
    String name, des;

    TextView cancel, update;
    LinearLayout mLlUpdateBackground;
    private Drawable bg = null;

    public UpdatePopup(@NonNull Context context, String name, String des, ButtonClick click) {
        super(context);
        this.click = click;
        this.des = des;
        this.name = name;
    }

    public UpdatePopup(@NonNull Context context, String name, String des, Drawable bg, ButtonClick click) {
        super(context);
        this.click = click;
        this.des = des;
        this.name = name;
        this.bg = bg;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_update;
    }

    public void setBg() {
        if (bg != null) {
            mLlUpdateBackground.setBackground(bg);
        }
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewByIds();
        setBg();
        setText();
        initListener();
    }

    private void initListener() {
        update.setOnClickListener(v -> {
            if (click != null) {
                dismiss();
                click.update();
            }
        });

        cancel.setOnClickListener(v -> {
            if (click != null) {
                dismiss();
                click.finish();
            }
        });
    }

    private void setText() {
        if (!TextUtils.isEmpty(name)) {
            versionName.setText(name);
        }

        if (!TextUtils.isEmpty(des)) {
            remark.setText(des);
        }
    }

    private void findViewByIds() {
        mLlUpdateBackground = findViewById(R.id.mLlUpdateBackground);
        versionName = findViewById(R.id.versionName);
        remark = findViewById(R.id.remark);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);
    }


    protected void onShow() {
        super.onShow();
    }

    public ButtonClick click;

    public interface ButtonClick {
        void update();

        void finish();
    }

}
