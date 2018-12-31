package com.gawain.androidautoclickdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("CheckRoot.isRoot()   " + CheckRoot.isRoot());
//
        System.out.println("CheckRoot.getRootAhth()   " + CheckRoot.getRootAhth());

    }
}
