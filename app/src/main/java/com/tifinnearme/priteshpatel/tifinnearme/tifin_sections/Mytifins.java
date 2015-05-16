package com.tifinnearme.priteshpatel.tifinnearme.tifin_sections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tifinnearme.priteshpatel.tifinnearme.R;

/**
 * Created by pritesh.patel on 09-04-15.
 */
public class Mytifins extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tifins,container,false);
    }
}
