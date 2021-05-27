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
import android.widget.AdapterView;
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
import com.example.as.entity.Bill;
import com.example.as.entity.Currency;
import com.example.as.entity.DailyBill;
import com.example.as.entity.DailyBudget;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;
import com.example.as.service.AccountManager;
import com.example.as.service.BillRecorder;
import com.example.as.view.account_management.AsApplication;

import org.w3c.dom.Text;

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
    private DailyBill curDailyBill = null;                  //当前的日账单清单

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

    //按钮组件
    Button bt_set_budget = null;
    Button bt_delete_budget = null;
    Button bt_add_bill = null;
    Button bt_set_bill = null;
    Button bt_delete_bill = null;

    private int curPosition = 0;//当前所在ListView的行数


    /*bug：如何在切换时初始化当天的预算信息
    @Override
    public void onHiddenChanged(boolean hidden) {
        if(!hidden) {
            CalendarView calendarView = (CalendarView) getActivity().findViewById(R.id.calendarView);
            calendarView.setDate(curCalendar.getTimeInMillis());
            curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR),
                    curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH) );
            Date date = new Date(curCalendar.get(Calendar.YEAR),
                    curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH));
            curDailyBill = billRecorder.queryDailyBill(date);
            showBudget();
            showBill();
        }
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_budget, container, false);
        listView = (ListView) view.findViewById(R.id.listView_budget);                 //初始化列表组件

        tv_budget_amount = (TextView) view.findViewById(R.id.textView_budget_amount);
        tv_budget_ifover = (TextView) view.findViewById(R.id.textView_budget_ifover);//初始化文本组件

        bt_set_bill = (Button) view.findViewById(R.id.button_set_bill);//初始化按钮组件
        bt_add_bill = (Button) view.findViewById(R.id.button_add_bill);
        bt_delete_bill = (Button) view.findViewById(R.id.button_delete_bill);
        bt_delete_budget = (Button) view.findViewById(R.id.button_delete_budget);
        bt_set_budget = (Button) view.findViewById(R.id.button_set_budget);

        bt_set_bill.setEnabled(false);//初始化按钮初始enabled值
        bt_add_bill.setEnabled(false);
        bt_delete_bill.setEnabled(false);
        bt_set_budget.setEnabled(false);
        bt_delete_budget.setEnabled(false);

        //获取日历组件并设置默认日期
        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setDate(curCalendar.getTimeInMillis());

        //为listview配置适配器
        adapter = new SimpleAdapter(AsApplication.getContext(), billItemList,
                R.layout.list_bill, new String[]{"amount","wallet","type"},
                new int[]{R.id.textView_bill_amount,R.id.textView_bill_wallet,R.id.textView_bill_type}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =  super.getView(position, convertView, parent);
                TextView tv_amount = (TextView) view.findViewById(R.id.textView_bill_amount);
                TextView tv_type_name = (TextView) view.findViewById(R.id.textView_bill_type);
                TextView tv_wallet_name = (TextView) view.findViewById(R.id.textView_bill_wallet);
                if(curPosition != position){
                    tv_amount.setEnabled(false);
                    tv_type_name.setEnabled(false);
                    tv_wallet_name.setEnabled(false);
                }
                else{
                    tv_amount.setEnabled(true);
                    tv_type_name.setEnabled(true);
                    tv_wallet_name.setEnabled(true);
                }
                return view;
            }
        };
        listView.setAdapter(adapter);

        //设置日历组件选择日期的监听器
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                bt_delete_budget.setEnabled(true);
                bt_set_budget.setEnabled(true);
                bt_add_bill.setEnabled(true);

                curCalendar.set(Calendar.YEAR, year);
                curCalendar.set(Calendar.MONTH, month);
                curCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //更新当前的标记
                curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR),
                        curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH) );
                Date date = new Date(curCalendar.get(Calendar.YEAR),
                        curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH));
                curDailyBill = billRecorder.queryDailyBill(date);

                if(curDailyBill.getSize() != 0){
                    if(curDailyBill.getSize() <= curPosition)
                        curPosition = 0;
                    curWallet = curDailyBill.getBillByIndex(0).getWallet();
                    curType = curDailyBill.getBillByIndex(0).getType();
                    bt_set_bill.setEnabled(true);
                    bt_delete_bill.setEnabled(true);
                } else{
                    curPosition = 0;
                }

                showBudget();//显示当前预算信息
                showBill();
            }
        });

        //为listView设置点击监听器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bt_set_bill.setEnabled(true);
                bt_delete_bill.setEnabled(true);
                curPosition = position;
                curWallet = curDailyBill.getBillByIndex(position).getWallet();
                curType = curDailyBill.getBillByIndex(position).getType();
                adapter.notifyDataSetChanged();
            }
        });

        //设置“设置预算”按钮的监听器
        bt_set_budget.setOnClickListener(new View.OnClickListener() {
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
                        if(budgetAmountString.length() == 0){
                            Toast.makeText(getActivity(), "请填写完整", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            double budgetAmountDouble = Double.parseDouble(budgetAmountString);
                            Date date = new Date(curCalendar.get(Calendar.YEAR) - 1900, curCalendar.get(Calendar.MONTH),
                                    curCalendar.get(Calendar.DAY_OF_MONTH));
                            if (curDailyBudget == null) {
                                curDailyBudget = new DailyBudget(budgetAmountDouble, date, budgetAmountDouble,
                                        AsApplication.getAccountId());
                                billRecorder.newDailyBudget(curDailyBudget);
                                curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR),
                                        curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH));
                            } else {
                                curDailyBudget.setAmount(budgetAmountDouble);
                                curDailyBudget.setBalance(budgetAmountDouble);
                                billRecorder.setDailyBudget(curDailyBudget);
                                curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR),
                                        curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH));
                            }

                            showBudget();
                        }
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

        //设置“删除预算”按钮
        bt_delete_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curDailyBudget == null)return;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("是否确定删除当天的预算？").setTitle("删除预算");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH), curCalendar.get(Calendar.DAY_OF_MONTH));
                        billRecorder.deleteDailyBudget(date);
                        curDailyBudget = null;
                        bt_delete_budget.setEnabled(false);
                        showBudget();
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
        });

        //设置“添加账单”按钮
        bt_add_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetBillActivity.class);
                startActivityForResult(intent,0x11);
            }
        });

        //设置“设置账单”按钮
        bt_set_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetBillActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("walletName", curWallet.getName());
                bundle.putString("typeName", curType.getName());
                bundle.putString("typeCategory", curType.getCategory());
                bundle.putDouble("amount", curDailyBill.getBillByIndex(curPosition).getAmount());
                intent.putExtras(bundle);
                startActivityForResult(intent,0x12);
            }
        });

        //设置“删除账单”按钮
        bt_delete_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("是否确定删除该条收支记录？").setTitle("删除收支记录");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),curCalendar.get(Calendar.DAY_OF_MONTH));
                        curDailyBill = billRecorder.queryDailyBill(date);
                        billRecorder.deleteBill(curDailyBill.getBillByIndex(curPosition).getId());
                        curDailyBill = billRecorder.queryDailyBill(date);
                        if(curDailyBill.getSize() == 0){
                            curPosition = 0;
                            bt_delete_bill.setEnabled(false);
                            bt_set_bill.setEnabled(false);
                        }
                        else{
                            if(curPosition>0)
                                curPosition -= 1;
                            else if(curPosition == 0)
                                curPosition = 1;
                            curWallet = curDailyBill.getBillByIndex(curPosition).getWallet();
                            curType = curDailyBill.getBillByIndex(curPosition).getType();

                            showBill();
                            showBudget();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        return view;
    }



    //显示预算信息
    private void showBudget(){
        if(curDailyBudget == null){
            tv_budget_amount.setText("当前预算额：");
            tv_budget_ifover.setText("是否已超支：");
            tv_budget_amount.setTextColor(getResources().getColor(R.color.golden));
            tv_budget_ifover.setTextColor(getResources().getColor(R.color.golden));
        }
        else {
            String dailyBudgetAmount = String.valueOf(curDailyBudget.getAmount());
            tv_budget_amount.setText("当前预算额：" + dailyBudgetAmount + currency.getSymbol());
            curDailyBudget.judgeIfOver();
            if(curDailyBudget.getIfOver()){
                tv_budget_ifover.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
            else{
                tv_budget_ifover.setTextColor(getResources().getColor(android.R.color.holo_green_light));
            }
            String ifOver = (curDailyBudget.getIfOver() ? "是" : "否");
            tv_budget_ifover.setText("是否已超支：" + ifOver);
        }
    }

    //显示账单信息
    private void showBill(){
        Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),curCalendar.get(Calendar.DAY_OF_MONTH));
        curDailyBill = billRecorder.queryDailyBill(date);
        //设置ListView的数据集
        billAmountList.clear();
        walletNameList.clear();
        typeNameList.clear();
        billItemList.clear();
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
        adapter.notifyDataSetChanged();
    }

    //在新增预算、账单和设置账单时的回调函数
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x11&&resultCode == 0x11){
            //从bundle中获取账单信息
            Bundle bundle = data.getExtras();
            String typeName = bundle.getString("typeName");
            String typeCategory = bundle.getString("typeCategory");
            double amount = bundle.getDouble("amount");
            String walletName = bundle.getString("walletName");

            //用于构造bill
            Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),
                    curCalendar.get(Calendar.DAY_OF_MONTH));
            Type type = am.queryTypeByName(typeName);
            Wallet wallet = am.queryWalletByName(walletName);

            //新建账单
            billRecorder.newBill(new Bill(amount, date , type, wallet, AsApplication.getAccountId()));
            bt_set_bill.setEnabled(true);
            bt_delete_bill.setEnabled(true);

            curDailyBudget = billRecorder.queryDailyBudget(curCalendar.get(Calendar.YEAR), curCalendar.get(Calendar.MONTH)+1,
                    curCalendar.get(Calendar.DAY_OF_MONTH));
            curDailyBudget.judgeIfOver();
            showBill();
            showBudget();
        }
        else if(requestCode == 0x12&&resultCode == 0x11){
            //从bundle中获取账单信息
            Bundle bundle = data.getExtras();
            String typeName = bundle.getString("typeName");
            String typeCategory = bundle.getString("typeCategory");
            double amount = bundle.getDouble("amount");
            String walletName = bundle.getString("walletName");

            //用于构造bill
            Date date = new Date(curCalendar.get(Calendar.YEAR)-1900, curCalendar.get(Calendar.MONTH),
                    curCalendar.get(Calendar.DAY_OF_MONTH));
            curType = am.queryTypeByName(typeName);
            curWallet = am.queryWalletByName(walletName);

            //设置账单
            billRecorder.setBill(new Bill(curDailyBill.getBillByIndex(curPosition).getId(),
                    amount, date , curType, curWallet, AsApplication.getAccountId()));
            curDailyBill = billRecorder.queryDailyBill(date);

            showBill();
            showBudget();
        }
    }
}
