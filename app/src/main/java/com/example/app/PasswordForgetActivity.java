package com.example.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class PasswordForgetActivity extends AppCompatActivity {

    private Button Enter;

    private EditText et_Phone;
    private EditText et_Recive;
    //本地IP
    public  static final String  IP="10.0.2.2";
    //与服务端连接的端口
    public  static final int  PORT= 51706;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget);
        Enter = findViewById(R.id.enter);
        et_Phone = findViewById(R.id.et_phone);
        et_Recive = findViewById(R.id.et_recive);
        Enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setTitle("android -- server");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Socket socket = null;
                        String toserver;
                        try {
                            InetAddress serverAddr = InetAddress.getByName(IP);
                            socket = new Socket(serverAddr, PORT);
                            // 将信息通过这个对象来发送给Server
                            PrintWriter out = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(socket.getOutputStream())),
                                    true);
                            //接受服务器信息
                            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(socket.getInputStream()));
                            //得到服务器信息
                           String msg = in.readLine();
                           //在页面上进行显示
                            et_Recive.setText(msg);
                            toserver = et_Phone.getText().toString();
                            out.write(toserver);

                            out.flush();

                        } catch (Exception e) {

                            Log.e("TCP", "服务器: 出错啦", e);
                        } finally {
                            try {
                                socket.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });
    }
}

