package com.example.as.view.account_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.as.R;
import com.example.as.AccountManager;
import com.example.as.view.MainActivity;

public class LoginActivity extends AppCompatActivity {
    private AccountManager am = new AccountManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);//设置布局为activity_login,登录界面

        final EditText editText_username = (EditText)findViewById(R.id.editText_username);
        final EditText editText_password = (EditText)findViewById(R.id.editText_password);//获取账户和密码的编辑框组件
        Button btn_log_in = (Button)findViewById(R.id.button_log_in);//获取登录按钮组件
        Button btn_switch_log_up = (Button)findViewById(R.id.button_switch_log_up);//获取新用户注册按钮

        final SharedPreferences sp = getSharedPreferences("Id_Password",MODE_PRIVATE);//获取SharedPreference，打开存储最近一次登录的账户密码的xml
        String username = sp.getString("username",null);//通过Key获取账户名称，默认值为null
        String password = sp.getString("password",null);//通过Key获取账户密码，默认值为null

        //实现自动/手动登录
        if(username!=null&&password!=null) {
            //自动登录
            am.logIn(username,password);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            //手动登录
            btn_log_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String in_username = editText_username.getText().toString();//获取填入的用户名
                    String in_password = editText_password.getText().toString();//获取填入的密码
                    //未填写完整
                    if(in_password.equals(null)||in_username.equals(null)){
                        Toast.makeText(LoginActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    }
                    //登录成功时更新SharedPreferences中最近登录账户的信息，并转入主界面
                    else if(am.logIn(in_username,in_password)){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.putString("username",in_username);
                        editor.putString("password",in_password);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    //登录失败时提示，清空密码编辑框
                    else{
                        Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                        editText_password.setText(null);
                    }
                }
            });
        }

        //实现点击“新用户注册”按钮，到注册界面的切换
        btn_switch_log_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LogupActivity.class);
                startActivity(intent);
            }
        });
    }
}