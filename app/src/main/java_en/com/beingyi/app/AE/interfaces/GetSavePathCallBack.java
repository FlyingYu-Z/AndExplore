package com.beingyi.app.AE.interfaces;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
public interface GetSavePathCallBack
{
    
    public void onSuccess(String filePath);
    public void onCancel();
    
}
