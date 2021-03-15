package com.beingyi.app.AE.axmleditor.editor;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
/**
 * Created by zl on 15/9/8.
 */
public interface XEditor {

    String NAME_SPACE = BASE128.decode("MoEZZXQ0fWnjgxbtxXcnQfNXG+E4vmcckQF1fu0odR4MYCPDO5AKwi1KfR/gWX/P");

    String NODE_MANIFEST=BASE128.decode("uAEhcaHHd/l/2BvMahO6KA==");
    String NODE_APPLICATION=BASE128.decode("u+l7lWsalgqYsU+0BQNEbw==");
    String NODE_METADATA=BASE128.decode("aDxWJ93ZD6gpVf0jpArDSA==");

    String NODE_USER_PREMISSION =BASE128.decode("X2e1hxgpt9U9NeCHlxfDNw==");
    String NODE_SUPPORTS_SCREENS=BASE128.decode("YcBRMsUbVvB5JaCNMzRqARc6851N1Lg/CkAIgUvVwng=");

    String NAME = BASE128.decode("zY9Kfdk3u2abqgZR++wJEg==");
    String VALUE = BASE128.decode("p3LNZaXOevGNfPRwKcRrAw==");

    void setEditor(String attrName, String attrValue);

    void commit();


}
