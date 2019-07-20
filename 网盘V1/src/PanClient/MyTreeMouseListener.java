/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MyTreeMouseListener
 * Author:   ITryagain
 * Date:     2018/12/4 21:56
 * Description: 鼠标点击事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 〈一句话功能简述〉<br> 
 * 〈鼠标点击事件〉
 *
 * @author ITryagain
 * @create 2018/12/4
 * @since 1.0.0
 */

public class MyTreeMouseListener extends MouseAdapter  {

//    private String oldNodeName;
//
//    MyTreeMouseListener(String oldNodeName){
//        this.oldNodeName=oldNodeName;
//    }
//
//    public void mousePressed(MouseEvent e) {
//        try {
//            JTree tree = (JTree) e.getSource();
//            int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
//            TreePath treepath = tree.getPathForRow(rowLocation);
//            TreeNode treenode = (TreeNode) treepath.getLastPathComponent();
//            oldNodeName = treenode.toString();
//            String path = treepath.toString();
//            path=path.replaceAll(", ","\\\\");
//            path=path.substring(1,path.length()-1);
//            System.out.println("Listener"+" "+oldNodeName+" "+path+" "+treepath);
//        } catch (NullPointerException ne) {
//
//        }
//    }

}
