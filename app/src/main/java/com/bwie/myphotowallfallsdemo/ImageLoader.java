package com.bwie.myphotowallfallsdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * 类的用途：
 *
 * @author 李岩
 * @date ${date} ${time}
 * ${tags}
 */

public class ImageLoader {
    //图片缓存技术的核心类，用于缓存所有下载好的图片，在程序内存达到设定值时会将最少最近使用的图片移除掉。
    private static LruCache<String, Bitmap> mMemoryCache;

    //ImageLoader的实例
    private static ImageLoader mImageLoager;

    private ImageLoader() {
        //获得应用程序最大的可用内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //设置图片缓存大小为程序最大可用内存的1/8
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    /**
     * 获取Imageloager的实例
     * 这里好像是单例模式
     */
    public static ImageLoader getInstance() {
        if (mImageLoager == null) {
            mImageLoager = new ImageLoader();
        }
        return mImageLoager;
    }

    /**
     * 将一张图片存储到LruCache中。
     *
     * @param key    LruCache的键，这里传入图片的URL的地址。
     * @param bitmap LruCache的键，这里传入从网络上下载的Bitmap对象
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 从LruCache中获取一张图片，如果不存在就返回null.
     *
     * @param key LruCache的键，这里传入图片的URL的地址。
     * @return 对应传入键的Bitmap对象，或者null。
     */
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }

    //计算抽样大小
    public static int calculateInSampleSize(BitmapFactory.Options option, int reqWidth) {
        //原图片的宽度
        final int width = option.outWidth;

        int inSampleSize = 1;
        if (width > inSampleSize) {
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = widthRatio;
        }
        return inSampleSize;
    }

    //从资源解码采样位图
    public static Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth) {
        //第一次解析将inJustDecodeBounds设为true,来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        //调用上面定义的方法计算inSampleSize的值
        options.inSampleSize = calculateInSampleSize(options, reqWidth);
        //使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }
}
