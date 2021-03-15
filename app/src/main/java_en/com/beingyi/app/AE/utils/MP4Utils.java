package com.beingyi.app.AE.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import com.beingyi.app.AE.R;
import com.beingyi.app.AE.application.MyApplication;
import java.util.HashMap;

public class MP4Utils {
    public static Drawable getIcon(String str) {
        
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(str, new HashMap());
            return new BitmapDrawable(mediaMetadataRetriever.getFrameAtTime((long) 1, 2));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ImgHelper.getDrawableFromResources(MyApplication.getContext(), R.drawable.ic_unable_png);
        }
    }
}
