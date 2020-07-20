package com.adarsh.smartcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class rules extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.rules,container,false);
        Intent intent = new Intent(getActivity(), instructions.class);
        startActivity(intent);
        return  inflater.inflate(R.layout.activity_instructions,container,false);
    }
}
