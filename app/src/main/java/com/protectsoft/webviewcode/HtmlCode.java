package com.protectsoft.webviewcode;

/**
 *
 */
 class HtmlCode {


    static final String HTML_HEAD = "<html><head>";

    static final String HTML_HEAD_END = "</head><body>";

    static final String HTML_BODY_END = "</body></html>";


    static final String STYLE_TEXT_WRAP = "\"<style>\" +\n" +
            "                \"pre {\\n\" +\n" +
            "                \"    white-space: pre-wrap;       /* CSS 3 */\n" +
            "                \"    white-space: -moz-pre-wrap;  /* Mozilla, since 1999 */\n" +
            "                \"    white-space: -pre-wrap;      /* Opera 4-6 */\n" +
            "                \"    white-space: -o-pre-wrap;    /* Opera 7 */\n" +
            "                \"    word-wrap: break-word;       /* Internet Explorer 5.5+ */\n" +
            "                \"} \n" +
            "                \"</style>\n";


    static final String HTML_PRE_TEXT_START = "<pre><code class=\"nohighlight\">";
    static final String HTML_PRE_TEXT_END = "</code></pre> \n";

    static final String HTML_PRE_CODE_START = "<pre><code class=\"";
    static final String HTML_PRE_CODE_WRAP_START = "<pre style=\"white-space: pre-wrap;\"><code class=\"";
    static final String HTML_PRE_CODE_CLASS = "\">";
    static final String HTML_PRE_CODE_END = "</code></pre> \n";



}
