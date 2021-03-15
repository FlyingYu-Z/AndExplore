package com.beingyi.app.AE.utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import maobyte.xml.decode.AXmlDecoder;
import maobyte.xml.decode.AXmlResourceParser;
import org.xmlpull.v1.XmlPullParser;


public class ManifestParse
{
    public static void writeInt ( byte[] data, int off, int value )
    {
        data [ off++ ] = (byte) ( value & 0xFF );
        data [ off++ ] = (byte) ( ( value >>> 8 ) & 0xFF );
        data [ off++ ] = (byte) ( ( value >>> 16 ) & 0xFF );
        data [ off ] = (byte) ( ( value >>> 24 ) & 0xFF );
    }
    public static int readInt ( byte[] data, int off )
    {
        return data [ off + 3 ] << 24 | ( data [ off + 2 ] & 0xFF ) << 16 | ( data [ off + 1 ] & 0xFF ) << 8
            | data [ off ] & 0xFF;
    }
    public static List<String> parseManifestActivity ( InputStream is ) throws IOException
    {
        String PackageName = null;
        List<String> list=new ArrayList<String> ( );
        AXmlDecoder axml = AXmlDecoder.decode ( is );
        AXmlResourceParser parser = new AXmlResourceParser ( );
        parser.open ( new ByteArrayInputStream ( axml.getData ( ) ), axml.mTableStrings );
        int type;
        while ( ( type = parser.next ( ) ) != XmlPullParser.END_DOCUMENT )
        {
            if ( type != XmlPullParser.START_TAG )
                continue;
            if ( parser.getName ( ).equals ( "manifest" ) )
            {
                int size = parser.getAttributeCount ( );
                for ( int i = 0; i < size; ++i )
                {
                    if ( parser.getAttributeName ( i ).equals ( "package" ) )
                        PackageName = parser.getAttributeValue ( i );
                }
            }
            else if ( parser.getName ( ).equals ( "activity" ) )
            {
                int size = parser.getAttributeCount ( );
                for ( int i = 0; i < size; ++i )
                {
                    if ( parser.getAttributeNameResource ( i ) == 0x01010003 )
                    {
                        String name=parser.getAttributeValue ( i );
                        if ( name.startsWith ( "." ) )
                        {
                            name = PackageName + name;
                        }
                        list.add ( name );
                    }
                }
            }
        }
        return list;
    }
}


