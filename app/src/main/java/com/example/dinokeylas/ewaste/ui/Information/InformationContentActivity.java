package com.example.dinokeylas.ewaste.ui.Information;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.InformationModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InformationContentActivity extends AppCompatActivity {

    //private FirebaseFirestore db;

    private TextView judul, tanggal, penulis, isi;
    private ImageView gambar, back;

    private InformationModel informasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_content);

        //db = FirebaseFirestore.getInstance();
        informasi = (InformationModel) getIntent().getSerializableExtra("information");

        judul = findViewById(R.id.tv_informationTittleContent);
        tanggal = findViewById(R.id.tv_informationDateContent);
        penulis = findViewById(R.id.tv_informationAuthorContent);
        isi = findViewById(R.id.tv_informationContent);
        gambar = findViewById(R.id.iv_imageInformationContent);
        back = findViewById(R.id.iv_backToInfoList);

        Date date = informasi.getiDate();
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String tgl = ""+simpleDateFormat.format(date);

        judul.setText(informasi.getiTittle());
        tanggal.setText(tgl);
        penulis.setText(informasi.getiAuthor());
        isi.setText(informasi.getiContent());
        Glide.with(this)
                .load(informasi.getiInfoImageUrl())
                .into(gambar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationContentActivity.this, InformationActivity.class);
                startActivity(intent);
            }
        });

    }
}
