package com.example.as.view.account_management;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.entity.AccountInfo;
import com.example.as.service.AccountManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

public class EditAccountInfoActivity extends Activity implements View.OnClickListener {
    private AccountManager am = null;
    private AccountInfo ai = null;
    private ImageView image_choose;
    private ImageView icon;
    private String icon_path = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_account_info);
        image_choose = (ImageView) findViewById(R.id.imageButton_upload_icon);
        image_choose.setOnClickListener(this);

        AsApplication asApplication = (AsApplication) getApplication();
        String accountId = asApplication.getAccountId();
        am = new AccountManager(accountId);//初始化AccountManager对象

        EditText editText_edit_nickname = (EditText) findViewById(R.id.editTex_edit_nickname);//获取昵称编辑框
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup_edit_sex);//获取设置性别的单选按钮组
        Spinner spinner_year = (Spinner)findViewById(R.id.spinner_year); //获取年/月/日下拉框组件
        Spinner spinner_month = (Spinner)findViewById(R.id.spinner_month);
        Spinner spinner_day = (Spinner)findViewById(R.id.spinner_day);
        Button btn_confirm = (Button) findViewById(R.id.button_confirm_edit_account_info);//获取提交按钮
        Button btn_cancel = (Button) findViewById(R.id.button_cancel_edit_account_info);//获取取消按钮
        icon = (ImageView)findViewById(R.id.imageView_icon_added);

        Calendar calendar = Calendar.getInstance();//用于正确显示下拉框内容的Calendar
        ArrayList<String> yearItems = new ArrayList<String>();//年下拉框的内容
        String[] monthItems = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};//月下拉框的内容
        ArrayList<String> dayItems = new ArrayList<String>();//日下拉框的内容

        //设置填写内容的默认值
        rg.check(R.id.radioButton_male);

        //设置年下拉框的适配器
        for(int year = 1900;year<=calendar.get(Calendar.YEAR);year++){
            yearItems.add(year+"");
        }
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                yearItems);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);
        spinner_year.setSelection(yearItems.size()-1);

        //设置月下拉框的适配器
        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                monthItems);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter_month);

        //设置日下拉框的适配器
        calendar.set(Calendar.YEAR,Integer.parseInt(spinner_year.getSelectedItem().toString()));
        calendar.set(Calendar.MONTH,Integer.parseInt(spinner_month.getSelectedItem().toString())-1);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int day = 1;day <= maxDay;day++){
            dayItems.add(day+"");
        }
        ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                dayItems);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(adapter_day);

        //初始化各组件和icon_uri
        if((ai = am.getAccountInfo())!=null){
            editText_edit_nickname.setText(ai.getName());

            String sex_has = ai.getSex();
            if(sex_has.equals(((RadioButton)findViewById(R.id.radioButton_male)).getText().toString())){
                rg.check(R.id.radioButton_male);
            }
            else
                rg.check(R.id.radioButton_female);

            Date date = ai.getBirthday();
            spinner_year.setSelection(date.getYear());
            spinner_month.setSelection(date.getMonth());
            spinner_day.setSelection(date.getDate()-1);

            //配置ImageLoader
            icon_path = ai.getIcon();
            if(icon_path.length()!=0) {
                Uri uri = Uri.parse(icon_path);
                icon.setImageURI(uri);
            }
        }

        //设置单选按钮组的选择监听器
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton)findViewById(checkedId);
                String in_sex = rb.getText().toString();
            }
        });

        AdapterView.OnItemSelectedListener l = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int year = Integer.parseInt(spinner_year.getSelectedItem().toString());
                int month = Integer.parseInt(spinner_month.getSelectedItem().toString())-1;
                calendar.clear();
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                dayItems.clear();
                for(int day = 1;day <= maxDay;day++){
                    dayItems.add(day+"");
                }
                ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item,dayItems);
                adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_day.setAdapter(adapter_day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        //设置年下拉框的切换监听器
        spinner_year.setOnItemSelectedListener(l);
        //设置月下拉框的切换监听器
        spinner_month.setOnItemSelectedListener(l);

        //设置提交按钮的监听器
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());

                String nickname = editText_edit_nickname.getText().toString();//输入的昵称
                String sex = rb.getText().toString();//选择的性别
                Date date;//选择的生日

                int year = Integer.parseInt(spinner_year.getSelectedItem().toString());
                int month = Integer.parseInt(spinner_month.getSelectedItem().toString());
                int day = Integer.parseInt(spinner_day.getSelectedItem().toString());
                date = new Date(year-1900,month-1,day);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.format(date);

                //判断是否填写完整
                if(nickname.length() == 0 || icon_path.length() == 0){
                    Toast.makeText(getApplicationContext(), "请填写完整", Toast.LENGTH_SHORT).show();
                }
                else {
                    AccountInfo accountInfo = new AccountInfo(nickname, sex, date, icon_path, accountId);
                    am.setAccountInfo(accountInfo);
                    finish();
                }
            }
        });

        //设置取消按钮的监听器
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imageButton_upload_icon: {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
                break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                icon_path = uri.toString();
                icon.setImageURI(uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
