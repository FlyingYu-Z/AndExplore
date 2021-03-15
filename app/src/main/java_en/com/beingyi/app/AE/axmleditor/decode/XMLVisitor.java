package com.beingyi.app.AE.axmleditor.decode;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import com.beingyi.app.AE.axmleditor.utils.TypedValue;

public class XMLVisitor implements IVisitor{
	private StringBlock mStrings;
	private ResBlock mRes;
	
	private int depth;
	
	public XMLVisitor(StringBlock sb){
		mStrings = sb;
	}

	/**
	 * print header
	 * print child
	 * print tail
	 */
	@Override
	public void visit(BNSNode node) {
		int prefix = node.getPrefix();
		int uri = node.getUri();

		String line1 = String.format(BASE128.decode("RbqUzBwKtJ/JTzavmLYP3w=="), getStringAt(prefix) , getStringAt(uri));
		
		System.out.println(line1);
		
		if(node.hasChild()){
			for(BXMLNode child : node.getChildren()){
				child.accept(this);
			}
		}
	}

	@Override
	public void visit(BTagNode node) {
		if(!node.hasChild()){
			print(BASE128.decode("PFCKLbVqkcv0h38e5cJ60A==")+ getStringAt(node.getName()));
			printAttribute(node.getAttribute());
			print(BASE128.decode("FMqIdkKkexvh+zi+lQQRSA=="));
		}else{
			print(BASE128.decode("PFCKLbVqkcv0h38e5cJ60A==")+ getStringAt(node.getName()));
			depth ++;
			printAttribute(node.getAttribute());
			print(BASE128.decode("JcAtkmUKMZ6Tz0nZ9z3g4A=="));
			
			for(BXMLNode child : node.getChildren()){
				child.accept(this);
			}
			depth --;
			print(BASE128.decode("iLSc+MTefyvDkxI5NXFjRA==") + getStringAt(node.getName()) + BASE128.decode("JcAtkmUKMZ6Tz0nZ9z3g4A=="));
		}
	}
	
	public void visit(BTXTNode node){
		print(BASE128.decode("OdpabCozGG1eu90QsmaDjA=="));
	}
	
	private void printAttribute(BTagNode.Attribute[] attrs){
		for(BTagNode.Attribute attr : attrs){
			StringBuilder sb = new StringBuilder();
			
			if(attr.hasNamespace()){
				sb.append(BASE128.decode("SG9fQp8kqGMXghNgWSPx5g==")).append(':');
			}
			String name = getStringAt(attr.mName);
			if(BASE128.decode("OkKB3pIOxjH98lsk+6V8UA==").equals(name)){
				System.out.println(BASE128.decode("USRbMTqr1N1Xrr51gG0ShQ=="));
			}
			sb.append(name).append('=');
			sb.append('\"').append(getAttributeValue(attr)).append('\"');
			
			print(sb.toString());
		}
	}
	final String intent = BASE128.decode("IUbD1BIqaKfaeT3XPqMgsiFGw9QSKmin2nk91z6jILIXOvOdTdS4PwpACIFL1cJ4");
	final int step = 4;
	private void print(String str){
		System.out.println(intent.substring(0, depth*step)+str);
	}
	
	private String getStringAt(int index){
		return mStrings.getStringFor(index);
	}
	
	@SuppressWarnings("unused")
	private int getResIdAt(int index){
		//TODO final res result in resources.arsc
		return mRes.getResourceIdAt(index);
	}
	
	private String getAttributeValue(BTagNode.Attribute attr) {
		int type = attr.mType >> 24;
		int data = attr.mValue;
		
		if (type== TypedValue.TYPE_STRING) {
			return mStrings.getStringFor(attr.mString);
		}
		if (type==TypedValue.TYPE_ATTRIBUTE) {
			return String.format(BASE128.decode("Vvqp5rl1B41nDUk32y4TqQ=="),getPackage(data),data);
		}
		if (type==TypedValue.TYPE_REFERENCE) {
			return String.format(BASE128.decode("m48S1OPYELuhzsVzLHs2Rw=="),getPackage(data),data);
		}
		if (type==TypedValue.TYPE_FLOAT) {
			return String.valueOf(Float.intBitsToFloat(data));
		}
		if (type==TypedValue.TYPE_INT_HEX) {
			return String.format(BASE128.decode("z6PQXBop67tGNI0B/3vn2g=="),data);
		}
		if (type==TypedValue.TYPE_INT_BOOLEAN) {
			return data!=0?BASE128.decode("bF8Crciu2D3NL2feXAChUA=="):BASE128.decode("diSwdWTUcXZ4XUrz00I8JQ==");
		}
		if (type==TypedValue.TYPE_DIMENSION) {
			return Float.toString(complexToFloat(data))+
				DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type==TypedValue.TYPE_FRACTION) {
			return Float.toString(complexToFloat(data))+
				FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
		}
		if (type>=TypedValue.TYPE_FIRST_COLOR_INT && type<=TypedValue.TYPE_LAST_COLOR_INT) {
			return String.format(BASE128.decode("ymJHjBWLNulZS7EfieG53g=="),data);
		}
		if (type>=TypedValue.TYPE_FIRST_INT && type<=TypedValue.TYPE_LAST_INT) {
			return String.valueOf(data);
		}
		return String.format(BASE128.decode("9Fa5mN8lxamLtKBydKDp6BY3i0qFtfQq4/BRYpLLvN4="),data,type);
	}
	
	private String getPackage(int id) {
		if (id>>>24==1) {
			return BASE128.decode("UbiPif/JJoO3DlCpQ6RMeQ==");
		}
		return BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
	}
	
	/////////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
		
	public static float complexToFloat(int complex) {
	return (float)(complex & 0xFFFFFF00)*RADIX_MULTS[(complex>>4) & 3];
	}
	
	private static final float RADIX_MULTS[]={
	0.00390625F,3.051758E-005F,1.192093E-007F,4.656613E-010F
	};
	private static final String DIMENSION_UNITS[]={
	BASE128.decode("+2LDVcNRJIsMV0w6LH6Tow=="),BASE128.decode("Z1ilmJBPKLHI+Rzy08KOpw=="),BASE128.decode("vhoUkn8+bRK7bvcJewUisQ=="),BASE128.decode("txWzCGhRFx+ZnblI4yBPZg=="),BASE128.decode("xkXujCKGXTuQrByvVFOOyA=="),BASE128.decode("DrZlbvVpttVjK8hmO9OHdw=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")
	};
	private static final String FRACTION_UNITS[]={
	BASE128.decode("4FTQ/2dtU0mUW7Qz2u+D0g=="),BASE128.decode("I/yW9mkSNBRdA0BhRcqUJw=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA=="),BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==")
	};
}
