package com.example.as.view.account_management;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.entity.Wallet;
import com.example.as.entity.WalletList;
import com.example.as.service.AccountManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetWalletActivity extends Activity {
    private AccountManager am = null;
    private WalletList walletList = null;
    private int curPosition = 0;
    private Wallet curWallet = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallet);

        //获取各个组件
        EditText et_name = (EditText) findViewById(R.id.editText_wallet_name);
        EditText et_amount = (EditText) findViewById(R.id.editText_wallet_amount);
        Button btn_confirm = (Button) findViewById(R.id.button_confirm_set_wallet);
        Button btn_cancel = (Button) findViewById(R.id.button_cancel_set_wallet);
        ListView listView = (ListView) findViewById(R.id.listView_set_wallet);

        //初始化各个组件和成员
        String accountId = AsApplication.getAccountId();
        am = new AccountManager(accountId);
        walletList = am.getWalletList();

        //配置ListView
        List<Map<String, Object>> walletItemList = new ArrayList<Map<String, Object>>();
        ArrayList<String> walletNameList = new ArrayList<String>();
        ArrayList<Double> walletAmountList = new ArrayList<Double>();
        Wallet wallet = null;

        for(int i = 0;i < walletList.getSize();i++){
            //先后设置walletNameList,walletAmountList,walletItemList的内容
            wallet = walletList.getWalletByIndex(i);
            String name = wallet.getName();
            Double amount = wallet.getAmount();
            walletNameList.add(name);
            walletAmountList.add(amount);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("name", "钱包名："+name);
            map.put("amount", "资产额："+amount+am.getCurrenctCurrency().getSymbol());
            walletItemList.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(this, walletItemList, R.layout.list_set_wallet,
                new String[]{"name", "amount"}, new int[]{R.id.textView_wallet_name, R.id.textView_wallet_amount}){
            @Override
            //重写getView方法实现点击的效果
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv_wallet_name = (TextView) view.findViewById(R.id.textView_wallet_name);
                TextView tv_wallet_amount =(TextView) view.findViewById(R.id.textView_wallet_amount);//获取一行中的两个TextView

                if(position == curPosition){
                    tv_wallet_name.setEnabled(true);
                    tv_wallet_amount.setEnabled(true);
                }
                else{
                    tv_wallet_name.setEnabled(false);
                    tv_wallet_amount.setEnabled(false);
                }
                return view;
            }
        };
        listView.setAdapter(listAdapter);

        //设置点击一行的监听器
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curPosition = position;
                btn_confirm.setEnabled(true);
                TextView tv_wallet_name = (TextView) view.findViewById(R.id.textView_wallet_name);
                TextView tv_wallet_amount =(TextView) view.findViewById(R.id.textView_wallet_amount);
                double curWalletAmount = walletAmountList.get(position);
                String name_text = tv_wallet_name.getText().toString();
                String name = name_text.substring(4);
                String amount_text = tv_wallet_amount.getText().toString();
                et_name.setText(name);
                et_amount.setText(String.valueOf(curWalletAmount));
                listAdapter.notifyDataSetChanged();
            }
        });

    btn_cancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

    btn_confirm.setEnabled(false);
    btn_confirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //获取输入信息
            String in_name = et_name.getText().toString();
            int numberEqualName = 0;
            for(int i = 0;i < walletList.getSize();i++){
                if(walletList.getWalletByIndex(i).getName() == in_name){
                    numberEqualName++;
                }
            }

            //判断钱包名称是否合法
            if(in_name.length() == 0 || et_amount.getText().toString().length() == 0){
                Toast.makeText(AsApplication.getContext(),"请填写完整",Toast.LENGTH_SHORT).show();
            }
            else if(numberEqualName>0){
                Toast.makeText(AsApplication.getContext(),"已经存在同名钱包",Toast.LENGTH_SHORT).show();
            }
            else {//此时输入合法，修改curWallet，更新数据库
                double in_amount = Double.parseDouble(et_amount.getText().toString());
                curWallet = am.getWalletList().getWalletByIndex(curPosition);
                String old_name = curWallet.getName();
                curWallet.setName(in_name);
                curWallet.setAmount(in_amount);
                am.setWallet(old_name, curWallet);

                //修改数组和集合，更新界面中的listView
                walletNameList.set(curPosition, in_name);
                walletAmountList.set(curPosition, in_amount);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", "钱包名：" + in_name);
                map.put("amount", "资产额：" + in_amount + am.getCurrenctCurrency().getSymbol());
                walletItemList.set(curPosition, map);
                listAdapter.notifyDataSetChanged();
                Toast.makeText(AsApplication.getContext(),"修改钱包成功",Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
}
