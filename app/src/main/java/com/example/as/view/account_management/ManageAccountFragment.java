package com.example.as.view.account_management;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;

public class ManageAccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_account,container,false);
        Button btn = (Button) view.findViewById(R.id.button_edit_account_info);
        Button btn2 = (Button) view.findViewById(R.id.button_change_password);
        Button btn3 = (Button) view.findViewById(R.id.button_set_wallet);
        Button btn4 = (Button) view.findViewById(R.id.button_set_currency);
        btn.setOnClickListener(l);
        btn2.setOnClickListener(l);
        btn3.setOnClickListener(l);
        btn4.setOnClickListener(l);
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
                case R.id.button_set_wallet:{
                    intent = new Intent(getActivity(),
                            SetWalletActivity.class);
                    break;
                }
                case R.id.button_set_currency:{
                    intent = new Intent(getActivity(),
                            SetCurrencyActivity.class);
                    break;
                }
            }
            startActivity(intent);
        }
    };
}
