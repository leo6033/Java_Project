/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UpLoadThread
 * Author:   ITryagain
 * Date:     2018/12/8 14:22
 * Description: 服务器接受文件线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanServer;

import java.io.*;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈服务器接受文件线程〉
 *
 * @author ITryagain
 * @create 2018/12/8
 * @since 1.0.0
 */

public class UpLoadThread extends Thread{
    private ServerSocket UpLoadServer;
    private int port;
    private String input;
    private FileOutputStream fos;

    UpLoadThread(int port,String input){
        this.port=port;
        this.input=input;
    }

    public void run(){
        DataInputStream dis =null;
        Socket socket =null;
        try{
            UpLoadServer = new ServerSocket(port);
            socket = UpLoadServer.accept();
            dis = new DataInputStream(socket.getInputStream());
            //文件名和长度
            String fileName = input.substring(input.indexOf("#")+1);
            long fileLength = dis.readLong();
            File file = new File(fileName);
            fos = new FileOutputStream(file);

            //开始接收文件
            byte[] bytes = new byte[1024];
            int length=0;
            while((length = dis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
            }
            System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");

            try {
                if(fos!=null)
                    fos.close();
                if(dis != null)
                    dis.close();
                if(socket !=null)
                    socket.close();
                if(UpLoadServer!=null)
                    UpLoadServer.close();

            } catch (Exception e) {}

        }catch(IOException e){

        }
    }


    private static DecimalFormat df = null;

    static {
        // 设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }

    /**
     * 格式化文件大小
     * @param length
     * @return
     */
    private String getFormatFileSize(long length) {
        double size = ((double) length) / (1 << 30);
        if(size >= 1) {
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1 << 20);
        if(size >= 1) {
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1 << 10);
        if(size >= 1) {
            return df.format(size) + "KB";
        }
        return length + "B";
    }
}
