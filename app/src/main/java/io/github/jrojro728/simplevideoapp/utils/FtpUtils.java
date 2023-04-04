package io.github.jrojro728.simplevideoapp.utils;

import android.content.Context;
import android.net.Uri;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

public class FtpUtils {
    //Ftp客户端参数
    private String Address;
    private String Username;
    private String Password;

    private FTPClient Client;
    private FTPClientConfig Config;

    public FtpUtils() {
        Address = "127.0.0.1";
        Username = "ftphome";
        Password = "1a2b3c4d";
    }

    public FtpUtils(String address) {
        Address = address;
        Username = "ftphome";
        Password = "1a2b3c4d";
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
//        try {
////            InputStream inputStream = new FileInputStream(Utils.getFileFromContentUri(context, uri));
//        } catch (FileNotFoundException e) {
//            error = true;
//            e.printStackTrace();
//        }

        return error;
    }
}
