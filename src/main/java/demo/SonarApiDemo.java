package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import utils.Base64Encoder;
import utils.HttpClientUtil;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class SonarApiDemo {

    // 172.20.23.182:5001/library/ta404-project-starter:20210510014907
    public static void main(String[] args) {
        String s = "apiVersion: extensions/v1beta1\n" +
                "kind: Deployment\n" +
                "metadata:\n" +
                "  name: devops-register-deployment\n" +
                "  namespace: devops\n" +
                "spec:\n" +
                "  replicas: 1\n" +
                "  selector:\n" +
                "    matchLabels:\n" +
                "      devops: devops-register-pod\n" +
                "  template:\n" +
                "    metadata:\n" +
                "      labels:\n" +
                "        devops: devops-register-pod\n" +
                "    spec:\n" +
                "      restartPolicy: Always\n" +
                "      containers:\n" +
                "      - name: devops-register-peer1-containers\n" +
                "        ${image: 172.20.23.182:5001/library/devops-register:peer1}\n" +
                "        imagePullPolicy: IfNotPresent\n" +
                "        securityContext:\n" +
                "          privileged: true\n" +
                "        ports:\n" +
                "        - containerPort: 9001\n" +
                "      - name: devops-register-peer2-containers\n" +
                "        image: 172.20.23.182:5001/library/devops-register:peer2\n" +
                "        imagePullPolicy: IfNotPresent\n" +
                "        securityContext:\n" +
                "          privileged: true\n" +
                "        ports:\n" +
                "        - containerPort: 9002";
        String result = s.replaceAll("(\\$\\{[\\s\\S]*)\\}", "image: 172.20.23.182:5001/library/ta404-project-starter:20210510014907");
        System.out.println(result);
    }
    public static void testList(List<String> list) {
        list.add("b");
    }


    public static Map<String, String> deleteUser() {
        Map<String, String> map = HttpClientUtil.doDelete(getUrl("http://172.20.23.219:8081/service/rest/v1/security/users" + "/test4", null), getHeader());
        return map;
    }

    public static Map<String, String> deleteRepository() {
        Map<String, String> map = HttpClientUtil.doDelete(getUrl("http://172.20.23.219:8081/service/rest/v1/repositories" + "/test2", null), getHeader());
        return map;
    }

    public static Map<String, String> deleteRole() {
        Map<String, String> map = HttpClientUtil.doDelete(getUrl("http://172.20.23.219:8081/service/rest/v1/security/roles" + "/test5", null), getHeader());
        return map;
    }

    public static Map<String, Object> getUser() {
        StringBuilder builder = new StringBuilder();
        builder.append("&userId=" + "devops2");
        if (builder.length() > 0) {
            builder.delete(0, 1);
        }
        Map<String, Object> map = HttpClientUtil.doGetHttp(getUrl("http://172.20.23.219:8081/service/rest/v1/security/users", builder.toString()), getHeader());
        JSONArray array = JSON.parseArray((String) map.get("response"));
        return map;
    }

    public static Map<String, Object> getRepository() {
        Map<String, Object> map = HttpClientUtil.doGetHttp(getUrl("http://172.20.23.219:8081/service/rest/v1/repositories/maven/hosted" + "/test222", null), getHeader());
        JSONObject array = JSON.parseObject((String) map.get("response"));
        return map;
    }

    public static Map<String, Object> getRole() {
        StringBuilder builder = new StringBuilder();
        builder.append("&source=" + "default");
        if (builder.length() > 0) {
            builder.delete(0, 1);
        }
        Map<String, Object> map = HttpClientUtil.doGetHttp(getUrl("http://172.20.23.219:8081/service/rest/v1/security/roles" + "/" + "devops2", builder.toString()), getHeader());
        JSONObject array = JSON.parseObject((String) map.get("response"));
        return map;
    }

    public static Map<String, Object> createUser() {
        String[] arr = new String[]{"123"};
        Map<String, Object> params = new HashMap<>();
        params.put("userId", "test5");
        params.put("firstName", "firstName");
        params.put("lastName", "lastName");
        params.put("emailAddress", "1527938655@qq.com");
        params.put("password", "test");
        params.put("status", "active");
        params.put("roles", arr);
        String json = JSON.toJSONString(params);
        Map<String, Object> map = HttpClientUtil.doPost3(getUrl("http://172.20.23.219:8081/service/rest/v1/security/users", null), getHeader(), json);
        return map;
    }

    public static Map<String, Object> createRepository() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "test15");
        params.put("online", true);
        Map<String, Object> storageMap = new HashMap<>();
        storageMap.put("blobStoreName", "default");
        storageMap.put("strictContentTypeValidation", false);
        storageMap.put("writePolicy", "allow_once");
        params.put("storage", storageMap);
        Map<String, Object> mavenMap = new HashMap<>();
        mavenMap.put("versionPolicy", "SNAPSHOT");
        mavenMap.put("layoutPolicy", "STRICT");
        mavenMap.put("contentDisposition", "INLINE");
        params.put("maven", mavenMap);
        /*Map<String, Object> componentMap = new HashMap<>();
        componentMap.put("deploymentPolicy", "ALLOW REDEPLOY");
        componentMap.put("proprietaryComponents", true);
        params.put("component", componentMap);*/
        String json = JSON.toJSONString(params);
        Map<String, Object> map = HttpClientUtil.doPost3(getUrl("http://172.20.23.219:8081/service/rest/v1/repositories/maven/hosted", null), getHeader(), json);
        return map;
    }

    public static Map<String, Object> createRole() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "test5");
        params.put("name", "test5");
        /*params.put("description", "test2");
        params.put("privileges", null);
        params.put("roles", null);*/
        String json = JSON.toJSONString(params);
        Map<String, Object> map = HttpClientUtil.doPost3(getUrl("http://172.20.23.219:8081/service/rest/v1/security/roles", null), getHeader(), json);
        return map;
    }

    private static String getUrl(String sonarApi, String params) {
        if (params == null) {
            return sonarApi;
        }
        return sonarApi + "?" + params;
    }

    public static Map<String, String> getHeader() {
        Map<String, String> header = new HashMap<>();
        String a = null;
        try {
            a = Base64Encoder.encode(("admin" + ":" + "admin").getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码失败");
        }
        header.put("Authorization","Basic " + a);
        return header;
    }

}
