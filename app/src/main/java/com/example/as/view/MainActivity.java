package com.example.as.view;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.as.R;
import com.example.as.view.account_management.ManageAccountFragment;
import com.example.as.view.bill_record.RecordBillFragment;
import com.example.as.view.query_statistics.QueryFragment;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private FragmentManager fm = getFragmentManager();
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.button_record_bill);
        Button btn2 = (Button)findViewById(R.id.button_query_statistics);
        Button btn3 = (Button)findViewById(R.id.button_manage_account);
        btn.setOnClickListener(l);
        btn2.setOnClickListener(l);
        btn3.setOnClickListener(l);
        RecordBillFragment recordBillFragment = new RecordBillFragment();
        FragmentTransaction ft = fm.beginTransaction();
        if(savedInstanceState == null)
            ft.add(R.id.record_bill_fl,recordBillFragment);
        ft.commit();
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment f = null;
            switch(v.getId()){
                case R.id.button_record_bill:{
                    f = new RecordBillFragment();
                    break;
                }
                case R.id.button_query_statistics:{
                    f = new QueryFragment();
                    break;
                }
                case R.id.button_manage_account: {
                    f = new ManageAccountFragment();
                    break;
                }
            }
            fm.beginTransaction().replace(R.id.record_bill_fl, f).commit();
        }
    };
}