package com.example.as.view.bill_record;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;
import com.example.as.entity.WalletList;
import com.example.as.service.AccountManager;
import com.example.as.view.account_management.AsApplication;

import java.util.ArrayList;
import java.util.Calendar;

public class SetBillActivity extends Activity {
    private EditText et_bill_amount = null;
    private Spinner spinner_wallet_name = null;
    private Spinner spinner_type_category = null;
    private Spinner spinner_type_name = null;
    private AccountManager am = new AccountManager(AsApplication.getAccountId());
    private ArrayList<String> walletNameList = new ArrayList<String>();//存放钱包名字数组
    ArrayList<Type> typeArrayList = new ArrayList<Type>();             //存放类型数组
    private ArrayList<String> typeCategoryList = new ArrayList<String>();//存放大类数组
    private ArrayList<String> typeNameList = new ArrayList<String>();//存放一个类数组
    private ArrayList<String[]> typeList = new ArrayList<String[]>();//存放二维数组，每一行是一个大类下的类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_bill);
        Intent intent = getIntent();
        Bundle bundle_input = intent.getExtras();

        //初始化组件
        et_bill_amount = (EditText) findViewById(R.id.editText_bill_amount);
        spinner_wallet_name = (Spinner) findViewById(R.id.spinner_wallet_name);
        spinner_type_category = (Spinner) findViewById(R.id.spinner_type_category);
        spinner_type_name = (Spinner) findViewById(R.id.spinner_type_name);

        //获取下拉框数据
        Context context = AsApplication.getContext();
        typeList.add(context.getResources().getStringArray(R.array.category1));
        typeList.add(context.getResources().getStringArray(R.array.category2));
        typeList.add(context.getResources().getStringArray(R.array.category3));
        typeList.add(context.getResources().getStringArray(R.array.category4));
        typeList.add(context.getResources().getStringArray(R.array.category5));
        typeList.add(context.getResources().getStringArray(R.array.category6));
        typeList.add(context.getResources().getStringArray(R.array.category7));
        typeList.add(context.getResources().getStringArray(R.array.category8));
        typeList.add(context.getResources().getStringArray(R.array.category9));
        typeList.add(context.getResources().getStringArray(R.array.category10));




        for(int i = 0; i < typeList.size(); i++){
            typeCategoryList.add(typeList.get(i)[0]);//初始化大类数组
        }

        //给钱包名字下拉框设置适配器
        WalletList walletList = am.getWalletList();
        for(int i = 0; i < walletList.getSize(); i++) {
            walletNameList.add(walletList.getWalletByIndex(i).getName());
        }
        ArrayAdapter<String> adapter_wallet_name = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, walletNameList);
        adapter_wallet_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_wallet_name.setAdapter(adapter_wallet_name);


        //给大类下拉框设置适配器
        ArrayAdapter<String> adapter_category = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typeCategoryList);
        adapter_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_category.setAdapter(adapter_category);
        spinner_type_category.setSelection(0);



        //给类型名字下拉框设置适配器
        ArrayAdapter<String> adapter_name = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typeNameList);
        adapter_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_name.setAdapter(adapter_name);


        //设置大类下拉框的切换选项监听器
        spinner_type_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                typeNameList.clear();//清空之前一个类的名字数组
                typeArrayList = am.queryTypeListByCategory(typeCategoryList.get(position));
                for(int i = 0; i<typeArrayList.size(); i++)
                    typeNameList.add(typeArrayList.get(i).getName());//初始化一个类的名字数组

                ArrayAdapter<String> adapter_name = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,typeNameList);
                adapter_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_type_name.setAdapter(adapter_name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //初始化传入的数据
        if(bundle_input != null) {
            String walletName = bundle_input.getString("walletName");
            String typeCategory = bundle_input.getString("typeCategory");
            String typeName = bundle_input.getString("typeName");
            double amount = bundle_input.getDouble("amount");
            et_bill_amount.setText(String.valueOf(amount));

            for(int i=0;i<walletList.getSize();i++) {
                if (walletList.getWalletByIndex(i).getName().equals(walletName)){
                    spinner_wallet_name.setSelection(i);
                    break;
                }
            }

            for(int i=0;i<typeCategoryList.size();i++) {
                if (typeCategoryList.get(i) == typeCategory) {
                    spinner_type_category.setSelection(i);
                    break;
                }
            }

            for(int i=0;i<typeNameList.size();i++) {
                if (typeNameList.get(i) == typeName) {
                    spinner_type_name.setSelection(i);
                    break;
                }
            }
        }

        Button btn_confirm = (Button) findViewById(R.id.button_confirm_set_bill);
        Button btn_cancel = (Button) findViewById(R.id.button_cancel_set_bill);

        //确认按钮的监听器
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_bill_amount.getText().toString().length() == 0)
                    Toast.makeText(AsApplication.getContext(), "请填写完整", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = getIntent();
                    Bundle bundle_output = new Bundle();

                    //包装输出的数据
                    double amount = Double.parseDouble(et_bill_amount.getText().toString());
                    String typeCategory = spinner_type_category.getSelectedItem().toString();
                    String typeName = spinner_type_name.getSelectedItem().toString();
                    String walletName = spinner_wallet_name.getSelectedItem().toString();

                    bundle_output.putDouble("amount", amount);
                    bundle_output.putString("typeCategory", typeCategory);
                    bundle_output.putString("typeName", typeName);
                    bundle_output.putString("walletName", walletName);

                    intent.putExtras(bundle_output);
                    setResult(0x11, intent);
                    finish();
                }
            }
        });

        //取消按钮的监听器
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
