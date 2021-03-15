package com.beingyi.app.AE.update;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

public class UpdateInfoParser
{
    public static UpdateInfo getUpdateInfo(InputStream is) throws Exception
    {
        XmlPullParser  parser = Xml.newPullParser();  
        parser.setInput(is, BASE128.decode("YF+TOQhsLibxaX9DZrlrMw=="));
        int type = parser.getEventType();
        UpdateInfo info = new UpdateInfo();
        while (type != XmlPullParser.END_DOCUMENT)
        {
            switch (type)
            {
                case XmlPullParser.START_TAG:
                    if (BASE128.decode("40UEqLe7hY3HVTkOqpodYA==").equals(parser.getName()))
                    {
                        info.setVersion(parser.nextText()); 
                    }
                    else if (BASE128.decode("TDroNxxo64YIQk6RVasoFQ==").equals(parser.getName()))
                    {
                        info.setUrl(parser.nextText()); 
                    }
                    else if (BASE128.decode("1kIy+HjZyHoMZPAMfAlmgA==").equals(parser.getName()))
                    {
                        info.setDescription(parser.nextText()); 
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }
}
