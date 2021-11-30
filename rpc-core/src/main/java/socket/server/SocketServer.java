package socket.server;

import core.RequestHandler;
import core.RpcServer;
import registry.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Ed
 * @create 2021-11-16 16:28
 */
public class SocketServer implements RpcServer {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;


    public SocketServer(ServiceRegistry serviceRegistry){
        this.serviceRegistry = serviceRegistry;
        BlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workingQueue, threadFactory);
    }

    @Override
    public void start(int port){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务器正在启动...");
            Socket socket;
            while((socket = serverSocket.accept()) != null) {
                System.out.println("客户端连接！Ip为：" + socket.getInetAddress());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
//                threadPool.execute(new WorkerThread(socket, service));
            }
        } catch (IOException e) {
            System.out.println("连接时有错误发生：" + e);
        }
    }

}
