package com.example.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private EditText mEtUserName;
    private EditText mEtPassWord;
    private Button mBtn_login;
    private CheckBox rem;
    private CheckBox auto;
    private ToggleButton toggleButton;
    private TextView forget;
    private SharedPreferences sp;//android中的一个轻量级的存储类
    private String userNameValue,passwordValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
        toggleButton.setOnCheckedChangeListener(new ToggleButtonClick());//密码可见性监听

        forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
        //获取sp对象实例
        sp = this.getSharedPreferences("userInfo", Context. MODE_PRIVATE );

        //判断记住密码多选框的状态
        if(sp.getBoolean("ISCHECK", false))
        {

            mEtUserName.setText(sp.getString("USER_NAME", ""));
            mEtPassWord.setText(sp.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if(sp.getBoolean("AUTO_ISCHECK", false))
            {
                //设置默认是自动登录状态
                auto.setChecked(true);
                //跳转界面
                Intent intent = new Intent(MainActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        }
        //监听记住密码多选框按钮事件
        rem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (rem.isChecked()) {

                    System.out.println("记住密码已选中");
                    sp.edit().putBoolean("ISCHECK", true).commit();

                }else {

                    System.out.println("记住密码没有选中");
                    sp.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        //监听自动登录多选框事件
        auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (auto.isChecked()) {
                    System.out.println("自动登录已选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
        initListener();
        //forget选项

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentforget = new Intent(MainActivity.this,PasswordForgetActivity.class);
                startActivity(intentforget);
            }
        });

    }

    public void findID(){
        mEtUserName = findViewById(R.id.et_1);
        mEtPassWord = findViewById(R.id.et_2);
        mBtn_login = findViewById(R.id.btn_login);
        toggleButton = findViewById(R.id.togglebutton);
        forget = findViewById(R.id.tv_forget);
        rem = findViewById(R.id.cb_1);
        auto = findViewById(R.id.cb_2);

    }

    public void initListener() {
        mBtn_login.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String UserName = mEtUserName.getText().toString();
                String Password = mEtPassWord.getText().toString();



                //判断用户名是否为空
                if (TextUtils.isEmpty(UserName)) {
                    mEtUserName.setError("用户名不能为空！");
                    return;
                }
                //判断密码是否为空
                if (TextUtils.isEmpty(Password)) {
                    mEtPassWord.setError("密码不能为空！");
                    return;
                }

                // 登录认证
                if (UserName.equals("123456") && Password.equals("123456")) {
                    Intent intent2 = new Intent(MainActivity.this, StudentActivity.class);
                    Toast.makeText(MainActivity.this, "登陆成功！", 1).show();
                    startActivity(intent2);
                    //登录成功和记住密码框为选中状态才保存用户信息
                    if(rem.isChecked())
                    {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD",passwordValue);
                        editor.commit();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误！", 1).show();
                }

            }
        });
    }

    //密码可见性监听事件
    private class ToggleButtonClick implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton,boolean ischecked) {
            //判断事件的选中状态
            if(ischecked){//显示密码
                mEtPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{//隐藏密码
                mEtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            //每次显示或关闭后  密码显示的编辑线不统一再最后，下面是为了同一
            mEtPassWord.setSelection(mEtPassWord.length());
        }

    }


}





