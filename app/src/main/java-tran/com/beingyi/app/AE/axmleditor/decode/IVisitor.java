package com.beingyi.app.AE.axmleditor.decode;

import com.beingyi.app.AE.application.MyApplication;
public interface IVisitor {
	public void visit(BNSNode node);
	public void visit(BTagNode node);
	public void visit(BTXTNode node);
}
