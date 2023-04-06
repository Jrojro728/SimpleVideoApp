package io.github.jrojro728.simplevideoapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import io.github.jrojro728.simplevideoapp.databinding.ActivityMainBinding;
import io.github.jrojro728.simplevideoapp.utils.FtpUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int mTag;
    private FtpUtils ftpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_upload)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void onUploadButtonClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, mTag);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == mTag) {
                Uri uri = data.getData();
                if (uri != null) {
                    // 上传文件
                    ftpUtils = new FtpUtils();
                    Context context = this;
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            ftpUtils.Connect();
                            ftpUtils.UploadFile(uri, context);
                            ftpUtils.Disconnect();
                        }
                    }).start();
                }
                else {
                    Toast.makeText(this, R.string.error_filenotfind, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}