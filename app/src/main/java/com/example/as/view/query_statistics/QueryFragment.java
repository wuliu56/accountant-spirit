package com.example.as.view.query_statistics;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.as.R;

public class QueryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query,container,false);
        Button btn = (Button)view.findViewById(R.id.button_query);
        Button btn2 = (Button) view.findViewById(R.id.button_statistics);
        btn.setOnClickListener(l);
        btn2.setOnClickListener(l);
        return view;
    }

    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            if(v.getId()==R.id.button_query){
                intent = new Intent(getActivity(),QueryByTypeActivity.class);
            }
            if(v.getId()==R.id.button_statistics){
                intent = new Intent(getActivity(),StatisticsActivity.class);
            }
            startActivity(intent);
        }
    };
}
