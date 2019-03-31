package com.fifi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建channel 监听8080端口
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // ServerSocketChannel调用自身的socket()方法获取ServerSocket
        ssc.socket().bind(new InetSocketAddress(8080));

        // 设置为非阻塞模式
        ssc.configureBlocking(false);

        // 注册选择器
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        // 创建处理器
        Handler handler = new Handler(1024);

        while (true){
            // 等待请求，每次等待阻塞3s，超过3s后线程继续向下运行，如果传入0或者不穿参数将一直阻塞
            if (selector.select(3000)==0){
                System.out.println("等待请求超时......");
                continue;
            }
            System.out.println("处理请求......");
            // 获取待处理的SelectionKey
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

            while (keyIter.hasNext()){
                SelectionKey key = keyIter.next();

                try {


                if (key.isAcceptable()){
                    handler.handleAccept(key);
                }
                if (key.isReadable()){
                    handler.handleRead(key);
                }}catch (IOException ix){
                    keyIter.remove();
                    continue;
                }

                // 处理完后，从待处理的SelectionKey迭代器中移除当前所使用的key
                keyIter.remove();
            }
        }
    }

    /**
     * 自定义处理器
     */
    private static class Handler{
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";

        public Handler(){}
        public Handler(int bufferSize){
            this(bufferSize, null);
        }
        public Handler(String localCharset){
            this(-1, localCharset);
        }
        public Handler(int bufferSize, String localCharset){
            if (bufferSize>0){
                this.bufferSize = bufferSize;
            }

            if (localCharset!=null){
                this.localCharset = localCharset;
            }
        }

        public void handleAccept(SelectionKey key) throws IOException {
            SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey key) throws IOException {
            // 获取Channel
            SocketChannel sc = (SocketChannel)key.channel();

            // 获取buffer并重置
            ByteBuffer buffer = (ByteBuffer)key.attachment();
            buffer.clear();
            // 没有读到内容则关闭
            if (sc.read(buffer) == -1){
                sc.close();
            }else {
                // 将buffer转换为读状态
                buffer.flip();

                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("received from client: " + receivedString);

                // 返回数据给客户端
                String sendString = "received data: " + receivedString;
                buffer = ByteBuffer.wrap(sendString.getBytes(localCharset));
                sc.write(buffer);

                // 关闭Socket
                sc.close();
            }
        }


    }

    private static class HttpHandler implements Runnable{
        private int bufferSize = 1024;
        private String localCharset = "UTF-8";
        private SelectionKey key;

        public HttpHandler(SelectionKey key){
            this.key = key;
        }

        public void handleAccept(SelectionKey key) throws IOException {
            SocketChannel sc = ((ServerSocketChannel)key.channel()).accept();
            sc.configureBlocking(false);
            sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
        }

        public void handleRead(SelectionKey key) throws IOException {
            // 获取Channel
            SocketChannel sc = (SocketChannel)key.channel();

            // 获取buffer并重置
            ByteBuffer buffer = (ByteBuffer)key.attachment();
            buffer.clear();
            // 没有读到内容则关闭
            if (sc.read(buffer) == -1){
                sc.close();
            }else {
                // 将buffer转换为读状态
                buffer.flip();

                String receivedString = Charset.forName(localCharset).newDecoder().decode(buffer).toString();
                System.out.println("received from client: " + receivedString);

               // 控制台打印请求报文头
                String[] requestMessage = receivedString.split("\r\n");
                for (String s : requestMessage){
                    System.out.println(s);
                    // 遇到空行说明报文头已经打印完
                    if (s.isEmpty()){
                        break;
                    }
                }

                // 控制台打印首行信息
                String[] firstLine = requestMessage[0].split(" ");
                System.out.println();
                System.out.println("Method:\t"+firstLine[0]);
                System.out.println("url:\t"+firstLine[1]);
                System.out.println("HTTP Version:\t"+firstLine[2]);
                System.out.println();

                // 返回客户端
                StringBuilder sendString = new StringBuilder();
                sendString.append("HTTP/1.1 200 OK\r\n"); // 响应报文首行，200表示处理成功
                sendString.append("Content-Type:text/html;charset="+localCharset+"\r\n");
                sendString.append("\r\n"); // 报文头结束后加一个空行

                sendString.append("<html><head><title>显示报文</title></head><body>");
                sendString.append("接受到的请求报文是:<br/>");
                for (String s : requestMessage){
                    sendString.append(s + "<br/>");
                }
                sendString.append("</body></html>");
                buffer = ByteBuffer.wrap(sendString.toString().getBytes(localCharset));

                sc.write(buffer);
                // 关闭Socket
                sc.close();
            }
        }

        public void run() {
            try{
                if (key.isAcceptable()){
                    handleAccept(key);
                }

                if (key.isReadable()){
                    handleRead(key);
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}
