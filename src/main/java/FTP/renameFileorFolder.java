/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import java.io.File;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author ACER
 */
public class renameFileorFolder {
    public boolean renaming(FTPClient ftpClient, String oldFile, String newFileName) {
        boolean success = false;
        System.err.println(oldFile);
        String newFilePath = getParentFolder(oldFile);
        System.out.println(newFilePath);
        try {
            File file = new File(oldFile);
            if (file.isDirectory()) {
                success = ftpClient.rename(oldFile, newFilePath + "/" + newFileName);
                if (success) {
                    System.out.println(oldFile + " was successfully renamed to: "
                            + newFilePath + "/" + newFileName);
                } else {
                    System.out.println("Failed to rename folder: " + oldFile);
                }
            } else {
                success = ftpClient.rename(oldFile, newFilePath + "/" + newFileName);
                if (success) {
                    System.out.println(oldFile + " was successfully renamed to: "
                            + newFilePath + "/" + newFileName);
                } else {
                    System.out.println("Failed to rename file: " + oldFile);
                }
            }
            // renaming file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    private String getParentFolder(String filePath) {
        return filePath.substring(0,filePath.lastIndexOf("\\"));
    }
}
