/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FileDownLoadThread
 * Author:   ITryagain
 * Date:     2018/12/8 14:37
 * Description: 下载文件的线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import java.io.*;
import java.math.RoundingMode;
import java.net.Socket;
import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈下载文件的线程〉
 *
 * @author ITryagain
 * @create 2018/12/8
 * @since 1.0.0
 */

public class FileDownLoadThread extends Thread {

    private Socket fileDownLoadSocket;
    private int port;
    private String fileName;
    private FileOutputStream fos;
    private DataInputStream dis;

    FileDownLoadThread(int port, String fileName){
        this.port=port;
        this.fileName=fileName;
        System.out.println("创建下载线程成功");
    }
    public void run(){
        try{
            fileDownLoadSocket = new Socket("localhost",this.port);
            //文件名和长度

            dis = new DataInputStream(fileDownLoadSocket.getInputStream());
            long fileLength = dis.readLong();

            File file = new File(fileName);
            System.out.println("FileDownLoadThread:"+fileName+" "+fileLength);
            fos = new FileOutputStream(file);

            //开始接收文件
            System.out.println("======开始接收文件=======");
            byte[] bytes = new byte[1024];
            int length;
            long progress = 0;
            while((length = dis.read(bytes, 0, bytes.length)) != -1) {
                fos.write(bytes, 0, length);
                fos.flush();
            }
            System.out.println();
            System.out.println("======== 文件接收成功 [File Name：" + fileName + "] [Size：" + getFormatFileSize(fileLength) + "] ========");

        }catch(IOException e1){

        }finally {
            try {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
            }catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
