package com.example.dinokeylas.ewaste.ui.Sell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;

public class Jual extends AppCompatActivity implements View.OnClickListener {
    Intent in = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jual);
        CardView daftarHarga = (CardView) findViewById(R.id.home);
        daftarHarga.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        in = new Intent(this, HomeActivity.class);
        startActivity(in);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
