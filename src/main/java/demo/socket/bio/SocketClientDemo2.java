package demo.socket.bio;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClientDemo2 {

    public static void main(String[] args) throws InterruptedException {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("localhost",6666);
            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("我是客户端：您好server!~");
            pw.flush();
            socket.shutdownOutput();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
