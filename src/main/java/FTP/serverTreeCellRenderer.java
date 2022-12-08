/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import org.openide.util.Exceptions;

/**
 *
 * @author ACER
 */
public class serverTreeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

    public serverTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        try {
            super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,row, hasFocus);
            String tmp = System.getProperty("java.io.tmpdir"); 
            File file = new File(tmp + value.toString());
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            if (node.getChildCount() >= 1) {
                return this;
            }else{
                file.createNewFile();
            }
            Icon ico = FileSystemView.getFileSystemView().getSystemIcon(file);
            file.delete();
            setIcon(ico);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return this;
    }
    
}
