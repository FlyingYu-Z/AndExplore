package com.beingyi.app.AE.axmleditor.utils;

import com.beingyi.app.AE.application.MyApplication;

/**
 * @author Dmitry Skiba
 * 
 */
public class Cast {
        
        public static final CharSequence toCharSequence(String string) {
                if (string==null) {
                        return null;
                }
                return new CSString(string);
        }
}
