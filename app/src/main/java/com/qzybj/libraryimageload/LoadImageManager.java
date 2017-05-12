package com.qzybj.libraryimageload;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.qzybj.libraryimageload.exception.LoadImageException;
import com.qzybj.libraryimageload.interfaces.ILoadImage;
import com.qzybj.libraryimageload.interfaces.ILoadImageCallback;
import com.qzybj.libraryimageload.interfaces.impl.picasso.DefPicassoImpl;


/**
 * Created by ZhangYuanBo on 2016/8/3.
 * 图片加载统一控制类
 */
public class LoadImageManager{

    /**Debug 标记用来控制 是否抛出异常，Crash */
    private boolean isDebug = false;
    private static Application mApplication;
    private static LoadImageManager instance = null;
    private static ILoadImage loadImageInstance = null;

    private LoadImageManager() {}

    /**
     * 初始化
     * !!!调用之前必须要初始化
     * @param application
     * @param loadImageImpl
     */
    public static void init(Application application ,ILoadImage loadImageImpl) {
        mApplication = application;
        loadImageInstance = loadImageImpl;
    }
    /**
     * 初始化:调用Picasso的默认实现
     * 调用之前必须要初始化
     * @param application
     */
    public static void init(Application application) {
        init(application,new DefPicassoImpl());
    }

    public synchronized static LoadImageManager instance() {
        if(loadImageInstance==null){
            throw new LoadImageException("Is't init.");
        }
        if (instance==null) {
            instance = new LoadImageManager();
        }
        return instance;
    }
    private Context getApplication(){
        return mApplication;
    }

    /**Debug 标记用来控制是否抛出异常，允许Crash */
    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * 加载图片
     *
     * @param iv
     * @param imageUrl 支持的格式：除正常的imageurl字符串外， load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     */
    public void loadImage(ImageView iv, Object imageUrl)  {
        loadImage(iv,imageUrl,ILoadImage.NONE,ILoadImage.NONE,ILoadImage.NONE,ILoadImage.NONE,false,null);
    }

    /**
     * 加载图片
     *
     * @param iv
     * @param imageUrl 支持的格式：除正常的imageurl字符串外， load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     * @param loadImgResId 默认加载的图片
     */
    public void loadImage(ImageView iv, Object imageUrl, int loadImgResId)  {
        loadImage(iv,imageUrl,ILoadImage.NONE,ILoadImage.NONE,loadImgResId,ILoadImage.NONE,false,null);
    }

    /**
     * 加载图片
     *
     * @param iv
     * @param imageUrl 支持的格式：除正常的imageurl字符串外， load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     * @param loadImgResId 默认加载的图片
     * @param isTransform 是否显示加载动画
     */
    public void loadImage(ImageView iv, Object imageUrl, int loadImgResId, boolean isTransform)  {
        loadImage(iv,imageUrl,ILoadImage.NONE,ILoadImage.NONE,loadImgResId,ILoadImage.NONE,isTransform,null);
    }

    /**
     * 加载图片
     *
     * @param iv
     * @param imageUrl 支持的格式：除正常的imageurl字符串外， load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     * @param loadImgResId 默认加载的图片
     * @param isTransform 是否显示加载动画
     * @param callback 回调接口
     */
    public void loadImage(ImageView iv, Object imageUrl, int loadImgResId, boolean isTransform, ILoadImageCallback callback)  {
        loadImage(iv,imageUrl,ILoadImage.NONE,ILoadImage.NONE,loadImgResId,ILoadImage.NONE,isTransform,callback);
    }

    /**
     * 加载图片
     *
     * @param iv
     * @param imageUrl     支持的格式：除正常的imageurl字符串外， load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     * @param width        指定的图片宽
     * @param height       指定的图片高
     * @param loadImgResId 默认加载的图片
     * @param errImgResId  加载错误时的图片
     * @param isTransform  是否显示加载动画
     * @param callback     回调接口
     */
    public void loadImage(ImageView iv, Object imageUrl, int width, int height, int loadImgResId, int errImgResId, boolean isTransform, ILoadImageCallback callback){
        if(isSupportImageUrlType(imageUrl)){
            loadImageInstance.loadImage(getApplication(),iv,imageUrl,width,height,loadImgResId,errImgResId,isTransform,callback);
        }
    }

    /**
     * 加载图片是否支持该格式ImageUrl
     *
     * @param imageUrl
     * @return
     */
    public boolean isSupportImageUrlType(Object imageUrl){
        if(loadImageInstance.isSupportImageUrlType(imageUrl)){
            return true;
        }
        if(isDebug){
            throw new LoadImageException("ImageUrl format nonsupport.");
        }
        return false;
    }

}