/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import java.io.IOException;
import org.apache.commons.net.ftp.*;

/**
 *
 * @author ACER
 */
public class deleteFileOrFolder {
    private int returnCode;
    int fileLength;
    
    public String deleteDeter(String dirPath,FTPClient ftpClient,boolean leafBoolean){
        boolean exist = false;
        try {
            exist = checkDirectoryExists(dirPath,ftpClient);
            if (exist & leafBoolean){
                deleteEmplyFolder(ftpClient,dirPath);
                System.err.println("deleteEmplyFolder");
            }else if (exist & !leafBoolean){
                removeDirectory(ftpClient,dirPath,"");
                System.err.println("removeDirectory");
            }else {
                deleteFile(ftpClient,dirPath);
                System.err.println("deleteFile");
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Delete Successful";
    }

    boolean checkDirectoryExists(String dirPath,FTPClient ftpClient) throws IOException {
        ftpClient.changeWorkingDirectory(dirPath);
        returnCode = ftpClient.getReplyCode();
        if (returnCode == 550) {
            return false;
        }
        return true;
    }
    public static void removeDirectory(FTPClient ftpClient, String parentDir,String currentDir) throws IOException {
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "\\" + currentDir;
        }
        System.err.println(dirToList);
        FTPFile[] subFiles = ftpClient.listFiles(dirToList);

        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                String filePath = parentDir + "\\" + currentDir + "\\"+ currentFileName;
                if (currentDir.equals("")) {
                    filePath = parentDir + "\\" + currentFileName;
                }
                if (aFile.isDirectory()) {
                    // remove the sub directory
                    removeDirectory(ftpClient, dirToList, currentFileName);
                } else {
                    // delete the file
                    ftpClient.deleteFile(filePath);
                }
            }
            // finally, remove the directory itself
            ftpClient.removeDirectory(dirToList);
        }else{
            deleteEmplyFolder(ftpClient,dirToList);
        }
    }
    private void deleteFile(FTPClient ftpClient,String fileToDelete){
        try {
            ftpClient.deleteFile(fileToDelete);
        } catch (IOException ex) {
            System.out.println("Oh no, there was an error: " + ex.getMessage());
        }
    }
    private static void deleteEmplyFolder(FTPClient ftpClient,String dirToRemove) {
        try {
            ftpClient.removeDirectory(dirToRemove);
        } catch (IOException e) {
            System.out.println("Oh no, there was an error: " + e.getMessage());
        }
    }
}
