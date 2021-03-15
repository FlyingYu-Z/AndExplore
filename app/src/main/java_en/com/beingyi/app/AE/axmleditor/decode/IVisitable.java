package com.beingyi.app.AE.axmleditor.decode;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
public interface IVisitable {
	public void accept(IVisitor v);  
}
