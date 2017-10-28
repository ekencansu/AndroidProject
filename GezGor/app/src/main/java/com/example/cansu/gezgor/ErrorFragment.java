package com.example.cansu.gezgor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ErrorFragment extends Fragment {//If no internet connection or there is other problems show "try again" mesagge in page
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String msg = getArguments().getString("msg");
        View view = inflater.inflate(R.layout.error_fragment, container, false);
        TextView errorMsg = (TextView)view.findViewById(R.id.error);
        errorMsg.setText(msg);
        return view;
    }
}