package demo;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/*
 * 转换过程：
 * 1、'严'转二进制：'严' ==> 0100111000100101
 * 2、二进制转UTF-8编码格式：0100111000100101 ==> 11100100 10111000 10100101
 * */

public class CharToByteArrayDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        char c1 = '严';
        char c2='\u4E25';
        char c3 = 97;
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        String str = new String(new char[]{c1});
        System.out.println(Arrays.toString(str.getBytes("UTF-8")));
    }
}
