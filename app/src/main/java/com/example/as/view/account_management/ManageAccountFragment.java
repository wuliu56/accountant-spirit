package com.example.as.view.account_management;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.AccountManager;

public class ManageAccountFragment extends Fragment {
    private AccountManager am = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account,container,false);
        Button btn_edit_account_info = (Button) view.findViewById(R.id.button_edit_account_info);//获取修改账户信息的按钮
        Button btn_change_password = (Button) view.findViewById(R.id.button_change_password);//获取更换密码的按钮
        Button btn_set_walletList = (Button) view.findViewById(R.id.button_set_walletList);//获取设置钱包清单的按钮
        Button btn_set_currency = (Button) view.findViewById(R.id.button_set_currency);//获取设置货币的按钮
        Button btn_set_typeList = (Button) view.findViewById(R.id.button_set_typeList);//获取设置收支类型清单的按钮
        Button btn_log_out = view.findViewById(R.id.button_log_out);//获取登出按钮

        AsApplication asApplication = (AsApplication) getActivity().getApplication();
        String accountId = asApplication.getAccountId();
        am = new AccountManager(accountId);

        btn_edit_account_info.setOnClickListener(l);
        btn_change_password.setOnClickListener(l);
        btn_set_walletList.setOnClickListener(l);
        btn_set_currency.setOnClickListener(l);
        btn_set_typeList.setOnClickListener(l);
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.logOut();
                getActivity().finish();
            }
        });
        return view;
    }

    View.OnClickListener l = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch(v.getId()) {
                case R.id.button_edit_account_info: {
                    intent = new Intent(getActivity(),
                            EditAccountInfoActivity.class);
                    break;
                }
                case R.id.button_change_password:{
                    intent = new Intent(getActivity(),
                            ChangePasswordActivity.class);
                    break;
                }
                case R.id.button_set_walletList:{
                    intent = new Intent(getActivity(),
                            SetWalletActivity.class);
                    break;
                }
                case R.id.button_set_currency:{
                    intent = new Intent(getActivity(),
                            SetCurrencyActivity.class);
                    break;
                }
                case R.id.button_set_typeList:{
                    intent = new Intent(getActivity(),SetTypeActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    };
}
