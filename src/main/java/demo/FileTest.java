package demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileTest {

    public static void main(String[] args) {
        FileTest fileTest = new FileTest();
        try {
            fileTest.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile() throws Exception {
        InputStream in = this.getClass().getResourceAsStream("/ansibleTemplate/elasticsearch.yml.j2");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String s = "";
        while ((s =reader.readLine()) != null) {
            sb.append(s + "\n");
            System.out.println(s);
        }
        reader.close();
        String str = sb.toString();
        System.out.println(str);
    }
}
