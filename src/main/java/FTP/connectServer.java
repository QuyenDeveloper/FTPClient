package FTP;


import java.io.IOException;
import java.util.*;
import java.util.List;
import org.apache.commons.net.ftp.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */
public class connectServer {
    FTPClient ftpClient;
    List<String> pathList = new ArrayList<String>();

    
    //lấy tất cả các đường dẫn file hiện có trên server
    public List<FTPFile> getListFileFromFTPServer(String path) {
        List<FTPFile> listFiles = new ArrayList<FTPFile>();
        try {
            FTPFile[] ftpFiles = ftpClient.listFiles(path);
            if (ftpFiles.length > 0) {
                for (FTPFile ftpFile : ftpFiles) {
                    // add file to listFiles
                    pathList.add(path + "/" + ftpFile.getName());
                    listFiles.add(ftpFile);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return listFiles;
    }

    public List<String> getRealPath() {
        return pathList;
    }
    
    //kết nối server
    public FTPClient connectFTPServer(String server, String user, String pass) {
        ftpClient = new FTPClient();
        int port = 21;

        try {
            ftpClient.setAutodetectUTF8(true);
            ftpClient.connect(server, port);
            ftpClient.setControlKeepAliveTimeout(300);
            int replyCode = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Operation failed. Server reply code: " + replyCode);
                return null;
            }
            boolean success = ftpClient.login(user, pass);
            if (!success) {
                System.out.println("Could not login to the server" + ftpClient.getReplyCode() + Arrays.toString(ftpClient.getReplyStrings()));
            } else {
                System.out.println("LOGGED IN SERVER");
            }
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        }

        return ftpClient;
    }

    //ngắt kết nối
    public void disconnectFTPServer() {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
