package com.example.as.view.query_statistics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.entity.Bill;
import com.example.as.entity.DailyBill;
import com.example.as.entity.Type;
import com.example.as.service.AccountManager;
import com.example.as.service.BillSearcher;
import com.example.as.view.account_management.AsApplication;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryByTypeActivity extends Activity {
    BillSearcher bs = new BillSearcher();
    AccountManager am = new AccountManager(AsApplication.getAccountId());

    ArrayList<String> typeNameOfCategoryList = new ArrayList<String>();
    ArrayList<Type> categoryTypeList = new ArrayList<Type>();
    ArrayList<String> categoryList = new ArrayList<String>();
    ArrayList<Bill> billArrayList = null;
    ArrayList<Map<String,Object>> billItemList = new ArrayList<Map<String,Object>>();

    SimpleAdapter adapter = null;

    int curPosition =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_by_type);

        Spinner spinner_query_category = (Spinner) findViewById(R.id.spinner_query_category);//初始化组件
        Spinner spinner_query_type_name = (Spinner) findViewById(R.id.spinner_query_type_name);

        //为下拉框准备数据
        categoryList.add("收入");
        categoryList.add("购物");
        categoryList.add("出行");
        categoryList.add("饮食");
        categoryList.add("住房");
        categoryList.add("教育");
        categoryList.add("娱乐");
        categoryList.add("医药");
        categoryList.add("投资");
        categoryList.add("其他");

        categoryTypeList = am.queryTypeListByCategory(categoryList.get(0));
        typeNameOfCategoryList.add("所有类型");
        for(int i=0;i<categoryTypeList.size();i++){
            typeNameOfCategoryList.add(categoryTypeList.get(i).getName());
        }

        //设置下拉框
        ArrayAdapter<String> category_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categoryList);
        category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_query_category.setAdapter(category_adapter);

        ArrayAdapter<String> type_name_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,typeNameOfCategoryList);
        type_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_query_type_name.setAdapter(type_name_adapter);

        //配置bill的listView
        for(int j=1;j<am.queryTypeListByCategory(spinner_query_category.getSelectedItem().toString()).size();j++) {
            String typeName = spinner_query_type_name.getItemAtPosition(j).toString();
            billArrayList = bs.queryBillsByType(am.queryTypeByName(typeName));
            for (int i = billArrayList.size() - 1; i >= 0; i--) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("type", typeName);
                map.put("amount", billArrayList.get(i).getAmount());
                if(billArrayList.get(i).getWallet() != null)
                    map.put("wallet", billArrayList.get(i).getWallet().getName());
                Date date = billArrayList.get(i).getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(date);
                map.put("date", dateString);
                billItemList.add(map);
            }
        }

        adapter = new SimpleAdapter(AsApplication.getContext(), billItemList,
                R.layout.list_bill_with_date, new String[]{"amount","wallet","type", "date"},
                new int[]{R.id.textView_query_bill_amount,R.id.textView_query_bill_wallet,
                        R.id.textView_query_bill_type, R.id.textView_query_date});
        ListView listView = (ListView) findViewById(R.id.listView_query_bill_by_type);
        listView.setAdapter(adapter);

        //为大类spinner设置监听器
        spinner_query_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeNameOfCategoryList.clear();
                categoryTypeList = am.queryTypeListByCategory(categoryList.get(position));
                typeNameOfCategoryList.add("所有类型");
                for(int i=0;i<categoryTypeList.size();i++){
                    typeNameOfCategoryList.add(categoryTypeList.get(i).getName());
                }
                type_name_adapter.notifyDataSetChanged();
                spinner_query_type_name.setSelection(0);
                String typeName = spinner_query_type_name.getSelectedItem().toString();
                billItemList.clear();
                curPosition = 0;
                for(int j=1;j<=am.queryTypeListByCategory(spinner_query_category.getSelectedItem().toString()).size();j++) {
                    typeName = spinner_query_type_name.getItemAtPosition(j).toString();
                    billArrayList = bs.queryBillsByType(am.queryTypeByName(typeName));
                    for (int i = billArrayList.size() - 1; i >= 0; i--) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("type", typeName);
                        map.put("amount", billArrayList.get(i).getAmount());
                        if(billArrayList.get(i).getWallet() != null)
                            map.put("wallet", billArrayList.get(i).getWallet().getName());
                        Date date = billArrayList.get(i).getDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = sdf.format(date);
                        map.put("date", dateString);
                        billItemList.add(map);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //为名称spinner设置监控器
        spinner_query_type_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position;
                billItemList.clear();
                String typeName = spinner_query_type_name.getSelectedItem().toString();
                if(curPosition==0){
                    for(int j=1;j<=am.queryTypeListByCategory(spinner_query_category.getSelectedItem().toString()).size();j++) {
                        typeName = spinner_query_type_name.getItemAtPosition(j).toString();
                        billArrayList = bs.queryBillsByType(am.queryTypeByName(typeName));
                        for (int i = billArrayList.size() - 1; i >= 0; i--) {
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("type", typeName);
                            map.put("amount", billArrayList.get(i).getAmount());
                            if(billArrayList.get(i).getWallet() != null)
                                map.put("wallet", billArrayList.get(i).getWallet().getName());
                            Date date = billArrayList.get(i).getDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dateString = sdf.format(date);
                            map.put("date", dateString);
                            billItemList.add(map);
                        }
                    }
                }
                else {
                    billArrayList = bs.queryBillsByType(am.queryTypeByName(typeName));
                    for (int i = billArrayList.size() - 1; i >= 0; i--) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("type", typeName);
                        map.put("amount", billArrayList.get(i).getAmount());
                        if(billArrayList.get(i).getWallet() != null)
                            map.put("wallet", billArrayList.get(i).getWallet().getName());
                        Date date = billArrayList.get(i).getDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = sdf.format(date);
                        map.put("date", dateString);
                        billItemList.add(map);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
