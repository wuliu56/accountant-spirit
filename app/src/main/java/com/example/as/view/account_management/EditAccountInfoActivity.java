package com.example.as.view.account_management;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.AccountManager;

public class EditAccountInfoActivity extends Activity {
    private AccountManager am = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_info);

        AsApplication asApplication = (AsApplication) getApplication();
        String accountId = asApplication.getAccountId();
        am = new AccountManager(accountId);//初始化AccountManager对象

        EditText editText_edit_nickname = (EditText) findViewById(R.id.editTex_edit_nickname);//获取昵称编辑框
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup_edit_sex);//获取设置性别的单选按钮组

        String nickname ;

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                String in_sex = rb.getText().toString();

            }
        });
    }
}
