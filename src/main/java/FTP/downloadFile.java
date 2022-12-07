/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import FTP.*;
import java.io.*;
import java.util.List;
import org.apache.commons.net.ftp.*;

/**
 *
 * @author ACER
 */
public class downloadFile {
    private static final int BUFFER_SIZE = 1024 * 1024;
    public static boolean downloadSingleFile(FTPClient ftpClient, String remoteFilePath, String savePath) throws IOException {
        File downloadFile = new File(savePath);

        File parentDir = downloadFile.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdir();
        }

        OutputStream outputStream = new BufferedOutputStream(
                new FileOutputStream(downloadFile));
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.retrieveFile(remoteFilePath, outputStream);
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public void deter(String parentDir, String currentDir, String saveDir, FTPClient ftpClient) throws IOException {
        parentDir = "\\" + parentDir.substring(parentDir.lastIndexOf("\\") + 1);
        if(parentDir.lastIndexOf(".") == -1){
            downLoadFileFromServer(parentDir, currentDir, saveDir, ftpClient);
        }else downloadPrefixFile(parentDir, saveDir, ftpClient);
    }

    public String findDownloadFilePath(String fileName, List<String> l) {
        String finalPath = null;
        String[] result;
        for (String a : l) {
            result = a.split("/");
            if (result[result.length - 1].equals(fileName)) {
                finalPath = a;
            }
        }
//        System.out.println(finalPath);
        return finalPath;
    }

    public void downLoadFileFromServer(String parentDir, String currentDir, String saveDir, FTPClient ftpClient) throws IOException {
        System.out.println("File " + currentDir + " is downloading...");
        
        
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "\\" + currentDir;
        }
        FTPFile[] subFiles = ftpClient.listFiles(dirToList);

        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {

                String currentFileName = aFile.getName();
//                System.out.println("cur "+ currentFileName);
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                String filePath = parentDir + "\\" + currentDir + "\\" + currentFileName;
                if (currentDir.equals("")) {
                    filePath = parentDir + "\\" + currentFileName;
                }

                String newDirPath = saveDir + parentDir + "\\" + currentDir + "\\" + currentFileName;
                if (currentDir.equals("")) {
                    newDirPath = saveDir + parentDir + "\\" + currentFileName;
                }

                if (aFile.isDirectory()) {
                    // create the directory in saveDir
                    File newDir = new File(newDirPath);
                    boolean created = newDir.mkdirs();
                    if (created) {
                        System.out.println("CREATED the directory: " + newDirPath);
                    } else {
                        System.out.println("COULD NOT create the directory: " + newDirPath);
                    }
                    // download the sub directory
                    downLoadFileFromServer(dirToList, currentFileName, saveDir, ftpClient);
                } else {
                    // download the file
                    boolean success = downloadSingleFile(ftpClient, filePath, newDirPath);
                    if (success) {
                        System.out.println("DOWNLOADED the file: " + filePath);
                    } else {
                        System.out.println("COULD NOT download the file: " + filePath);
                    }
                }
            }
        }
    }

    public void downloadPrefixFile(String ftpFilePath, String downloadFilePath, FTPClient ftpClient) {
        System.out.println("File " + ftpFilePath + " is downloading...");
        OutputStream outputStream = null;
        boolean success = false;
        File temp = new File(ftpFilePath);
//        System.err.println("ftpFilePath: "+ftpFilePath+"downloadFilePath: "+downloadFilePath+"Temp: "+downloadFile);
        try {
            File downloadFile = new File(downloadFilePath +"\\"+temp.getName());
            outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            // download file from FTP Server
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(BUFFER_SIZE);
            success = ftpClient.retrieveFile(ftpFilePath, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (success) {
            System.out.println("File " + ftpFilePath + " has been downloaded successfully.");
        }
    }
}