package com.example.dinokeylas.ewaste.ui.Message;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.FieldOfficerModel;
import com.example.dinokeylas.ewaste.model.MessageModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_FIELD_OFFICER;
import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_MESSAGE;

public class DetailMessageActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private MessageModel message;
    private FieldOfficerModel sender;
    private String date, time, messageContent, sendersName, sendersEmail, sendersPhoneNumber;

    private TextView tvSenderName, tvSenderEmail, tvSenderPhoneNumber, tvMessageContent;
    private Button btnDate, btnTime;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_message);

        db = FirebaseFirestore.getInstance();
        message = (MessageModel) getIntent().getSerializableExtra("message");

        tvSenderName = findViewById(R.id.tv_name);
        tvSenderEmail = findViewById(R.id.tv_email);
        tvSenderPhoneNumber = findViewById(R.id.tv_phone);
        tvMessageContent = findViewById(R.id.tv_message);
        btnDate = findViewById(R.id.btn_date);
        btnTime = findViewById(R.id.btn_time);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Pesan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSenderData();
    }

    private void getSenderData() {
        db.collection(COLLECTION_FIELD_OFFICER)
                .whereEqualTo("fOemailAddress", message.getSender())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                FieldOfficerModel p = d.toObject(FieldOfficerModel.class);
                                p.setId(d.getId());
                                sender = p;
                            }
                            updateMessageData();
                            fillToLayout();
                        }
                    }
                });
    }

    private void updateMessageData() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("read", true);

        db.collection(COLLECTION_MESSAGE).document(message.getMessageId()).update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // to do here
            }
        });
    }

    private void fillToLayout() {
        Date dates = message.getSendDate();
        String DATE_FORMAT = "dd-MM-yyyy";
        String DATE_FORMAT2 = "HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(DATE_FORMAT2);

        date = "" + simpleDateFormat.format(dates);
        time = "" + simpleDateFormat2.format(dates);
        messageContent = message.getMessage();
        sendersName = "Nama : " + sender.getfOName();
        sendersPhoneNumber = "Telepon : " + sender.getfOphoneNumber();
        sendersEmail = "Email : " + sender.getfOemailAddress();

        tvSenderName.setText(sendersName);
        tvSenderEmail.setText(sendersEmail);
        tvSenderPhoneNumber.setText(sendersPhoneNumber);
        tvMessageContent.setText(messageContent);
        btnDate.setText(date);
        btnTime.setText(time);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailMessageActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
