package com.example.as.view.bill_record;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.as.R;
import com.example.as.entity.Currency;
import com.example.as.entity.DailyBill;
import com.example.as.entity.DailyBudget;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;
import com.example.as.service.AccountManager;
import com.example.as.service.BillRecorder;
import com.example.as.view.account_management.AsApplication;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RecordBillFragment extends Fragment {
    //两个服务类分别用于获取账单、预算，设置信息
    private BillRecorder billRecorder = new BillRecorder();
    private AccountManager am = new AccountManager();

    private Currency currency = am.getCurrenctCurrency();   //用户设置的货币
    private Wallet curWallet = null;                        //当前账单对应的钱包
    private Type curType = null;                            //当前账单对应的类型
    private Calendar curCalendar = Calendar.getInstance();  //当前的日期（日历类）
    private DailyBudget curDailyBudget = null;              //当前的预算
    private DailyBill curDailyBill = null;                  // 当前的日账单清单

    private ListView listView = null;//列表视图的组件
    private SimpleAdapter adapter = null;                   //使用的适配器

    //列表视图的数据
    private ArrayList<Map<String,Object>> billItemList = new ArrayList<Map<String,Object>>(); //账单
    private ArrayList<String> billAmountList = new ArrayList<String>(); //账单额
    private ArrayList<String> walletNameList = new ArrayList<String>(); //账单对应的钱包名字
    private ArrayList<String> typeNameList = new ArrayList<String>();//账单对应的类型


    //文本组件
    TextView tv_budget_amount = null;
    TextView tv_budget_ifover = null;

    private int curPosition = 0;//当前所在ListView的行数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_manage_budget, container, false);
        listView = (ListView) view.findViewById(R.id.listView_budget);
        tv_budget_amount = (TextView) view.findViewById(R.id.textView_budget_amount);
        tv_budget_ifover = (TextView) view.findViewById(R.id.textView_budget_ifover);


        //获取日历组件并设置默认日期
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setDate(curCalendar.getTimeInMillis());

        showBudget();//显示当前预算信息
        showBill();//显示当前日账单清单


        //设置日历组件选择日期的监听器
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                curCalendar.set(Calendar.YEAR, year);
                curCalendar.set(Calendar.MONTH, month);
                curCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Toast.makeText(getActivity(),
                        curCalendar.get(Calendar.YEAR)+" "+(curCalendar.get(Calendar.MONTH)+1)+" "+curCalendar.get(Calendar.DAY_OF_MONTH),
                Toast.LENGTH_SHORT).show();

                curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR),
                        curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH) );

                showBudget();//显示当前预算信息
                //showBill();
            }
        });

        //设置“设置预算”按钮的监听器
        Button ib_add_budget = (Button) view.findViewById(R.id.button_set_budget);
        ib_add_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder editDialog = new AlertDialog.Builder(getActivity());
                final EditText et = new EditText(getActivity());
                et.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editDialog.setTitle("设置预算").setView(et);
                editDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String budgetAmountString = et.getText().toString();
                        double budgetAmountDouble = Double.parseDouble(budgetAmountString);
                        Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),
                                curCalendar.get(Calendar.DAY_OF_MONTH));
                        if(curDailyBudget == null) {
                            curDailyBudget = new DailyBudget(budgetAmountDouble, date, budgetAmountDouble,
                                    AsApplication.getAccountId());
                            billRecorder.newDailyBudget(curDailyBudget);
                        }
                        else {
                            curDailyBudget.setAmount(budgetAmountDouble);
                            curDailyBudget.setBalance(budgetAmountDouble);
                            billRecorder.setDailyBudget(curDailyBudget);
                        }
                        showBudget();
                    }
                });
                editDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                editDialog.show();
            }
        });
        return view;
    }
    private void showBudget(){
        if(curDailyBudget == null){
            tv_budget_amount.setText("当前预算额：");
            tv_budget_ifover.setText("是否已超支：");
        }
        else {
            String dailyBudgetAmount = String.valueOf(curDailyBudget.getAmount());
            tv_budget_amount.setText("当前预算额：" + dailyBudgetAmount + currency.getSymbol());
            String ifOver = (curDailyBudget.getIfOver() ? "是" : "否");
            tv_budget_ifover.setText("是否已超支：" + ifOver);
        }
    }

    private void showBill(){
        Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),curCalendar.get(Calendar.DAY_OF_MONTH));
        curDailyBill = billRecorder.queryDailyBill(date);
        //设置ListView的数据集
        for(int i = 0; i < curDailyBill.getSize(); i++) {
            double amount = curDailyBill.getBillByIndex(i).getAmount();
            String amountString = String.valueOf(amount);
            String walletName = curDailyBill.getBillByIndex(i).getWallet().getName();
            String typeName = curDailyBill.getBillByIndex(i).getType().getName();
            Map<String, Object> map = new HashMap<String, Object>();
            billAmountList.add(amountString);
            walletNameList.add(walletName);
            typeNameList.add(typeName);
            map.put("amount", amountString);
            map.put("wallet", walletName);
            map.put("type", typeName);
            billItemList.add(map);
        }
        adapter = new SimpleAdapter(AsApplication.getContext(), billItemList, R.layout.list_bill,
                new String[]{"amount", "wallet", "type"},
                new int[]{R.id.textView_bill_amount, R.id.textView_type_name, R.id.textView_wallet_name}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                return view;
            }
        };
        listView.setAdapter(adapter);
    }
}
