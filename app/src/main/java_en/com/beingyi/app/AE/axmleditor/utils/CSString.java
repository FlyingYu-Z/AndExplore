package com.beingyi.app.AE.axmleditor.utils;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
/**
 * @author Dmitry Skiba
 *
 * Helper class, used in Cast.toCharSequence.
 */
public class CSString implements CharSequence {
        
        public CSString(String string) {
                if (string==null) {
                        string=BASE128.decode("FzrznU3UuD8KQAiBS9XCeA==");
                }
                m_string=string;
        }

        public int length() {
                return m_string.length();
        }
        
        public char charAt(int index) {
                return m_string.charAt(index);
        }
        
        public CharSequence subSequence(int start,int end) {
                return new CSString(m_string.substring(start,end));
        }
        
        public String toString() {
                return m_string;
        }
        
        private String m_string;
}
