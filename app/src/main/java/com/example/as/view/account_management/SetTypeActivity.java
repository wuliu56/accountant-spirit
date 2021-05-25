package com.example.as.view.account_management;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.entity.Type;
import com.example.as.entity.TypeList;
import com.example.as.service.AccountManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SetTypeActivity extends Activity {
    private AccountManager am = null;
    private TypeList typeList = null;
    private String curCategory = null;
    private FragmentManager fm = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_type);

        //初始化成员
        String accountId = AsApplication.getAccountId();
        am = new AccountManager(accountId);
        typeList = am.getTypeList();
        curCategory = "收入";

        //初始化需要的数据
        ArrayList<String> categoryList = new ArrayList<String>();//所有大类的数组

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

        //为listView配置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_choose_category, categoryList);
        ListView listView = (ListView) findViewById(R.id.listView_choose_category);
        listView.setAdapter(adapter);

        //配置fragment
        fm = getFragmentManager();

        addFragment(R.id.fl_set_type, new SetTypeFragment());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCategory = categoryList.get(position);
                addFragment(R.id.fl_set_type, new SetTypeFragment());
            }
        });
    }

    public String getCurCategory(){
        return curCategory;
    }

    public ArrayList<Type> getTypeListByCurCategory() {
        return am.queryTypeListByCategory(curCategory);
    }

    private void addFragment(int layoutID, Fragment fragment) {
        if (fragment.isAdded()) {
            fm.beginTransaction().show(fragment).commit();
        } else {
            fm.beginTransaction().add(layoutID, fragment).commit();
        }
    }
}
