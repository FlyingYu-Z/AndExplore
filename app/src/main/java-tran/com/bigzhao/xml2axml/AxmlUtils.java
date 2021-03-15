package com.bigzhao.xml2axml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParserException;

import com.beingyi.app.AE.application.MyApplication;
import com.bigzhao.xml2axml.test.AXMLPrinter;

import java.io.*;

/**
 * Created by Roy on 16-4-27.
 */
public class AxmlUtils {

    public static String decode(byte[] data) throws IOException, XmlPullParserException {

        InputStream is = new ByteArrayInputStream(data);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        AXMLPrinter.out = new PrintStream(os);
        AXMLPrinter.decode(is);
        byte[] bs = os.toByteArray();
        IOUtils.closeQuietly(os);
        AXMLPrinter.out.close();
        return new String(bs, "UTF-8");

    }

    public static String decode(File file) throws Exception {
        return decode(FileUtils.readFileToByteArray(file));
    }

    public static byte[] encode(String xml) throws IOException, XmlPullParserException {
        Encoder encoder = new Encoder();
        return encoder.encodeString(MyApplication.getContext(), xml);
    }

    public static byte[] encode(File file) throws IOException, XmlPullParserException {

        Encoder encoder = new Encoder();
        return encoder.encodeFile(MyApplication.getContext(), file.getAbsolutePath());

    }


    public static byte[] decodeByte(byte[] in) throws IOException, XmlPullParserException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AXMLPrinter.out = new PrintStream(byteArrayOutputStream);
        AXMLPrinter.decode(new ByteArrayInputStream(in));
        AXMLPrinter.out.close();
        return byteArrayOutputStream.toByteArray();
    }


    public static byte[] encodeByte(byte[] in) throws Exception {
        Encoder e = new Encoder();
        byte[] bs = e.encodeFile(MyApplication.getContext(), in);
        return bs;
    }


    public static byte[] decode(String in) throws FileNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        AXMLPrinter.out = new PrintStream(byteArrayOutputStream);
        AXMLPrinter.main(new String[]{in});
        AXMLPrinter.out.close();

        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] encodeString(String in) throws IOException, XmlPullParserException {
        Encoder e = new Encoder();
        byte[] bs = e.encodeString(MyApplication.getContext(), in);

        return bs;
    }


    public static void encode(String in, String out) throws IOException, XmlPullParserException {
        Encoder e = new Encoder();
        byte[] bs = e.encodeFile(MyApplication.getContext(), in);
        FileUtils.writeByteArrayToFile(new File(out), bs);
    }

    public static void decode(String in, String out) throws FileNotFoundException {
        AXMLPrinter.out = new PrintStream(new File(out));
        AXMLPrinter.main(new String[]{in});
        AXMLPrinter.out.close();
    }


}
