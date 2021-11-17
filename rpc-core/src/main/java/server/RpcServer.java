package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Ed
 * @create 2021-11-16 16:28
 */
public class RpcServer {

    private final ExecutorService threadPool;

    public RpcServer(){
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    public void register(Object service, int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器正在启动...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                System.out.println("客户端连接！Ip为：" + socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket, service));
            }
        } catch (IOException e) {
            System.out.println("连接时有错误发生：" + e);
        }
    }

}
