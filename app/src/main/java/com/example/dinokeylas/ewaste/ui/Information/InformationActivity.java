package com.example.dinokeylas.ewaste.ui.Information;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.InformationModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_INFORMATION;

public class InformationActivity extends AppCompatActivity {
    Intent in = new Intent();

    private FirebaseFirestore db;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private InformationListAdapter informationListAdapter;
    private List <InformationModel> informationList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        db = FirebaseFirestore.getInstance();

        progressBar = findViewById(R.id.progressbar);
        back = findViewById(R.id.btn_backToHome);
        recyclerView = findViewById(R.id.rv_information);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        informationList = new ArrayList<>();

        informationListAdapter = new InformationListAdapter(InformationActivity.this, informationList);
        recyclerView.setAdapter(informationListAdapter);

        progressBar.setVisibility(View.VISIBLE);
        db.collection(COLLECTION_INFORMATION).orderBy("iDate", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        if(!documentSnapshots.isEmpty()){
                            List <DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                InformationModel m = d.toObject(InformationModel.class);
                                m.setInformationId(d.getId());
                                informationList.add(m);
                            }
                            informationListAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //hjk
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in = new Intent(InformationActivity.this, HomeActivity.class);
                startActivity(in);
            }
        });

//        ArrayList <InformationModel> model = new ArrayList<>();
//
//        model.add(new InformationModel(
//            "Bank Sampah Malang (BSM) jadi Percontohan",
//                getString(R.string.textOne),
//                "Admin",
//                "https://firebasestorage.googleapis.com/v0/b/ewaste-4314a.appspot.com/o/informationImages%2Fgambar1.jpg?alt=media&token=40f89410-bfa3-4955-b20c-4e4e8886c2bb",
//                 Calendar.getInstance().getTime()
//        ));
//        model.add(new InformationModel(
//                "Menabung di Bank Sampah Malang",
//                getString(R.string.textTwo),
//                "Admin",
//                "https://firebasestorage.googleapis.com/v0/b/ewaste-4314a.appspot.com/o/informationImages%2Fgambar2.jpg?alt=media&token=a69d3271-d3a9-43fc-af2a-23aaf7b4923a",
//                Calendar.getInstance().getTime()
//        ));
//        model.add(new InformationModel(
//                "Riwayat Bank Sampah Malang, Kini Punya 30.000 Nasabah",
//                getString(R.string.textThree),
//                "Admin",
//                "https://firebasestorage.googleapis.com/v0/b/ewaste-4314a.appspot.com/o/informationImages%2Fgambar3.jpg?alt=media&token=ab20b63f-7ace-42bd-a1e9-bd8cc75e35c0",
//                Calendar.getInstance().getTime()
//        ));
//        for(int i=0; i<model.size(); i++) {
//            db.collection(COLLECTION_INFORMATION).add(model.get(i))
//                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                        @Override
//                        public void onSuccess(DocumentReference documentReference) {
//                            Toast.makeText(InformationActivity.this, "upload data berhasil", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//
//                        }
//                    });
//        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
