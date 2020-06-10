package com.example.dinokeylas.ewaste.ui.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.UsersModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_USERS;

public class EditProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    private String userId, profileImageUrl;

    private UsersModel usersModel;
    private FirebaseFirestore db;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageRef;

    private EditText userName, fullName, phoneNumber, address;
    private TextView emailAddress;
    private CircleImageView circleProfileImage;
    private ImageView selectImage;
    private ProgressBar progressBar;
    private Button buttonUpdate, buttonBack;

    private Uri uriProfileImage, uriOldProfileImage;

    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usersModel = documentSnapshot.toObject(UsersModel.class);
                fillToLayout();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        Bundle id = intent.getExtras();
        if (id != null) {
            userId = (String) id.get("id");
        }

        userName = (EditText) findViewById(R.id.et_userName);
        fullName = (EditText) findViewById(R.id.et_fullName);
        emailAddress = (TextView) findViewById(R.id.tv_emailAddress);
        phoneNumber = (EditText) findViewById(R.id.et_phoneNumber);
        address = (EditText) findViewById(R.id.et_address);
        circleProfileImage = (CircleImageView) findViewById(R.id.civ_profileImage);
        selectImage = (ImageView) findViewById(R.id.iv_selectImage);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        buttonBack = (Button) findViewById(R.id.btn_back);
        buttonUpdate = (Button) findViewById(R.id.btn_update);

        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usersModel = documentSnapshot.toObject(UsersModel.class);
                fillToLayout();
                loadImageProfileUser(profileImageUrl);
            }
        });

        circleProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                updateProfileUser();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadImageProfileUser(String ImageUrl) {
        if (profileImageUrl != null) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(circleProfileImage);
        }
    }

    private void updateProfileUser() {
        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userId);
        docRef.update("fullName", fullName.getText().toString().trim());
        docRef.update("phoneNumber", phoneNumber.getText().toString().trim());
        docRef.update("profileImageUrl", profileImageUrl);
        docRef.update("address", address.getText().toString().trim());
        docRef.update("userName", userName.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditProfileActivity.this, "data berhasil di Update", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        //navigateToHomeActivity();
    }

    private void showImageChooser() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(galleryIntent, "pilih foto profil Anda"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                circleProfileImage.setImageBitmap(bitmap);
                if (usersModel.getProfileImageUrl()==null){
                    uploadPhoto();
                } else {
                    deleteOldPhoto();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteOldPhoto(){
        mFirebaseStorage = FirebaseStorage.getInstance();
        StorageReference photoReference = mFirebaseStorage.getReferenceFromUrl(usersModel.getProfileImageUrl());
        photoReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                profileImageUrl = null;
                uploadPhoto();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void uploadPhoto() {
        mStorageRef = FirebaseStorage.getInstance().getReference("profileImages/" + System.currentTimeMillis() + ".jpg");
        if (uriProfileImage != null) {
            progressBar.setVisibility(View.VISIBLE);
            mStorageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                            usersModel.setProfileImageUrl(profileImageUrl);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void fillToLayout() {
        userName.setText(usersModel.getUserName());
        fullName.setText(usersModel.getFullName());
        emailAddress.setText(usersModel.getEmailAddress());
        profileImageUrl = usersModel.getProfileImageUrl();
        phoneNumber.setText(usersModel.getPhoneNumber());
        address.setText(usersModel.getAddress());
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
        intent.putExtra("mark", "move");
        startActivity(intent);
    }
}