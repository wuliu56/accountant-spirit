package com.example.as.view.query_statistics;

import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.as.R;
import com.example.as.entity.Bill;
import com.example.as.entity.Type;
import com.example.as.service.AccountManager;
import com.example.as.service.BillRecorder;
import com.example.as.service.BillSearcher;
import com.example.as.view.account_management.AsApplication;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticsActivity extends Activity {
    private BillSearcher bs = new BillSearcher();
    private BillRecorder br = new BillRecorder();
    private AccountManager am = new AccountManager(AsApplication.getAccountId());

    //文本组件
    private TextView tv_total_in_amount = null;
    private TextView tv_total_out_amount = null;
    private TextView tv_total_amount = null;

    //下拉框组件
    private Spinner spinner_year = null;
    private Spinner spinner_month = null;

    //状态参数
    private int year;
    private int month = 13;//全月份
    private boolean chartType = true;//图表类型的参数

    //图表
    private List<PieEntry> entries = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<String>();
    private PieChart pieChart = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //获取文本组件
        tv_total_in_amount = (TextView) findViewById(R.id.textView_total_in_amount);
        tv_total_out_amount = (TextView) findViewById(R.id.textView_total_out_amount);
        tv_total_amount = (TextView) findViewById(R.id.textView_total_amount);

        //设置年下拉框的数据
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        ArrayList<String> yearList = new ArrayList<String>();
        for(int i=calendar.get(Calendar.YEAR);i>=1900;i--){
            yearList.add(String.valueOf(i));
        }

        //设置月下拉框的数据
        ArrayList<String> monthList = new ArrayList<String>();
        for(int i = 1;i<=12;i++){
            monthList.add(String.valueOf(i));
        }
        monthList.add("全月份");

        //配置年下拉框
        spinner_year = (Spinner) findViewById(R.id.spinner_sta_year);
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, yearList);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_year.setAdapter(adapter_year);

        //配置月下拉框
        spinner_month = (Spinner) findViewById(R.id.spinner_sta_month);
        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,monthList);
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(adapter_month);
        spinner_month.setSelection(month-1);

        //设置年、月下拉框的监听器
        spinner_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year = 2021-position;
                if(month == 13){
                    tv_total_in_amount.setText(String.valueOf(bs.queryYearlyInAmount(year)));
                    tv_total_out_amount.setText(String.valueOf(bs.queryYearlyOutAmount(year)));
                    tv_total_amount.setText(String.valueOf(bs.queryYearlyOutAmount(year)-bs.queryYearlyInAmount(year)));
                }else{
                    tv_total_in_amount.setText(String.valueOf(bs.queryMonthlyInAmount(year,month)));
                    tv_total_out_amount.setText(String.valueOf(bs.queryMonthlyOutAmount(year,month)));
                    tv_total_amount.setText(String.valueOf(bs.queryMonthlyOutAmount(year,month)-bs.queryMonthlyInAmount(year,month)));
                }
                showChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position+1;
                if(month == 13){
                    tv_total_in_amount.setText(String.valueOf(bs.queryYearlyInAmount(year)));
                    tv_total_out_amount.setText(String.valueOf(bs.queryYearlyOutAmount(year)));
                    tv_total_amount.setText(String.valueOf(bs.queryYearlyOutAmount(year)-bs.queryYearlyInAmount(year)));
                }
                else{
                    tv_total_in_amount.setText(String.valueOf(bs.queryMonthlyInAmount(year,month)));
                    tv_total_out_amount.setText(String.valueOf(bs.queryMonthlyOutAmount(year,month)));
                    tv_total_amount.setText(String.valueOf(bs.queryMonthlyOutAmount(year,month)-bs.queryMonthlyInAmount(year,month)));
                }
                showChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageButton btn = (ImageButton) findViewById(R.id.button_switch);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(chartType){
                        showInChart();
                        chartType = false;
                    }
                    else{
                        showOutChart();
                        chartType = true;
                    }
                }
        });

        //绘制饼图
        categoryList.add("收入");
        categoryList.add("购物");
        categoryList.add("出行");
        categoryList.add("饮食");
        categoryList.add("住房");
        categoryList.add("教育");
        categoryList.add("娱乐");
        categoryList.add("医药");
        categoryList.add("投资");
        categoryList.add("其他");
        pieChart = (PieChart) findViewById(R.id.pie_chart);
        showChart();
    }

    public void showChart(){
        if(chartType){
            showOutChart();
        }
        else{
            showInChart();
        }
    }

    public void showInChart(){
        pieChart.clear();
        float categoryAmount = 0;
        ArrayList<Type> typeList = am.queryTypeListByCategory(categoryList.get(0));
        ArrayList<Bill> billList = new ArrayList<Bill>();
        entries.clear();
        for(int i=0;i<typeList.size();i++) {
            ArrayList<Bill> billOfType = bs.queryBillsByType(typeList.get(i));
            if(month == 13){
                for(int k=0;k<billOfType.size();k++){
                    if((billOfType.get(k).getDate().getYear()+1900) == year)
                        categoryAmount+=billOfType.get(k).getAmount();
                }
            }
            else{
                for(int k=0;k<billOfType.size();k++){
                    if((billOfType.get(k).getDate().getYear()+1900) == year
                            &&(billOfType.get(k).getDate().getMonth()+1) == month)
                        categoryAmount+=billOfType.get(k).getAmount();
                }
            }
            entries.add(new PieEntry(categoryAmount, typeList.get(i).getName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "类型");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for(int c:ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for(int c:ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        dataSet.setColors(colors); // 每个点之间线的颜色，还有其他几个方法，自己看
        dataSet.setValueFormatter(new ValueFormatter() {   // 将值转换为想要显示的形式，比如，某点值为1，变为“1￥”,MP提供了三个默认的转换器，
            // LargeValueFormatter:将大数字变为带单位数字；PercentFormatter：将值转换为百分数；StackedValueFormatter，对于BarChart，是否只显示最大值图还是都显示
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + am.getCurrenctCurrency().getSymbol();
            }
        });
        dataSet.setValueLinePart1Length(0.1f);//设置连接线的长度
        dataSet.setValueTextSize(12f);
        //x,y值在圆外显示(在圆外才会有连接线)
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData= new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.black));
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawEntryLabels(false);   // 同上,默认true，记住颜色和环不要一样，否则会显示不出来
        pieChart.setCenterText("总收入构成"); // 圆环中心的文字，会自动适配不会被覆盖
        pieChart.setTransparentCircleColor(Color.WHITE); // 上述透明圆环的颜色
        pieChart.setMaxAngle(360);    // 设置整个饼形图的角度，默认是360°即一个整圆，也可以设置为弧，这样现实的值也会重新计算
        pieData.setDrawValues(true);//饼状图上显示值
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setBackgroundColor(getResources().getColor(R.color.gray));
        pieChart.notifyDataSetChanged();
    }

    public void showOutChart(){
        pieChart.clear();
        entries.clear();
        for (int i = 1; i < categoryList.size(); i++) {
            float categoryAmount = 0;
            ArrayList<Type> typeList = am.queryTypeListByCategory(categoryList.get(i));
            ArrayList<Bill> billList = new ArrayList<Bill>();
            for(int j=0;j<typeList.size();j++) {
                ArrayList<Bill> billOfType = bs.queryBillsByType(typeList.get(j));
                if (month == 13) {
                    for(int k=0;k<billOfType.size();k++){
                        if(billOfType.get(k).getDate().getYear()+1900 == year)
                            categoryAmount+=billOfType.get(k).getAmount();
                    }
                }
               else{
                    for(int k=0;k<billOfType.size();k++){
                        if((billOfType.get(k).getDate().getYear()+1900) == year
                                &&(billOfType.get(k).getDate().getMonth()+1) == month)
                            categoryAmount+=billOfType.get(k).getAmount();
                    }
                }
            }
            entries.add(new PieEntry(categoryAmount, categoryList.get(i)));
        }
        PieDataSet dataSet = new PieDataSet(entries, "大类");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for(int c:ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for(int c:ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        dataSet.setColors(colors); // 每个点之间线的颜色，还有其他几个方法，自己看
        dataSet.setValueFormatter(new ValueFormatter() {   // 将值转换为想要显示的形式，比如，某点值为1，变为“1￥”,MP提供了三个默认的转换器，
            // LargeValueFormatter:将大数字变为带单位数字；PercentFormatter：将值转换为百分数；StackedValueFormatter，对于BarChart，是否只显示最大值图还是都显示
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + am.getCurrenctCurrency().getSymbol();
            }
        });
        dataSet.setValueLinePart1Length(0.1f);//设置连接线的长度
        dataSet.setValueTextSize(12f);
        //x,y值在圆外显示(在圆外才会有连接线)
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData pieData= new PieData(dataSet);
        PieChart pieChart = (PieChart) findViewById(R.id.pie_chart);
        pieChart.setData(pieData);
        pieChart.setEntryLabelColor(getResources().getColor(R.color.black));
        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);
        pieChart.setDrawEntryLabels(false);   // 同上,默认true，记住颜色和环不要一样，否则会显示不出来
        pieChart.setCenterText("总支出构成"); // 圆环中心的文字，会自动适配不会被覆盖
        pieChart.setTransparentCircleColor(Color.WHITE); // 上述透明圆环的颜色
        pieChart.setMaxAngle(360);    // 设置整个饼形图的角度，默认是360°即一个整圆，也可以设置为弧，这样现实的值也会重新计算
        pieData.setDrawValues(true);//饼状图上显示值
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setBackgroundColor(getResources().getColor(R.color.gray));
        pieChart.notifyDataSetChanged();
    }
}
