package com.myopicmobile.textwarrior.common;

public class LanguageSmali extends Language {

    private final static String[] keywords = {
            ".class", "public", "/", ".method", "constructor", "move-object", "return-void", ".end method", ".register", "invoke-direct",
            "private", "const/4", "invoke-virtual", "if-eqz", "if-nez", "goto", "catch", "new-instance",
            "move-exception", "throw", ".annotation", ".end annotation", "iget-object", "protected", "array-length", "cond_"
    };

    public LanguageSmali(){
        super.setKeywords(keywords);
    }

    /**
     * Java has no preprocessors. Override base class implementation
     */
    public boolean isLineAStart(char c){
        return false;
    }
}
