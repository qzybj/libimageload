package com.qzybj.libimageload.interfaces.impl.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.qzybj.libimageload.interfaces.ILoadImage;
import com.qzybj.libimageload.interfaces.ILoadImageCallback;
import com.qzybj.libimageload.interfaces.impl.picasso.transformation.ScaleTransformation;
import com.qzybj.libraryutil.data.StringUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;


/**
 * Created by ZhangYuanBo on 2016/6/16.
 * 图片加载的默认实现
 */
public class DefPicassoImpl implements ILoadImage {

    @Override
    public <T extends ILoadImageCallback> void downloadImage(Context con, Object imageUrl, T callback) {
        if(con!=null&&callback instanceof PicassoCallback){
            RequestCreator requestCreator = getRequestCreator(con,imageUrl);
            if(requestCreator!=null){
                requestCreator.into((PicassoCallback)callback);
            }
        }
    }

    /**
     *  加载图片(Picasso)
     * @param con
     * @param iv
     * @param imageUrl   支持的格式： load(R.drawable.landing_screen); load("file:///android_asset/DvpvklR.png");load(new File(...))
     * @param width     指定的图片宽
     * @param height    指定的图片高
     * @param loadImgResId 默认加载的图片
     * @param errImgResId   加载错误时的图片
     * @param isTransform   是否加载动画
     * @param callback
     */
    @Override
    public <T extends ILoadImageCallback> void loadImage(Context con, ImageView iv, Object imageUrl, int width, int height, int loadImgResId, int errImgResId, boolean isTransform, T callback) {
        if(con!=null&&iv!=null){
            RequestCreator requestCreator =getRequestCreator(con,imageUrl);
            if(requestCreator!=null){
                //requestCreator.skipMemoryCache();//no cache
                //requestCreator.noPlaceholder();//set no Placeholder default image
                //requestCreator.priority(Priority priority);//set request priority
                requestCreator.config(Bitmap.Config.ARGB_8888);
                //requestCreator.fit();//self adaptation (ps:Conflict with method fetch())
                //requestCreator.centerCrop();//Set the ImageView ScaleType attribute,other method centerInside()
                if(width!= ILoadImage.NONE&&height!= ILoadImage.NONE){
                    requestCreator.resize(width, height);//set image display pixels number
                    //requestCreator.resizeDimen(targetWidthResId,targetHeightResId);//set image display pixels by ResId
                }
                //requestCreator.rotate(float degrees);//set rotate degrees
                //requestCreator.rotate(float degrees, float pivotX, float pivotY);//Set rotation degrees to a central point.
                if(isTransform){
                    //requestCreator.noFade();//No fade animation
                    if(width!= ILoadImage.NONE&&height!= ILoadImage.NONE){
                        requestCreator.transform(new ScaleTransformation(width, height));//set animation
                    }
                }
                if (callback != null&&callback instanceof PicassoCallback) {
                    PicassoCallback mCallback  = (PicassoCallback)callback;
                    requestCreator.into(mCallback);
                }
                if (loadImgResId!= ILoadImage.NONE&&loadImgResId>0) {
                    requestCreator.placeholder(loadImgResId);//Placeholder default image
                }
                if (errImgResId!= ILoadImage.NONE&&errImgResId>0) {
                    requestCreator.error(errImgResId);//error image
                }
                requestCreator.into(iv);
            }else{
                iv.setImageBitmap(null);
            }
        }
    }

    @Override
    public  boolean isSupportImageUrlType(Object imageUrl){
        if(imageUrl instanceof String){
            return true;
        }
        if(imageUrl instanceof Integer){
            return true;
        }
        if(imageUrl instanceof File){
            return true;
        }
        if(imageUrl instanceof Uri){
            return true;
        }
        return false;
    }

    /**
     * 根据不同的Url类型做对应的处理,转换成图片请求 RequestCreator
     * @param con
     * @param imageUrl
     * @return
     */
    private RequestCreator getRequestCreator(Context con, Object imageUrl){
        RequestCreator requestCreator = null;
        if(imageUrl instanceof String){
            String imageStr = (String)imageUrl;
            if(StringUtil.isNotEmpty(imageStr)){
                requestCreator = Picasso.with(con).load(imageStr);
            }
        }else if(imageUrl instanceof Integer){
            int imageInt = (int)imageUrl;
            if(imageInt>0){
                requestCreator = Picasso.with(con).load(imageInt);
            }
        }else if(imageUrl instanceof File){
            File file = (File)imageUrl;
            if(file.exists()){
                requestCreator = Picasso.with(con).load(file);
            }
        }else if(imageUrl instanceof Uri) {
            Uri uri = (Uri) imageUrl;
            requestCreator = Picasso.with(con).load(uri);
        }
        return requestCreator;
    }
}
