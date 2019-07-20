/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: DownLoadThread
 * Author:   ITryagain
 * Date:     2018/12/8 14:38
 * Description: 服务器下载文件线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈服务器下载文件线程〉
 *
 * @author ITryagain
 * @create 2018/12/8
 * @since 1.0.0
 */

public class DownLoadThread extends Thread {

    private int port;
    private ServerSocket downLoadServer;
    private String input;
    private DataOutputStream dos;
    private FileInputStream fis;

    DownLoadThread(int port,String input){
        this.port=port;
        this.input=input;
    }

    public void run(){
        Socket socket =null;
        try{
            downLoadServer = new ServerSocket(this.port);
            socket = downLoadServer.accept();
            String fileName = input.substring(input.indexOf("#")+1);
            System.out.println("fileName:"+fileName);
            File file = new File(fileName);
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeLong(file.length());

            //开始传输文件
            System.out.println("======开始传输文件=======");
            byte[] bytes = new byte[1024];
            int length;

            long progress = 0;
            fis = new FileInputStream(file);
            while((length=fis.read(bytes,0,bytes.length))!=-1){
                dos.write(bytes,0,length);
                dos.flush();
                progress = progress + length;
                System.out.print("| " + (100*progress/file.length()) + "% |");
            }
            System.out.println();
            System.out.println("======== 文件传输成功 ========");

        }catch (IOException e){

        }finally {
            try {
                if (fis != null)
                    fis.close();
                if (socket != null)
                    socket.close();
                if(downLoadServer!=null)
                    downLoadServer.close();
            }catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
