package com.beingyi.app.AE.utils.editor;

import com.beingyi.app.AE.utils.AesUtil;
import com.beingyi.app.AE.utils.BASE128;

import com.beingyi.app.AE.utils.LEDataInputStream;
import com.beingyi.app.AE.utils.LEDataOutputStream;

import java.io.*;
import java.util.List;
import java.util.ArrayList;


public class AXmlDecoder{
    private static final int AXML_CHUNK_TYPE=0x00080003;
    public StringBlock mTableStrings;
    private final LEDataInputStream mIn;
    ByteArrayOutputStream byteOut=new ByteArrayOutputStream();

    private void readStrings() throws IOException{
        int type=mIn.readInt();
        checkChunk(type,AXML_CHUNK_TYPE);
        mIn.readInt();//Chunk size
        mTableStrings = StringBlock.read(this.mIn);
        byte[] buf=new byte[2048];
        int num;
        while((num=mIn.read(buf,0,2048))!=-1)
            byteOut.write(buf,0,num);
    }

    private AXmlDecoder(LEDataInputStream in){
        this.mIn=in;
    }


    public static AXmlDecoder read(InputStream input)throws IOException{
        AXmlDecoder axml=new AXmlDecoder(new LEDataInputStream(input));
        axml.readStrings();
        return axml;
    }

    public void write(List<String> list,OutputStream out)throws IOException{
        write(list,new LEDataOutputStream(out));
    }


    public void write(List<String> list,LEDataOutputStream out)throws IOException{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        LEDataOutputStream buf=new LEDataOutputStream(baos);
        mTableStrings.write(list,buf);
        buf.writeFully(byteOut.toByteArray());
        //write out
        out.writeInt(AXML_CHUNK_TYPE);
        out.writeInt(baos.size()+8);
        out.writeFully(baos.toByteArray());
    }



    public static void main(String[] args)throws IOException{

        FileInputStream file=new FileInputStream(BASE128.decode("YTP9bU7pDpQ/u52b5bd5Qw=="));
        AXmlDecoder axml=read(file);
        List<String> list=new ArrayList<String>();
        axml.mTableStrings.getStrings(list);
        for(int i=0;i<list.size();i++)
            System.out.println(i+BASE128.decode("Pj8ZNlHIJ/Iq78bmhIjsOg==")+list.get(i));
        FileOutputStream out =new FileOutputStream(BASE128.decode("JCJC4p8tJeBtg0brJB2RzA=="));
        axml.write(list,out);

    }



    private void checkChunk(int type,int expectedType) throws IOException {
        if (type != expectedType)
            throw new IOException(String.format(BASE128.decode("NzGQvBxQWej/DzbTRvzYlwT45W5CKndUEAET9RioUeoOSHIUdbowJ019ZaeTBPE7"), new Object[] { Integer.valueOf(expectedType), Short.valueOf((short)type) }));
    }

}
