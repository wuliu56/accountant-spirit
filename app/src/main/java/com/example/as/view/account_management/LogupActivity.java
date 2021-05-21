package com.example.as.view.account_management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.AccountManager;

public class LogupActivity extends Activity {
    private AccountManager am = new AccountManager();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up);

        EditText editText_create_username = (EditText) findViewById(R.id.editText_create_username);//获取账户名编辑框
        EditText editText_create_password = (EditText) findViewById(R.id.editText_create_password);//获取密码编辑框
        EditText editText_create_confirm = (EditText) findViewById(R.id.editText_create_confirm);//获取确认的密码编辑框

        Button btn_log_up = (Button)findViewById(R.id.button_log_up);//获取注册按钮
        Button btn_switch_log_in = (Button) findViewById(R.id.button_switch_log_in);//获取切换到登录界面按钮

        btn_log_up.setOnClickListener(new View.OnClickListener() {
            //设置点击“注册”按钮，实现注册
            @Override
            public void onClick(View v) {
                String username = editText_create_username.getText().toString();//获取账户名
                String password = editText_create_password.getText().toString();//获取密码
                String confirm_password = editText_create_confirm.getText().toString();//获取确认的密码

                //未填写完整
                if(username.equals(null)||password.equals(null)||confirm_password.equals(null))
                {
                    Toast.makeText(LogupActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                }
                //两次密码不一致，提示并清空两个密码编辑框
                else if(!password.equals(confirm_password)){
                    Toast.makeText(LogupActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    editText_create_password.setText("");
                    editText_create_confirm.setText("");
                }
                else{
                    //注册失败时，提示账户名已被注册
                    if(!am.logUp(username,password)){
                        Toast.makeText(LogupActivity.this,"账户名被已占用",Toast.LENGTH_SHORT).show();
                        editText_create_username.setText(null);
                        editText_create_password.setText(null);
                        editText_create_confirm.setText(null);
                    }
                    //注册成功，提示
                    else{
                        Toast.makeText(LogupActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        btn_switch_log_in.setOnClickListener(new View.OnClickListener() {
            //实现切换到登录界面
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
