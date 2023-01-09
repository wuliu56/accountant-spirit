package com.example.as.view.account_management;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.entity.Type;
import com.example.as.entity.TypeList;
import com.example.as.service.AccountManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetTypeActivity extends Activity {
    private AccountManager am = null;
    private TypeList typeList = null;
    private String curCategory = null;
    private FragmentManager fm = null;
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    private int curPosition = 0;

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
        ArrayList<Map<String,String>> categoryItemList = new ArrayList<Map<String,String>>();

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

        for(int i = 0; i < categoryList.size(); i++){
            HashMap<String, String> map = new HashMap<String,String>();
            map.put("category", categoryList.get(i));
            categoryItemList.add(map);
        }

        //为listView配置适配器
        ListView listView = (ListView) findViewById(R.id.listView_choose_category);
        SimpleAdapter adapter = new SimpleAdapter(this, categoryItemList, R.layout.list_choose_category,
                new String[]{"category"}, new int[]{R.id.textView_choose_category}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv_category = (TextView) view.findViewById(R.id.textView_choose_category);

                if(position == curPosition){
                    tv_category.setEnabled(true);
                }
                else{
                    tv_category.setEnabled(false);
                }
                return view;
            }
        };
        listView.setAdapter(adapter);

        //配置fragment
        fm = getFragmentManager();

        addFragment(R.id.fl_set_type, new SetTypeFragment());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCategory = categoryList.get(position);
                SetTypeFragment setTypeFragment = new SetTypeFragment();
                addFragment(R.id.fl_set_type, setTypeFragment);
                curPosition = position;
                adapter.notifyDataSetChanged();
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
        List<Fragment> fragments = getFragments();
        for (Fragment f : fragments) {
            if (!f.equals(fragment) && !f.isHidden()) {
                fm.beginTransaction().hide(f).commit();
            }
        }
        if (fragment.isAdded()) {
            fm.beginTransaction().show(fragment).commit();
        } else {
            fragmentList.add(fragment);
            fm.beginTransaction().add(layoutID, fragment).commit();
        }
    }

    private ArrayList<Fragment> getFragments(){
        return this.fragmentList;
    }
}
