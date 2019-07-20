/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FileChooser
 * Author:   ITryagain
 * Date:     2018/12/4 23:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ITryagain
 * @create 2018/12/4
 * @since 1.0.0
 */

public class FileChooser extends JFrame implements ActionListener {
    JButton open=null;
    public static void main(String[] args) {
        new FileChooser();
    }
    public FileChooser(){
        open=new JButton("open");
        this.add(open);
        this.setBounds(400, 200, 100, 100);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
        }
        System.out.println(jfc.getSelectedFile().getName());
    }
}
