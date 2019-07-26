package com.android.baseapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.baseapp.R;
import com.android.baseapp.app.App;

import com.android.baseapp.widget.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import utils.ViewUtil;

/**
 * Date   2017/8/23
 * Editor  Misuzu
 * 图片加载类
 */

public class ImageUtils {

    /**
     * 质量压缩图片
     */
    private static final String COMPRESS_PIC = "";

    /**
     * 普通加载图片
     */
    public static void displayImage(ImageView imageView, String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl)) {
            imageUrl += COMPRESS_PIC;
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .placeholder(R.drawable.img_loading_gray)
                    .transform(getTransformation(imageView))
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }


    /**
     * 普通加载图片 无裁剪
     */
    public static void displayImageNoResize(ImageView imageView, String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .placeholder(R.drawable.img_loading_gray)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }

    /**
     * 普通加载图片
     */
    public static void displayImage(ImageView imageView, File file) {

        if (file!=null) {
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(file)
                    .placeholder(R.drawable.img_loading_gray)
                    .transform(getTransformation(imageView))
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }



    /**
     * 普通加载图片 可替换默认图
     */
    public static void displayImage(ImageView imageView, String imageUrl,int placeHolder) {

        if (!TextUtils.isEmpty(imageUrl)) {
            imageUrl += COMPRESS_PIC;
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .placeholder(placeHolder)
                    .into(imageView);
        } else {
            imageView.setImageResource(placeHolder);
        }

    }

    /**
     * 加载圆形图片
     */
    public static void displayCircleImage(ImageView imageView, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl))
            return;
        Picasso mPicasso = Picasso.with(App.getContext());
        mPicasso.load(imageUrl)
                .transform(new CircleCornerForm())
                .placeholder(R.drawable.quesheng_big)
                .into(imageView);
    }





    /**
     * 加载圆角图片
     */
    public static void displayRoundImage(ImageView imageView, String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl)) {
            imageUrl += COMPRESS_PIC;
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .fit().centerCrop()
                    .transform(new RoundedCornersTransformation(ViewUtil.dp2Px(App.getContext(), 5), 0))
                    .placeholder(R.drawable.img_loading_gray)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }

    /**
     * 加载圆角图片
     */
    public static void displayRoundImageByBanner(ImageView imageView, String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .fit().centerCrop()
                    .transform(new RoundedCornersTransformation(ViewUtil.dp2Px(App.getContext(), 5), 0))
                    .placeholder(R.drawable.img_loading_gray)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }



    /**
     * 模糊加载图片
     */
    public static void displayImageBlur(ImageView imageView, String imageUrl) {

        if (!TextUtils.isEmpty(imageUrl)) {
            imageUrl += COMPRESS_PIC;
            Picasso mPicasso = Picasso.with(App.getContext());
            mPicasso.load(imageUrl)
                    .placeholder(R.drawable.img_loading_gray)
                    .transform(new BlurTransformation(App.getContext()))
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img_loading_gray);
        }

    }


    /**
     * 按照图片控件大小来等比例缩放
     */
    public static Transformation getTransformation(final ImageView view) {

        return new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = view.getWidth();
                int targetHeight = view.getHeight();
//                L.dm("控件宽高:--->w"+targetWidth+"h"+targetHeight+"/n"+"图片宽高---->"+"w"+source.getWidth()+"h"+source.getHeight());
                //返回原图
                if (source.getWidth() == 0 || source.getWidth() < targetWidth) {
                    return source;
                }
                if (targetHeight == 0 || targetWidth == 0) {
                    return source;
                }
                //复制代码
                int inWidth = source.getWidth();
                int inHeight = source.getHeight();
                int drawX = 0;
                int drawY = 0;
                int drawWidth = inWidth;
                int drawHeight = inHeight;

                float widthRatio = targetWidth / (float) inWidth;
                float heightRatio = targetHeight / (float) inHeight;
                float scaleX, scaleY;
                if (widthRatio > heightRatio) {
                    int newSize = (int) Math.ceil(inHeight * (heightRatio / widthRatio));
                    drawY = (inHeight - newSize) / 2;
                    drawHeight = newSize;
                    scaleX = widthRatio;
                    scaleY = targetHeight / (float) drawHeight;
                } else {
                    int newSize = (int) Math.ceil(inWidth * (widthRatio / heightRatio));
                    drawX = (inWidth - newSize) / 2;
                    drawWidth = newSize;
                    scaleX = targetWidth / (float) drawWidth;
                    scaleY = heightRatio;
                }
                Matrix matrix = new Matrix();
                matrix.preScale(scaleX, scaleY);
                Bitmap result = Bitmap.createBitmap(source, drawX, drawY, drawWidth, drawHeight, matrix, true);
                if (result != source) {
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                int width = view.getWidth();
                if (width!=0)
                   return view.getWidth()+"";
                return view.getId()+"";
            }
        };
    }


    /**
     * 模糊
     */
    public static class BlurTransformation implements Transformation {

        RenderScript rs;

        public BlurTransformation(Context context) {
            super();
            rs = RenderScript.create(context);
        }

        @SuppressLint("NewApi")
        @Override
        public Bitmap transform(Bitmap bitmap) {
            // 创建一个Bitmap作为最后处理的效果Bitmap
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // 分配内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // 根据我们想使用的配置加载一个实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // 设置模糊半径
            script.setRadius(25);

            //开始操作
            script.forEach(output);

            // 将结果copy到blurredBitmap中
            output.copyTo(blurredBitmap);

            //释放资源
            bitmap.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }

    public static class CircleCornerForm implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int widthLight = source.getWidth();
            int heightLight = source.getHeight();

            Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(output);
            Paint paintColor = new Paint();
            paintColor.setFlags(Paint.ANTI_ALIAS_FLAG);

            RectF rectF = new RectF(new Rect(0, 0, widthLight, heightLight));

            canvas.drawRoundRect(rectF, widthLight / 5, heightLight / 5, paintColor);

            Paint paintImage = new Paint();
            paintImage.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            canvas.drawBitmap(source, 0, 0, paintImage);
            source.recycle();
            return output;
        }

        @Override
        public String key() {
            return "roundcorner";
        }
    }

}
