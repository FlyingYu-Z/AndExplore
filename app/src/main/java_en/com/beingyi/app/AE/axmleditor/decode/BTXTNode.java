package com.beingyi.app.AE.axmleditor.decode;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import java.io.IOException;
import java.util.List;

public class BTXTNode extends BXMLNode implements IVisitable{
	private final int TAG = 0x00100104;
	private int mRawName;
	
	public void checkTag(int value) throws IOException{
		super.checkTag(TAG, value);
	}
	
	@SuppressWarnings("unused")
	public void readStart(IntReader reader) throws IOException{
		super.readStart(reader);
		
		mRawName = reader.readInt();
		
		int skip0 = reader.readInt();
		int skip1 = reader.readInt();
	}

	public void readEnd(IntReader reader) throws IOException{
	}
	
	public void prepare(){
		
	}
	
	public void writeStart(IntWriter writer) throws IOException{
		writer.writeInt(TAG);
		super.writeStart(writer);
		writer.writeInt(mRawName);
		
		writer.writeInt(0);//skiped
		writer.writeInt(0);//skiped
	}
	
	public void writeEnd(IntWriter writer){
		
	}
	
	public int getName(){
		return mRawName;
	}
	
	public boolean hasChild(){
		return false;
	}
	
	public List<BXMLNode> getChildren(){
		throw new RuntimeException(BASE128.decode("l+JsP+4KsOmvXUeZj2U2goMvHLlV8I1zyvK4n3tuUWc="));
	}
	
	public void addChild(BXMLNode node){
		throw new RuntimeException(BASE128.decode("SMelLUTdNKsGKSy09uLVrv2UJElMuKe4lSmeWqJtUXM="));
	}
	
	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}

}
