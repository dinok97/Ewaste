
package com.example.dinokeylas.ewaste.ui.Profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.TranModel;
import com.example.dinokeylas.ewaste.model.UsersModel;
import com.example.dinokeylas.ewaste.ui.Sign.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_TRANSACTION;
import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_USERS;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Intent intent;
    private static final String USER_ID = "userId";
    private String userId = "user";
    private UsersModel usersModel;
    private List<TranModel> transactionList;
    private TransactionListAdapter transactionListAdapter;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    //for Fragment
    private static String profileImageUrl;
    private ProgressBar progressBar;
    private ImageView editProfile, detailProfile, logout;
    private CircleImageView profileImage;
    private TextView email, userName, noTransaction;
    private Dialog profileDialog;
    private Button transaction, transactionHistory;
    private RecyclerView transactionRecyclerView;

    //for Dialog
    private CircleImageView dProfileImage;
    private TextView dUserName, dFullName, dEmailAddress, dPhoneNumber;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //get user id
        assert mUser != null;
        userId = mUser.getUid();

        //initialize all view
        logout = (ImageView) view.findViewById(R.id.iv_logout);
        detailProfile = (ImageView) view.findViewById(R.id.iv_detailProfile);
        editProfile = (ImageView) view.findViewById(R.id.iv_editProfile);
        profileImage = (CircleImageView) view.findViewById(R.id.civ_profileImage);
        email = (TextView) view.findViewById(R.id.tv_emailAddress);
        userName = (TextView) view.findViewById(R.id.tv_userName);
        noTransaction = (TextView) view.findViewById(R.id.tv_no_transaction);
        transaction = (Button) view.findViewById(R.id.btn_transaction);
        transactionHistory = (Button) view.findViewById(R.id.btn_transactionHistory);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        //setting the adapter
        transactionRecyclerView = new RecyclerView(this.getActivity());
        transactionRecyclerView = (RecyclerView) view.findViewById(R.id.rv_transaction);
        transactionRecyclerView.setHasFixedSize(true);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        transactionList = new ArrayList<>();

        logout.setOnClickListener(this);
        detailProfile.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        userName.setOnClickListener(this);
        transaction.setOnClickListener(this);
        transactionHistory.setOnClickListener(this);

        //default active button
        transaction.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        transactionHistory.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        DocumentReference docRef = db.collection(COLLECTION_USERS).document(userId);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                usersModel = documentSnapshot.toObject(UsersModel.class);
                profileImageUrl = usersModel.getProfileImageUrl();
                fillToLayout();
                loadImageProfileUser(profileImageUrl);
                loadData(false);
            }
        });

        return view;
    }


    private void fillToLayout() {
        String text = "(" + usersModel.getEmailAddress() + ")";
        email.setText(text);
        userName.setText(usersModel.getUserName());
        loadImageProfileUser(profileImageUrl);
    }


    private void loadImageProfileUser(String ImageUrl) {
        if (profileImageUrl != null) {
            Glide.with(this)
                    .load(ImageUrl)
                    .into(profileImage);
            progressBar.setVisibility(View.GONE);
        }
    }


    private void loadData(Boolean mark) {
        transactionList.clear();
        //Select all transaction data from Firebase
        db.collection(COLLECTION_TRANSACTION)
                .whereEqualTo("done", mark)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
//                                Log.d("Transaction Data", d.getId() + " => " + d.getData());
                                TranModel p = d.toObject(TranModel.class);
                                p.setTranId(d.getId());
                                transactionList.add(p);
                            }
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            noTransaction.setVisibility(View.VISIBLE);
                        }
                        insertToRecyclerView(transactionList);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Error is", e.getMessage());
                    }
                });
    }


    private void insertToRecyclerView(List<TranModel> tranModels) {
        transactionListAdapter = new TransactionListAdapter(getContext(), tranModels);
        transactionRecyclerView.setAdapter(transactionListAdapter);
    }


    private void navigateToLoginActivity() {
        intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


    private void navigateToEditProfileActivity() {
        intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("id", userId);
        startActivity(intent);
    }


    private void logout() {
        mAuth.signOut();
        getActivity().finish();
        navigateToLoginActivity();
    }

    private void showProfileDialog() {
        profileDialog = new Dialog(getActivity());
        profileDialog.setContentView(R.layout.dialog_profile);
        profileDialog.setTitle("Profil Anda");

        dUserName = (TextView) profileDialog.findViewById(R.id.tv_userName);
        dFullName = (TextView) profileDialog.findViewById(R.id.tv_fullName);
        dEmailAddress = (TextView) profileDialog.findViewById(R.id.tv_emailAddress);
        dPhoneNumber = (TextView) profileDialog.findViewById(R.id.tv_phoneNumber);
        dProfileImage = (CircleImageView) profileDialog.findViewById(R.id.civ_detailProfileImage);

        dUserName.setText(usersModel.getUserName());
        dFullName.setText(usersModel.getFullName());
        dEmailAddress.setText(usersModel.getEmailAddress());
        dPhoneNumber.setText(usersModel.getPhoneNumber());
        profileImageUrl = usersModel.getProfileImageUrl();

        if (profileImageUrl != null) {
            Glide.with(this)
                    .load(profileImageUrl)
                    .into(dProfileImage);
        }

        profileDialog.show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_logout:
                logout();
                break;
            case R.id.iv_detailProfile:
                showProfileDialog();
                break;
            case R.id.iv_editProfile:
                navigateToEditProfileActivity();
                break;
            case R.id.tv_userName:
                navigateToEditProfileActivity();
                break;
            case R.id.btn_transaction:
                transaction.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                transactionHistory.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                loadData(false);
                break;
            case R.id.btn_transactionHistory:
                transactionHistory.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                transaction.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                loadData(true);
                break;
        }
    }
}