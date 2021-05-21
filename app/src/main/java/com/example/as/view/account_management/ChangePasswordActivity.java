package com.example.as.view.account_management;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.AccountManager;

public class ChangePasswordActivity extends Activity {
    AccountManager am = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button btn_confirm = (Button) findViewById(R.id.button_confirm);//获取确定按钮
        Button btn_cancel = (Button) findViewById(R.id.button_cancel);//获取取消按钮

        EditText editText_old_password = (EditText) findViewById(R.id.editText_old_password);//获取旧密码编辑框
        EditText editText_new_password = (EditText) findViewById(R.id.editText_new_password);//获取新密码编辑框
        EditText editText_confirm_password = (EditText) findViewById(R.id.editText_confirm_password);//获取确认新密码编辑框

        AsApplication asApplication = (AsApplication) getApplication();
        String accountId = asApplication.getAccountId();
        am = new AccountManager(accountId);//获取对应账户的AccountManager
        //确定按钮的点击
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = editText_old_password.getText().toString();//获取旧密码
                String new_password = editText_new_password.getText().toString();//获取新密码
                String confirm_password = editText_confirm_password.getText().toString();//获取确认新密码
                //两次新密码不一致，提示并清空两个新密码编辑框
                if(!new_password.equals(confirm_password)){
                    Toast.makeText(ChangePasswordActivity.this,"两次输入的新密码不一致",Toast.LENGTH_SHORT).show();
                    editText_new_password.setText(null);
                    editText_confirm_password.setText(null);
                }
                //更换密码，更新SharedPreference文件
                else{
                    am.changePassword(new_password);
                    SharedPreferences sp = getSharedPreferences("Id_Password",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.remove("password");
                    editor.putString("password",new_password);
                    Toast.makeText(ChangePasswordActivity.this, "更换成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        //取消按钮的点击
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
