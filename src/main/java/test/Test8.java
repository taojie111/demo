package test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Test8 {

    public static void main(String[] args) throws IOException {
        m1();
    }

    public static void m1() throws IOException {
        FileOutputStream fos = new FileOutputStream("D:\\data\\work\\devops\\1.txt");
        ObjectOutput out = new ObjectOutputStream(fos);
        out.writeUTF("test");
    }

    public static String byteToHex(byte[] bt){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<bt.length;i++){
            String tmpStr = Integer.toHexString(bt[i]);
            if(tmpStr.length()<2)
                sb.append("0");
            sb.append(tmpStr);
        }
        return sb.toString().toUpperCase();
    }

}
