package com.example.dinokeylas.ewaste.ui.Home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dinokeylas.ewaste.HospitalActivity;
import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.ui.Information.InformationActivity;
import com.example.dinokeylas.ewaste.ui.Price.PriceActivity;
import com.example.dinokeylas.ewaste.ui.Sell.MapsActivity;
import com.example.dinokeylas.ewaste.ui.SavingBook.SavingBookActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class HomeFragment extends Fragment implements View.OnClickListener{

    public static HomeFragment newInstance() {
        HomeFragment m_fragment = new HomeFragment();
        return m_fragment;
    }

    Intent intent = new Intent();

    private static final String TAG = "GoogleMap";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private CardView request, savingBook, priceList, information, helpp, hospital;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        //pendefinisian cardView
        priceList =  view.findViewById(R.id.cv_priceList);
        savingBook = view.findViewById(R.id.cv_savingBook);
        request = view.findViewById(R.id.cv_request);
        information = view.findViewById(R.id.cv_information);
        hospital = view.findViewById(R.id.cv_hospital);
        helpp = view.findViewById(R.id.explan);

        //penerapanInten
        priceList.setOnClickListener(this);
        savingBook.setOnClickListener(this);
        information.setOnClickListener(this);
        hospital.setOnClickListener(this);
        //helpp.setOnClickListener(this);

        if (isServiceOK()){
            init();
        }

        return view;
    }

    private void init(){
        request.setOnClickListener(this);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent (getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean isServiceOK(){
        Log.d(TAG, "is Service OK? checking google service version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "service is OK");
            return true;
        }

        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(getActivity(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cv_priceList : intent = new Intent(getActivity(), PriceActivity.class); startActivity(intent); break;
            case R.id.cv_savingBook : intent = new Intent(getActivity(), SavingBookActivity.class); startActivity(intent); break;
            case R.id.cv_information : intent = new Intent(getActivity(), InformationActivity.class); startActivity(intent); break;
            case R.id.cv_hospital : intent = new Intent(getActivity(), HospitalActivity.class); startActivity(intent); break;
            default: break;
        }
    }
}
