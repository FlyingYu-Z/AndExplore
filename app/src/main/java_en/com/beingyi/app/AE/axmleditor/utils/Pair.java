package com.beingyi.app.AE.axmleditor.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
public class Pair<T1,T2> {
	public T1 first;
	public T2 second;

	public Pair(T1 t1, T2 t2){
		first = t1;
		second = t2;
	}
	
	public Pair(){}


	public static <A, B> Pair <A, B> create(A a, B b) {
		return new Pair<A,B>(a, b);
	}
}
