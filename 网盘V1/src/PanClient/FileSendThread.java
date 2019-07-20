/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FileSendThread
 * Author:   ITryagain
 * Date:     2018/12/8 14:11
 * Description: 文件上传线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈文件上传线程〉
 *
 * @author ITryagain
 * @create 2018/12/8
 * @since 1.0.0
 */

public class FileSendThread extends Thread {

    private Socket fileSendSocket;
    private int port;
    private String path;
    private File file;
    private OutputStream ous;
    private FileInputStream fis;
    private DataOutputStream dos;
    private JLabel statusLabel;

    FileSendThread(int port, File file,String path,JLabel statusLabel){
        this.port=port;
        this.file=file;
        this.path=path;
        this.statusLabel=statusLabel;
    }
    public void run(){
        try{
            fileSendSocket = new Socket("localhost",this.port);
            // 发送： 文件名称 文件长度
            this.ous = fileSendSocket.getOutputStream();
            dos = new DataOutputStream(this.ous);
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
                statusLabel.setText("文件已传输：| " + (100*progress/file.length()) + "% |");
            }
            System.out.println();
            System.out.println("======== 文件传输成功 ========");
            statusLabel.setText("======== 文件传输成功 ========");
        }catch(IOException e1){

        }finally {
            try {
                if (fis != null)
                    fis.close();
                if (dos != null)
                    dos.close();
            }catch(IOException e){
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
