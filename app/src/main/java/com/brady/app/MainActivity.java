package com.brady.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.brady.app.provider.Data;
import com.qzybj.libimageload.LoadImageManager;

public class MainActivity extends AppCompatActivity {

    private ImageView showIv;
    private ImageView show1Iv;
    private ImageView show2Iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadImageManager.init(this.getApplication());
        initView();
        initData();
    }

    private void initView() {
        showIv = getView(R.id.iv_show);
        show1Iv = getView(R.id.iv_show1);
        show2Iv = getView(R.id.iv_show2);
    }

    private void initData() {
        LoadImageManager.instance().loadImage(showIv, Data.URLS[0]);
        LoadImageManager.instance().loadImage(show1Iv, Data.URLS[1]);
        LoadImageManager.instance().loadImage(show2Iv, Data.URLS[2]);
    }

    /**
     * 采用泛型的方式查找View，在使用的时候不用强转
     * @param id
     * @return
     */
    public <T> T getView(int id){
        return (T)findViewById(id);
    }
}
