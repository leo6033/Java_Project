/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PanServer
 * Author:   ITryagain
 * Date:     2018/12/5 21:05
 * Description: 服务器socket创建
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈服务器socket创建〉
 *
 * @author ITryagain
 * @create 2018/12/5
 * @since 1.0.0
 */

public class PanServer extends Thread {
    private ServerSocket ss;//服务器对象
    private int port;//端口
    private boolean running=false;//服务器是否在运行中

    PanServer(int port){
        this.port=port;
    }

    public void run(){
        setupServer();
    }

    //在指定端口上启动服务器
    private void setupServer(){
        try{
            ss=new ServerSocket(this.port);
            running=true;
            System.out.println("服务器创建成功："+this.port);
            while(running){
                Socket client = ss.accept();
                System.out.println("进入了一个客户机对象："+client.getRemoteSocketAddress());
                ServerThread ct = new ServerThread(client);
                ct.start();
            }
        }catch(IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 查询服务器是否在运行
     *
     * @return: true为运行中
     */
    public boolean isRunning() {
        return this.running;
    }

    // 关闭服务器
    public void stopPanServer() {
        this.running = false;
        try {
            ss.close();
        } catch (Exception e) {}
    }
}
