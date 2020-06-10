package com.example.dinokeylas.ewaste.ui.SavingBook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.SavingBookModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_SAVINGBOOK;

public class SavingBookActivity extends AppCompatActivity{
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private RecyclerView recyclerView;
    private SavingBookAdapter savingBookAdapter;
    private List<SavingBookModel> savingBookList;
    private ProgressBar progressBar;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_book);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        progressBar = findViewById(R.id.progressbar);
        back = findViewById(R.id.btn_backToHome);
        recyclerView = findViewById(R.id.rv_savingBook);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        savingBookList = new ArrayList<>();

        savingBookAdapter = new SavingBookAdapter(this, savingBookList);
        recyclerView.setAdapter(savingBookAdapter);

        progressBar.setVisibility(View.VISIBLE);

        //get saving book data
        getSavingBook(mUser.getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(SavingBookActivity.this, HomeActivity.class);
                startActivity(in);
            }
        });
    }

    private void getSavingBook(String userEmail){
        String email = userEmail;
        db.collection(COLLECTION_SAVINGBOOK).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        progressBar.setVisibility(View.GONE);
                        if(!documentSnapshots.isEmpty()){
                            List <DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d: list){
                                SavingBookModel s = d.toObject(SavingBookModel.class);
                                s.setTranId(d.getId());
                                savingBookList.add(s);
                            }
                            sortSavingBookList();
                            savingBookAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //on failure action
                    }
                });

    }

    private void sortSavingBookList() {
        // selection sort
        for (int i = 0; i < savingBookList.size(); i++) {
            int index = i;
            for (int j = i + 1; j < savingBookList.size(); j++) {
                if (savingBookList.get(i).getDate().compareTo(savingBookList.get(j).getDate()) > 0) {
                    index = j;
                }
            }
            SavingBookModel model = savingBookList.get(index);
            savingBookList.set(index, savingBookList.get(i));
            savingBookList.set(i, model);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
