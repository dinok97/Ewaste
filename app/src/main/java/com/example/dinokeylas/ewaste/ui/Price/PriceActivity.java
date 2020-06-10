package com.example.dinokeylas.ewaste.ui.Price;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.PriceModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_PRICE;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseFirestore db;

    private static final String SEMUA = "Semua";
    private static final String PLASTIK = "Plastik";
    private static final String KERTAS = "Kertas";
    private static final String BESI_SENG = "Besi dan Seng";
    private static final String ALUMINIUM = "Aluminium";
    private static final String BOTOL_KACA = "Botol dan Kaca";
    private static final String KUNINGAN_TEMBAGA = "Kuningan dan Tembaga";
    private static final String GARBAGE_NAME = "garbageName";

    private List<PriceModel> priceListAll;
    private List<PriceModel> priceListPlastik;
    private List<PriceModel> priceListKertas;
    private List<PriceModel> priceListBesiSeng;
    private List<PriceModel> priceListAluminium;
    private List<PriceModel> priceListBotolKaca;
    private List<PriceModel> priceListKuninganTembaga;
    private RecyclerView recyclerView;
    private PriceAdapter priceAdapter;
    private ProgressBar progressBar;
    private ImageView back;
    private Spinner mSpinner;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        db = FirebaseFirestore.getInstance();

        priceListAll = new ArrayList<>();
        priceListPlastik = new ArrayList<>();
        priceListKertas = new ArrayList<>();
        priceListBesiSeng = new ArrayList<>();
        priceListAluminium = new ArrayList<>();
        priceListBotolKaca = new ArrayList<>();
        priceListKuninganTembaga = new ArrayList<>();

        mSpinner = (Spinner) findViewById(R.id.spinner_typeOfWaste);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.waste_type_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        back = (ImageView) findViewById(R.id.btn_backToHome);
        recyclerView = findViewById(R.id.rv_price);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Select All Price Data From Database
        progressBar.setVisibility(View.VISIBLE);
        db.collection(COLLECTION_PRICE)
                .orderBy(GARBAGE_NAME, Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                PriceModel p = d.toObject(PriceModel.class);
                                p.setId(d.getId());
                                priceListAll.add(p);
                                //Toast.makeText(PriceActivity.this, d.getId(), Toast.LENGTH_SHORT).show();
                            }
                            //priceAdapter.notifyDataSetChanged();
                            insertToRecyclerView(priceListAll);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //on failure ac/tion
                        Toast.makeText(PriceActivity.this, "Pengambilan data gagal", Toast.LENGTH_SHORT).show();
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(PriceActivity.this, HomeActivity.class);
                startActivity(in);
            }
        });
    }

    private void insertToRecyclerView(List<PriceModel> mPriceList) {

        mSettAdapter(mPriceList);

        for (i = 0; i < mPriceList.size(); i++) {
            if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(PLASTIK)) {
                priceListPlastik.add(mPriceList.get(i));
            } else if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(KERTAS)) {
                priceListKertas.add(mPriceList.get(i));
            } else if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(BESI_SENG)) {
                priceListBesiSeng.add(mPriceList.get(i));
            } else if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(ALUMINIUM)) {
                priceListAluminium.add(mPriceList.get(i));
            } else if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(BOTOL_KACA)) {
                priceListBotolKaca.add(mPriceList.get(i));
            } else if (mPriceList.get(i).getGarbageType().equalsIgnoreCase(KUNINGAN_TEMBAGA)) {
                priceListKuninganTembaga.add(mPriceList.get(i));
            }
        }
    }

    public void mSettAdapter(List<PriceModel> mPriceList) {
        priceAdapter = new PriceAdapter(this, mPriceList);
        recyclerView.setAdapter(priceAdapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (item.equalsIgnoreCase(SEMUA)) {
            mSettAdapter(priceListAll);
        } else if (item.equalsIgnoreCase(PLASTIK)) {
            mSettAdapter(priceListPlastik);
        } else if (item.equalsIgnoreCase(KERTAS)) {
            mSettAdapter(priceListKertas);
        } else if (item.equalsIgnoreCase(BESI_SENG)) {
            mSettAdapter(priceListBesiSeng);
        } else if (item.equalsIgnoreCase(ALUMINIUM)) {
            mSettAdapter(priceListAluminium);
        } else if (item.equalsIgnoreCase(BOTOL_KACA)) {
            mSettAdapter(priceListBotolKaca);
        } else if (item.equalsIgnoreCase(KUNINGAN_TEMBAGA)) {
            mSettAdapter(priceListKuninganTembaga);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
