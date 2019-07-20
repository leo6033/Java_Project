/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MyTreeCellEditorListener
 * Author:   ITryagain
 * Date:     2018/12/4 22:06
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * 〈一句话功能简述〉<br> 
 * 〈编辑器监听类〉
 *
 * @author ITryagain
 * @create 2018/12/4
 * @since 1.0.0
 */

public class MyTreeCellEditorListener implements  CellEditorListener {

    private JTree tree;

    MyTreeCellEditorListener(JTree tree) {
        this.tree = tree;

    }


    public void editingStopped(ChangeEvent e) {

//        TreeSelectionModel selectionModel = tree.getSelectionModel();
//        TreePath treepath = selectionModel.getSelectionPath();
//        String path = treepath.toString();
//        path=path.replaceAll(", ","\\\\");
//        path=path.substring(1,path.length()-1);
//        System.out.println("nodePath:"+path);

        Object selectNode = tree.getLastSelectedPathComponent();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectNode;
        //获取选中对象
        CellEditor cellEditor = (CellEditor) e.getSource();

        //获取选中对象的值
        String newName = (String) cellEditor.getCellEditorValue();
        node.setUserObject(newName);

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.nodeStructureChanged(node);
    }

    public void editingCanceled(ChangeEvent e) {
        editingStopped(e);
    }

}
