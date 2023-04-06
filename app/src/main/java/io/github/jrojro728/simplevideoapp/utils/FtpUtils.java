package io.github.jrojro728.simplevideoapp.utils;

import static io.github.jrojro728.simplevideoapp.utils.Utils.getFileAbsolutePath;

import android.content.Context;
import android.net.Uri;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FtpUtils {
    //Ftp客户端参数
    private String Address;
    private String Username;
    private String Password;

    private FTPClient Client;
    private FTPClientConfig Config;

    public FtpUtils() {
        Address = "192.168.1.108";
        Username = "ftphome";
        Password = "1a2b3c";
    }

    public FtpUtils(String address) {
        Address = address;
        Username = "ftphome";
        Password = "1a2b3c";
    }

    public FtpUtils(String address, String username, String password){
        Address = address;
        Username = username;
        Password = password;
    }

    public boolean Connect() {
        Config = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        Client = new FTPClient();
        Client.configure(Config);

        int reply = 0;
        boolean error = false;
        try {
            Client.connect(Address);
            Client.setFileType(FTP.BINARY_FILE_TYPE);
            reply = Client.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                Client.disconnect();
                throw new ConnectException("FTP服务器拒绝了连接请求");
            }
            Client.login(Username, Password);
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        }

        return error;
    }

    public boolean Disconnect() {
        boolean error = false;
        if(Client.isConnected()) {
            try {
                Client.logout();
                Client.disconnect();
            } catch (IOException e) {
                error = true;
                e.printStackTrace();
            }
        }

        return error;
    }

    public FTPFile[] ListDirFile(String dir) throws IOException { return Client.listFiles(dir); }

    public boolean UploadFile(Uri uri, Context context) {
        boolean error = false;
        try {
            String FilePath = getFileAbsolutePath(context, uri);
            InputStream inputFileStream = Files.newInputStream(Paths.get(FilePath));
            int FileNameCharNumber = FilePath.lastIndexOf("/");
            String FileName = FilePath;
            StringBuffer tempStringBuffer = new StringBuffer(FileName);

            tempStringBuffer.delete(0, FileNameCharNumber + 1);
            FileName = tempStringBuffer.toString();

            Client.storeFile(FileName, inputFileStream);
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        }

        return error;
    }
}
