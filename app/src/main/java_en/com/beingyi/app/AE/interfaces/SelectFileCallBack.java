package com.beingyi.app.AE.interfaces;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
public interface SelectFileCallBack
{

    public void onSelected(String selectedPath);
    public void onCancel();


}
