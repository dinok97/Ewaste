package com.example.dinokeylas.ewaste.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.PriceModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class cobaaa extends AppCompatActivity {

    FirebaseFirestore db;
    ArrayList<PriceModel> priceList = new ArrayList<>();

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cobaaa);
        TextView a = (TextView) findViewById(R.id.coba);
//
//        db = FirebaseFirestore.getInstance();
//
//        priceList.add(new PriceModel("Plastik", "Glangsi Utuh 10Kg", 175, 250));
//        priceList.get(0).setId("P31");
//        priceList.add(new PriceModel("Plastik", "Glangsi Rusak", 325, 400));
//        priceList.get(1).setId("P32");
//        priceList.add(new PriceModel("Plastik", "Mika", 400, 500    ));
//        priceList.get(2).setId("P33");
//        priceList.add(new PriceModel("Plastik", "Plastik Keras", 550, 650));
//        priceList.get(3).setId("P34");
//        priceList.add(new PriceModel("Plastik", "Plastik Keras Bening", 2600, 2750));
//        priceList.get(4).setId("P35");
//        priceList.add(new PriceModel("Plastik", "CD/DVD/Kaset PS", 2600, 2800));
//        priceList.get(5).setId("P36");
//        priceList.add(new PriceModel("Plastik", "Galon PC", 3550, 3750));
//        priceList.get(6).setId("P37");
//        priceList.add(new PriceModel("Kertas", "Buku Tulis", 1550, 1700));
//        priceList.get(7).setId("K1");
//        priceList.add(new PriceModel("Kertas", "Kertas HVS", 1550, 1700));
//        priceList.get(8).setId("K2");
//        priceList.add(new PriceModel("Kertas", "Koran", 1850, 2000));
//        priceList.get(9).setId("K3");
//        priceList.add(new PriceModel("Kertas", "Kertas Semen", 1450, 1600));
//        priceList.get(10).setId("K4");
//        priceList.add(new PriceModel("Kertas", "Majalah Duplek", 450, 550));
//        priceList.get(11).setId("K5");
//        priceList.add(new PriceModel("Kertas", "Karton/Kardus", 1300, 1450));
//        priceList.get(12).setId("K6");
//        priceList.add(new PriceModel("Kertas", "Kertas Campur", 900, 1000));
//        priceList.get(13).setId("K7");
//        priceList.add(new PriceModel("Kertas", "Kertas Buram", 900, 1000));
//        priceList.get(14).setId("K8");
//        priceList.add(new PriceModel("Kertas", "PET Botol Warna Kotor", 2300, 2500));
//        priceList.get(15).setId("K9");
//        priceList.add(new PriceModel("Besi dan Seng", "Seng Omplong", 1400, 1600));
//        priceList.get(16).setId("S1");
//        priceList.add(new PriceModel("Besi dan Seng", "Seng Biasa", 550, 700));
//        priceList.get(17).setId("S2");
//        priceList.add(new PriceModel("Besi dan Seng", "Besi Super", 2800, 3000));
//        priceList.get(18).setId("BS1");
//        priceList.add(new PriceModel("Besi dan Seng", "Besi Biasa", 1600, 1750));
//        priceList.get(19).setId("BS2");
//        priceList.add(new PriceModel("Aluminium", "Slender Cop/Seker", 11500, 12500));
//        priceList.get(20).setId("A1");
//        priceList.add(new PriceModel("Aluminium", "Antena/Panci/Wajan", 9500, 10000));
//        priceList.get(21).setId("A2");
//        priceList.add(new PriceModel("Aluminium", "Kaleng Aluminium", 9500, 10000));
//        priceList.get(22).setId("A3");
//        priceList.add(new PriceModel("Aluminium", "Plat", 10500, 11500));
//        priceList.get(23).setId("A4");
//        priceList.add(new PriceModel("Aluminium", "Siku", 14300, 14500));
//        priceList.get(24).setId("A5");
//        priceList.add(new PriceModel("Aluminium", "Tutup Botol Aluminium", 3300, 3500));
//        priceList.get(25).setId("A6");
//        priceList.add(new PriceModel("Aluminium", "Perunggu", 5500, 6000));
//        priceList.get(26).setId("A7");
//        priceList.add(new PriceModel("Aluminium", "StainLess Monel", 13000, 14000));
//        priceList.get(27).setId("A8");
//        priceList.add(new PriceModel("Botol dan Kaca", "Kaca Kecil", 100, 100));
//        priceList.get(28).setId("B1");
//        priceList.add(new PriceModel("Botol dan Kaca", "Kaca Kecil", 100, 100));
//        priceList.get(29).setId("B2");
//        priceList.add(new PriceModel("Botol dan Kaca", "Kaca Orson", 100, 100));
//        priceList.get(30).setId("B3");
//        priceList.add(new PriceModel("Botol dan Kaca", "Botol Kecap/Saos", 450, 550));
//        priceList.get(31).setId("B4");
//        priceList.add(new PriceModel("Botol dan Kaca", "Botol Bensin", 650, 800));
//        priceList.get(32).setId("B5");
//        priceList.add(new PriceModel("Botol dan Kaca", "Botol bir", 650, 800));
//        priceList.get(33).setId("B6");
//        priceList.add(new PriceModel("Botol dan Kaca", "Botol Coca-Cola/Sprite", 200, 250));
//        priceList.get(34).setId("B7");
//        priceList.add(new PriceModel("Kuningan dan Tembaga", "Kuningan", 28500, 30000));
//        priceList.get(35).setId("KN");
//        priceList.add(new PriceModel("Kuningan dan Tembaga", "Tembaga Biasa", 45500, 47000));
//        priceList.get(36).setId("T1");
//        priceList.add(new PriceModel("Kuningan dan Tembaga", "Tembaga Super", 53500, 55000));
//        priceList.get(37).setId("T2");
//
//        for (i = 0; i < priceList.size(); i++) {
//            db.collection(COLLECTION_PRICE).document(priceList.get(i).getId()).set(priceList.get(i))
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            if (i == 29) {
//                                Toast.makeText(cobaaa.this, "data sukses " + priceList.get(29).getId(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(cobaaa.this, "gagal upload " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        }
    }
}

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Spinner element
//        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        Button button=(Button)findViewById(R.id.button);
//
//        // Spinner click listener
//        spinner.setOnItemSelectedListener(this);
//
//        // Spinner Drop down elements
//        List<String> categories = new ArrayList<String>();
//        categories.add("Item 1");
//        categories.add("Item 2");
//        categories.add("Item 3");
//        categories.add("Item 4");
//        categories.add("Item 5");
//        categories.add("Item 6");
//
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(MainActivity.this,SecondActivity.class);
//                intent.putExtra("data",String.valueOf(spinner.getSelectedItem()));
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
//
//    }
//
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//}

