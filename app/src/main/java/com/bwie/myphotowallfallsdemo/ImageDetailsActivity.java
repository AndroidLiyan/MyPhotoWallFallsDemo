package com.bwie.myphotowallfallsdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

/**
 * 类的用途：
 *
 * @author 李岩
 * @date ${date} ${time}
 * ${tags}
 */

public class ImageDetailsActivity extends AppCompatActivity {

    //自定义ImageView控件，可进行多点单点触控
    private ZoomImageView zoomImageView;
    //待展示的图片
    private Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.image_details);
        zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
        // 取出图片路径，并解析成Bitmap对象，然后在ZoomImageView中显示
        String imagePath = getIntent().getStringExtra("image_path");
        bitmap = BitmapFactory.decodeFile(imagePath);
        zoomImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 记得将Bitmap对象回收掉
        if (bitmap != null) {
            bitmap.recycle();
        }
    }
}
