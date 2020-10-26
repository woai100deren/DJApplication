package com.dj.sk;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.webkit.WebView;
import android.widget.EditText;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketActivity extends BaseActivity {
    @BindView(R.id.clientIp)
    EditText clientIp;
    @BindView(R.id.messageContent)
    EditText messageContent;

    private Socket socket;
    private Thread clientThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.serverBtn)
    protected void server(){
        SocketManager.startServer();
    }

    @OnClick(R.id.clientBtn)
    protected void clent(){
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(clientIp.getText().toString(), 8887);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        clientThread.start();
    }

    @OnClick(R.id.sendBtn)
    protected void send(){
        final String data = messageContent.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
                    outputStream.write((socket.getLocalPort() + "//" + data + "//" + df.format(new Date())).getBytes("utf-8"));
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SocketManager.shutDown();
        try{
            if(socket!=null){
                socket.close();
            }
            clientThread.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
