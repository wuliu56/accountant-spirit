package com.example.as.view.account_management;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.service.AccountManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageAccountFragment extends Fragment {
    private AccountManager am = null;
    private boolean isFirstLoading = true;
    private ImageView icon = null;
    @Override
    public void onResume() {
        super.onResume();
        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
           refreshIcon();
        }
        isFirstLoading = false;
    }

    public void refreshIcon(){
        String icon_uri = "";
        if(am.getAccountInfo() != null) {
             icon_uri = am.getAccountInfo().getIcon();
        }
        if(icon_uri.length() != 0){
            Uri uri = Uri.parse(icon_uri);
            icon.setImageURI(uri);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);

        String accountId = AsApplication.getAccountId();//获取当前账户的accountId
        am = new AccountManager(accountId);

        //设置ListView
        String[] title = new String[]{"修改账户信息", "更换密码", "设置货币", "设置钱包", "设置收支类型"};
        int[] imageId = new int[]{R.drawable.edit_account_info, R.drawable.change_password, R.drawable.set_currency,
                R.drawable.set_wallet, R.drawable.set_type};
        List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        for(int i = 0;i < title.length;i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", title[i]);
            map.put("image", imageId[i]);
            listItem.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(getActivity(), listItem, R.layout.list_manage_account,
                new String[]{"title", "image"}, new int[]{R.id.title, R.id.image});
        ListView listView = view.findViewById(R.id.listView_set_currency);
        listView.setAdapter(listAdapter);

        icon = (ImageView) view.findViewById(R.id.imageView_icon);//设置头像
        refreshIcon();

        //设置ListView的监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0: {
                        intent = new Intent(getActivity(),
                                EditAccountInfoActivity.class);
                        break;
                    }
                    case 1: {
                        intent = new Intent(getActivity(),
                                ChangePasswordActivity.class);
                        break;
                    }
                    case 2: {
                        intent = new Intent(getActivity(),
                                SetCurrencyActivity.class);
                        break;
                    }
                    case 3: {
                        intent = new Intent(getActivity(),
                                SetWalletActivity.class);
                        break;
                    }
                    case 4: {
                        intent = new Intent(getActivity(), SetTypeActivity.class);
                        break;
                    }
                }
                startActivity(intent);
            }
        });

        //设置退出登录按钮的监听器
        Button btn_log_out = (Button)view.findViewById(R.id.button_log_out);
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.logOut();
                SharedPreferences sp = getActivity().getSharedPreferences("Id_Password", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
