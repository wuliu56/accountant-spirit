package com.example.as.view.account_management;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.entity.Account;
import com.example.as.entity.Type;
import com.example.as.entity.TypeList;
import com.example.as.entity.Wallet;
import com.example.as.service.AccountManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class SetTypeFragment extends Fragment {
    private String category;//所对应大类
    private ArrayList<Type> typeList = null; //所对应大类下所有的种类
    private ArrayList<String> typeNameList = new ArrayList<String>();//种类名称的数组
    private ArrayList<Map<String,Object>> typeItemList = new ArrayList<Map<String, Object>>();//种类信息的ArrayList
    private SimpleAdapter simpleAdapter = null;//ListView的适配器
    private AccountManager am = new AccountManager(AsApplication.getAccountId());
    private ListView listView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_type, container, false);

        //初始化成员
        SetTypeActivity parentActivity = (SetTypeActivity)getActivity();
        category = parentActivity.getCurCategory();
        typeList = parentActivity.getTypeListByCurCategory();

        //设置ListView需要的数据
        //加入每一个type的信息
        for(int i = 0; i < typeList.size(); i++){
            String typeName = typeList.get(i).getName();
            typeNameList.add(typeName);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.color.transparent);
            map.put("name", typeName);
            typeItemList.add(map);
        }

        //配置适配器
        simpleAdapter = new SimpleAdapter(AsApplication.getContext(), typeItemList, R.layout.list_set_type,
                new String[]{"image","name"}, new int[]{R.id.imageView_type_icon,R.id.textView_type_name}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageButton ib_set_type = (ImageButton) view.findViewById(R.id.imageButton_edit_type);
                ImageButton ib_delete_type = (ImageButton) view.findViewById(R.id.imageButton_delete_type);

                //为一行中删除按钮设置监听器
                ib_delete_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteDialogue(position);
                    }
                });

                //为一行中设置按钮设置监听器
                ib_set_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditDialogue(position);
                    }
                });
                return view;
            }
        };

        //设置ListView
        listView = (ListView) view.findViewById(R.id.listView_set_type);
        listView.setAdapter(simpleAdapter);

        //设置新增类型按钮
        ImageView btn_add_type = (ImageView) getActivity().findViewById(R.id.imageView_add_type);
        btn_add_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddEditDialogue();
            }
        });
        return view;


    }

    private void setCatelogue(String category){
        this.category = category;
    }

    //新增按钮的点击事件
    private void showAddEditDialogue(){
        SetTypeActivity parentActivity = (SetTypeActivity)getActivity();
        category = parentActivity.getCurCategory();
        final EditText et_add_type = new EditText(getActivity());
        et_add_type.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        AlertDialog.Builder editDialogue = new AlertDialog.Builder(getActivity());
        editDialogue.setTitle("添加类型");
        editDialogue.setView(et_add_type);
        editDialogue.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改名字数组和map数组中的对应内容，修改数据库中的type表，并刷新ListView
                        String newTypeName = et_add_type.getText().toString();
                        if(newTypeName.length() != 0) {
                            Type type = new Type(category,newTypeName,AsApplication.getAccountId());
                            typeList.add(type);
                            typeNameList.add(newTypeName);
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("name", newTypeName);
                            map.put("category",category);
                            typeItemList.add(map);
                            am.newType(type);
                            simpleAdapter.notifyDataSetChanged();
                            Toast.makeText(AsApplication.getContext(),"添加类型成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AsApplication.getContext(),"类型名称不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        editDialogue.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        editDialogue.show();
    }

    //删除图片按钮的点击事件
    private void showDeleteDialogue(int position){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setMessage("确定删除该类型？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除名字数组和map数组中的对应内容，修改数据库中的type表，并刷新ListView
                        typeList.remove(position);
                        typeItemList.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                        am.deleteByType(typeNameList.get(position));
                        typeNameList.remove(position);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        normalDialog.show();
    }

    //编辑按钮的点击事件
    private void showEditDialogue(int position){
        final EditText et_edit_type = new EditText(getActivity());
        et_edit_type.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        AlertDialog.Builder editDialogue = new AlertDialog.Builder(getActivity());
        editDialogue.setTitle("修改类型名称");
        editDialogue.setView(et_edit_type);
        editDialogue.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //修改名字数组和map数组中的对应内容，修改数据库中的type表，并刷新ListView
                        String oldTypeName = typeNameList.get(position);
                        String newTypeName = et_edit_type.getText().toString();
                        if(newTypeName.length() != 0) {
                            Type type = typeList.get(position);
                            type.setName(newTypeName);
                            typeList.set(position, type);
                            typeNameList.set(position, newTypeName);
                            Map<String, Object> map = typeItemList.get(position);
                            map.remove("name");
                            map.put("name", newTypeName);
                            typeItemList.set(position, map);
                            am.setType(oldTypeName, type);
                            simpleAdapter.notifyDataSetChanged();
                            Toast.makeText(AsApplication.getContext(),"修改类型成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AsApplication.getContext(),"类型名称不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        editDialogue.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        editDialogue.show();
    }

}
