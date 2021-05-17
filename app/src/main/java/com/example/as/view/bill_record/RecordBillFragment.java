package com.example.as.view.bill_record;

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

public class RecordBillFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_bill,container,false);
        Button btn = (Button)view.findViewById(R.id.button_manage_budget);
        Button btn2 = (Button)view.findViewById(R.id.button_manage_bill);
        btn.setOnClickListener(l);
        btn2.setOnClickListener(l);
        return view;
    }

    View.OnClickListener l = new View.OnClickListener(){
        public void onClick(View v){
            Intent intent = null;
            if(v.getId()==R.id.button_manage_budget){
                intent = new Intent(getActivity(),ManageBudgetActivity.class);
            }
            if(v.getId()==R.id.button_manage_bill){
                intent = new Intent(getActivity(),ManageBillActivity.class);
            }
            startActivity(intent);
        }
    };
}
