/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.awt.Component;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author ACER
 */
public class treeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

    public treeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,row, hasFocus);
        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();
            TreePath treePath = tree.getPathForRow(row);
            File file = new File(path(treePath));
            Icon icon = fileSystemView.getSystemIcon(file);
            setIcon(icon);
        }
        return this;
    }
    private String path(TreePath treePath){
        String tempPath = "";
        if (treePath != null) {
            tempPath = treePath.toString().replace("]", "");
            tempPath = tempPath.replace("[", "").replaceAll(",", "/");
            tempPath = tempPath.replaceAll("/ ", "/");
        }
        return tempPath;
    }
}
