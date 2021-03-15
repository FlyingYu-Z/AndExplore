package com.protectsoft.webviewcode;

/**
 *
 */
public final class Settings {

    //avoid instantiation
    private Settings() {
    }

    public final class Lang {

        public static final String JAVA = "java";
        public static final String PYTHON = "python";
        public static final String JAVASCRIPT = "javascript";
        public static final String RUBY = "ruby";
        public static final String CSHARP = "c#";
        public static final String PHP = "php";
        public static final String SQL = "sql";
        public static final String CPLUSPLUS = "c++";

    }

    public final class WithStyle {

        public static final String DEFAULT =                HighlightLib.CSS_DEFAULT;
        public static final String AGATE =                  HighlightLib.CSS_AGATE;
        public static final String ANDROIDSTUDIO    =       HighlightLib.CSS_ANDROIDSTUDIO;
        public static final String ARDUINO_LIGHT =          HighlightLib.CSS_ARDUINO;
        public static final String ARTA =                   HighlightLib.CSS_ARTA;
        public static final String ASCETIC =                HighlightLib.CSS_ASCETIC;
        public static final String ATELIER_DARK =           HighlightLib.CSS_ATELIER_DARK;
        public static final String ATELIER_LIGHT =          HighlightLib.CSS_ATELIER_LIGHT;
        public static final String ATELIER_FOREST_DARK =    HighlightLib.CSS_ATELIER_FOREST_DARK;
        public static final String DARKSTYLE =              HighlightLib.CSS_DARKSTYLE;
        public static final String DARKULA =                HighlightLib.CSS_DARKULA;
        public static final String DOCCO =                  HighlightLib.CSS_DOCCO;
        public static final String FAR =                    HighlightLib.CSS_FAR;
        public static final String GITHUB =                 HighlightLib.CSS_GITHUB;
        public static final String GITHUBGIST =             HighlightLib.CSS_GIHUBGIST;
        public static final String GOOGLECODE =             HighlightLib.CSS_GOOGLECODE;
        public static final String IDEA =                   HighlightLib.CSS_IDEA;
        public static final String MAGULA =                 HighlightLib.CSS_MAGULA;
        public static final String OBSIDIAN =               HighlightLib.CSS_OBSIDIAN;
        public static final String XCODE =                  HighlightLib.CSS_XCODE;

    }


    //not implemented yet
    public final class MimeType {

        public static final String TEXT_HTML = "text/html";
        public static final String TEXT_PLAIN = "text/plain";

    }

    //not implemented yet
    public final class Charset {

        public static final String UTF_8 = "utf-8";

    }

    //not implemented yet
    public final class TextWrap {

        public static final String PRE_WRAP = "pre-wrap";

    }

}
