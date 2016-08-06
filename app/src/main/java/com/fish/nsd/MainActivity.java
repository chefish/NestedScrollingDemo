package com.fish.nsd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout ll= (LinearLayout) findViewById(R.id.linear);





        for (int i = 0; i < 50; i++) {
            TextView tv=new TextView(this);
            tv.setText("" + i);
            tv.setTextSize(60);
            ll.addView(tv);
        }
    }
}
