package com.example.as.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.as.R;
import com.example.as.service.AccountManager;

public class LoginActivity extends AppCompatActivity {
    private AccountManager am = new AccountManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//设置布局为activity_login,登录界面
        final EditText editText_username = (EditText)findViewById(R.id.editText_username);
        final EditText editText_password = (EditText)findViewById(R.id.editText_password);//获取账户和密码的编辑框组件
        Button btn_log_in = (Button)findViewById(R.id.button_log_in);//获取登录按钮组件

        final SharedPreferences sp = getSharedPreferences("Id_Password",MODE_PRIVATE);//获取SharedPreference，打开存储最近一次登录的账户密码的xml

        String username = sp.getString("username",null);//通过Key获取账户名称，默认值为null
        String password = sp.getString("password",null);//通过Key获取账户密码，默认值为null
        if(username!=null&&password!=null)
        {
            am.logIn();

        }

        btn_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}