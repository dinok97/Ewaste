package com.example.dinokeylas.ewaste.ui.Message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.MessageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_MESSAGE;


public class MessageFragment extends Fragment {

    private String userEmail = "email";
    private List<MessageModel> messageList;
    private MessageAdapter messageAdapter;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvNoMessage;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        //initialize database
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

//      get user id
        assert mUser != null;
        userEmail = mUser.getEmail();

        tvNoMessage = view.findViewById(R.id.tv_no_message);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = new RecyclerView(this.getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_message);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        messageList = new ArrayList<>();

        //setting the adapter
        messageAdapter = new MessageAdapter(getContext(), messageList);
        recyclerView.setAdapter(messageAdapter);

        getMessage();

        return view;
    }

    private void getMessage(){
        db.collection(COLLECTION_MESSAGE)
                .whereEqualTo("receiver", userEmail)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        MessageModel p = d.toObject(MessageModel.class);
                        p.setMessageId(d.getId());
                        messageList.add(p);
                    }
                    progressBar.setVisibility(View.GONE);
                    sortMessageList();
                    messageAdapter.notifyDataSetChanged();
                } else {
                    progressBar.setVisibility(View.GONE);
                    tvNoMessage.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void sortMessageList() {
        // selection sort
        for (int i = 0; i < messageList.size(); i++) {
            int index = i;
            for (int j = i + 1; j < messageList.size(); j++) {
                if (messageList.get(i).getSendDate().compareTo(messageList.get(j).getSendDate()) < 0) {
                    index = j;
                }
            }
            MessageModel model = messageList.get(index);
            messageList.set(index, messageList.get(i));
            messageList.set(i, model);
        }
    }
}
