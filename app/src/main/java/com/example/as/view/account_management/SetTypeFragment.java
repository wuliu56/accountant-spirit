package com.example.as.view.account_management;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.entity.Account;
import com.example.as.entity.Type;
import com.example.as.entity.TypeList;
import com.example.as.service.AccountManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class SetTypeFragment extends Fragment {
    private String category;//所对应大类
    private ArrayList<Type> typeList = null; //所对应大类下所有的种类

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_type, container, false);

        //初始化成员
        SetTypeActivity parentActivity = (SetTypeActivity)getActivity();
        category = parentActivity.getCurCategory();
        typeList = parentActivity.getTypeListByCurCategory();

        //设置ListView需要的数据
        ArrayList<String> typeNameList = new ArrayList<String>();//种类名称的数组
        ArrayList<Map<String,Object>> typeItemList = new ArrayList<Map<String, Object>>();//种类信息的ArrayList
        //加入每一个type的信息
        for(int i = 0; i < typeList.size(); i++){
            String typeName = typeList.get(i).getName();
            typeNameList.add(typeName);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", typeName);
            typeItemList.add(map);
        }

        //配置适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(AsApplication.getContext(), typeItemList, R.layout.list_set_type,
                new String[]{"name"}, new int[]{R.id.textView_type_name});

        //设置ListView
        ListView listView = (ListView) view.findViewById(R.id.listView_set_type);
        listView.setAdapter(simpleAdapter);

        return view;
    }

    void setCatelogue(String category){
        this.category = category;
    }
}
