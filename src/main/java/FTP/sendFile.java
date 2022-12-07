/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import java.io.*;
import org.apache.commons.net.ftp.*;

/**
 *
 * @author ACER
 */
public class sendFile {
    public void deterSend(FTPClient ftpClient, String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException {
        File localDir = new File(localParentDir);
        if(localParentDir.lastIndexOf(".") == -1){
            ftpClient.makeDirectory(localDir.getName());
            uploadDirectory(ftpClient, remoteDirPath, localParentDir, "");
        }else uploadSingleFile(ftpClient, localParentDir, localDir.getName());
    }
    
    public void uploadDirectory(FTPClient ftpClient, String remoteDirPath, String localParentDir, String remoteParentDir) throws IOException {
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setAutodetectUTF8(true);
        ftpClient.setControlEncoding("UTF-8");
        System.out.println("remoteParentDir: "+remoteParentDir);
        System.out.println("LISTING directory: " + localParentDir);
        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "\\" + remoteParentDir + "\\" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "\\" + item.getName();
                }
                System.out.println(remoteFilePath);
                if (item.isFile()) {
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile(ftpClient,
                            localFilePath, remoteFilePath);
                    if (uploaded) {
                        System.out.println("UPLOADED a file to: "
                                + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT upload the file: "
                                + localFilePath);
                    }
                } else {
                    // create directory on the server
                    ftpClient.setAutodetectUTF8(true);
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("CREATED the directory: " + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT create the directory: " + remoteFilePath);
                    }

                    // upload the sub directory
                    String parent = remoteParentDir + "\\" + item.getName();
                    if (remoteParentDir.equals("")) {
                        parent = item.getName();
                    }

                    localParentDir = item.getAbsolutePath();
                    uploadDirectory(ftpClient, remoteDirPath, localParentDir, parent);
                }
            }
        }
    }

    public boolean uploadSingleFile(FTPClient ftpClient, String localFilePath, String remoteFilePath) throws IOException {
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.setAutodetectUTF8(true);
        ftpClient.setControlEncoding("UTF-8");
        File localFile = new File(localFilePath);
        InputStream inputStream = new FileInputStream(localFile);
        try {
            ftpClient.setAutodetectUTF8(true);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remoteFilePath, inputStream);
        } finally {
            inputStream.close();
        }
    }

}
