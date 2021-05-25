package com.example.as.view.account_management;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.example.as.entity.Currency;
import com.example.as.entity.CurrencyList;
import com.example.as.service.AccountManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetCurrencyActivity extends Activity {
    private Currency currentCurrency = null;
    private AccountManager am = null;
    static private int curPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_currency);

        String accountId = AsApplication.getAccountId();
        am = new AccountManager(accountId);//用当前accountId构造AccountManager
        CurrencyList currencyList = new CurrencyList();
        currentCurrency = am.getCurrenctCurrency();//初始化之前设置的currency

        //获取各组件
        ListView listView = (ListView) findViewById(R.id.listView_set_currency);//ListView组件
        Button btn_confirm = (Button) findViewById(R.id.button_confirm_set_currency);//确认按钮
        Button btn_cancel = (Button) findViewById(R.id.button_cancel_set_currency);//取消按钮
        TextView textView_currency = (TextView) findViewById(R.id.textView_current_currency);

        //设置ListView
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> symbolList = new ArrayList<String>();
        List<Map<String,String>> currencyItemList = new ArrayList<Map<String,String>>();//存放每种货币的name和symbol

        //为ListView配置适配器
        for(int i = 0;i < currencyList.getSize();i++){
            String name = currencyList.getCurrencyByIndex(i).getName();
            String symbol = currencyList.getCurrencyByIndex(i).getSymbol();
            nameList.add(name);
            symbolList.add(symbol);
            Map<String,String> map = new HashMap<String,String>();
            map.put("name", name);
            map.put("symbol",symbol);
            currencyItemList.add(map);
        }

        MySimpleAdapter listAdapter = new MySimpleAdapter(this, currencyItemList, R.layout.list_set_currency,
                new String[]{"name", "symbol"}, new int[]{R.id.textView_currency_name, R.id.textView_symbol});
        listView.setAdapter(listAdapter);

        //设置当前货币信息
        textView_currency.setText(textView_currency.getText().toString() + " " +
                currentCurrency.getSymbol() + currentCurrency.getName());

        //设置ListView监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_name = view.findViewById(R.id.textView_currency_name);
                TextView textView_symbol = view.findViewById(R.id.textView_symbol);
                String sel_name = nameList.get(position);
                String sel_symbol = nameList.get(position);
                currentCurrency = new Currency(sel_name, sel_symbol);
                curPosition = position;
                listAdapter.notifyDataSetChanged();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                am.setCurrentCurrency(currentCurrency);
                finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class MySimpleAdapter extends  SimpleAdapter{
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource,
                               String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            TextView tv_name = (TextView) view.findViewById(R.id.textView_currency_name);
            TextView tv_symbol = (TextView) view.findViewById(R.id.textView_symbol);
            if(curPosition == position){
                tv_name.setEnabled(true);
                tv_symbol.setEnabled(true);
                Drawable drawable = getResources().getDrawable(R.drawable.currency_selected);
                drawable.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
                tv_symbol.setCompoundDrawables(drawable, null, null, null);//只放左边
            }
            else{
                tv_name.setEnabled(false);
                tv_symbol.setEnabled(false);
                Drawable drawable = getResources().getDrawable(R.color.transparent);
                drawable.setBounds(0,0,40,40);
                tv_symbol.setCompoundDrawablesWithIntrinsicBounds(drawable,
                        null, null, null);
            }
            return view;
        }
    }
}

