package com.example.dinokeylas.ewaste.ui.Sell;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.PriceModel;
import com.example.dinokeylas.ewaste.model.TranModel;
import com.example.dinokeylas.ewaste.model.TypeOfWasteModel;
import com.example.dinokeylas.ewaste.ui.Home.HomeActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_PRICE;
import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.COLLECTION_TRANSACTION;
import static com.example.dinokeylas.ewaste.util.Constant.COLLECTION.UN_PROCESS;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //constant variable
    private static final String TAG = "MapsActivity";

    //private static final String TAGG = "TypeOfWaste";
    private static final String GARBAGE_NAME = "garbageName";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TRANSACTION_TYPE = "Menabung";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    public static final String SOURCES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;

    //variable
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private TranModel transactionData;
    private ArrayList<TypeOfWasteModel> typeOfWasteModels;
    private TypeOfWasteAdapter listAdapter;
    private ArrayAdapter<String> weightAdapter;
    private String userId;
    private String userEmail;
    private Date date;
    private String selectedWaste = "-";
    private double weight;
    private String placeName;
    private GeoPoint location = new GeoPoint(0, 0);
    private Double[] weightValueDouble = new Double[]{
            20.0,
            25.0,
            30.0,
            35.0,
            40.0,
            45.0,
            50.0,
            55.0,
            60.0,
            65.0,
            70.0,
            75.0,
            80.0,
            85.0,
            90.0,
            95.0,
            100.0,
            105.0,
            110.0,
            115.0,
            120.0,
            125.0,
            130.0,
            135.0,
            200.0,
    };
    private String[] weightValueString = new String[]{
            "20 Kg",
            "25 Kg",
            "30 Kg",
            "35 Kg",
            "40 Kg",
            "45 Kg",
            "50 Kg",
            "55 Kg",
            "60 Kg",
            "65 Kg",
            "70 Kg",
            "75 Kg",
            "80 Kg",
            "85 Kg",
            "90 Kg",
            "95 Kg",
            "100 Kg",
            "105 Kg",
            "110 Kg",
            "115 Kg",
            "120 Kg",
            "125 Kg",
            "130 Kg",
            "135 Kg",
            "<135 Kg"
    };

    //item view
    LinearLayout btnShowTypeOfWaste, btnShowWeight, helper, backgroundTransparent, mPlacePicker;
    CardView typeOfWasteLayout, weightLayout;
    Button finish, selectButton;
    ImageView back;
    ListView wasteList, weightList;
    TextView selectedWeight, tvLocation, selectTypeOfWaste;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        back = (ImageView) findViewById(R.id.btn_backToHome);
        btnShowTypeOfWaste = (LinearLayout) findViewById(R.id.ll_btn_typeOfWaste);
        typeOfWasteLayout = (CardView) findViewById(R.id.cv_typeOfWaste);
        btnShowWeight = (LinearLayout) findViewById(R.id.ll_btn_weight);
        weightLayout = (CardView) findViewById(R.id.cv_weight);
        helper = (LinearLayout) findViewById(R.id.ll_help);
        backgroundTransparent = (LinearLayout) findViewById(R.id.ll_backgroundTransparent);
        weightList = (ListView) findViewById(R.id.lv_weight);
        selectedWeight = (TextView) findViewById(R.id.tv_selectedWeight);
        selectTypeOfWaste = (TextView) findViewById(R.id.tv_selectedTypeOfWaste);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        finish = (Button) findViewById(R.id.btn_finish);
        selectButton = (Button) findViewById(R.id.btn_finishSelectCheckBox);
        mPlacePicker = (LinearLayout) findViewById(R.id.ll_searchLocation);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        assert mUser != null;
        userId = mUser.getUid();
        userEmail = mUser.getEmail();

        getLocationPermission();
        navigationView();
        setWasteListLayout();
        searchLocationClick();
        onSelectedGarbageChecked();
        onWasteWeightSelected();
        saveSellData();
        backClick();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Toast.makeText(this, "Maps berhasil dimuat", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");

        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //mMap.setMyLocationEnabled(true);
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions from phone");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(
                this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation : start");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete : found location");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "onComplete : location is null");
                            Toast.makeText(MapsActivity.this, "enable to find location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "catch message : " + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "move camera : moving camera to: lat:" + latLng.latitude + " long:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)));
        marker.setVisible(true);
    }

    private void searchLocationClick() {
        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage());
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("%s\n%s", place.getName(), place.getAddress());
                placeName = String.format("%s\n%s", place.getName(), place.getAddress());
                tvLocation.setText(String.valueOf(toastMsg));
                moveCamera(place.getLatLng(), DEFAULT_ZOOM);
                location = new GeoPoint(place.getLatLng().latitude, place.getLatLng().longitude);
            }
        }
    }

    private void onWasteWeightSelected() {
        weightAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                weightValueString);
        weightList.setAdapter(weightAdapter);
        weightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String weightText = weightValueString[position];
                weight = weightValueDouble[position];
                showSelectedWeight(weightText);
            }
        });
    }

    private void showSelectedWeight(String text) {
        hideListOfWeight();
        backgroundTransparent.setVisibility(View.GONE);
        selectedWeight.setText(text);
    }

    private void setWasteListLayout() {
        db = FirebaseFirestore.getInstance();
        typeOfWasteModels = new ArrayList<>();

        db.collection(COLLECTION_PRICE).orderBy(GARBAGE_NAME, Query.Direction.ASCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                PriceModel p = d.toObject(PriceModel.class);
                                String name = p.getGarbageName();
                                typeOfWasteModels.add(new TypeOfWasteModel(name, false));
                            }
                            insertToListView(typeOfWasteModels);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//        wasteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TypeOfWasteModel model = (TypeOfWasteModel) parent.getItemAtPosition(position);
//                //Toast.makeText(getApplicationContext(), "Clicked on Row: " + model.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void insertToListView(ArrayList<TypeOfWasteModel> mWasteList) {
        listAdapter = new TypeOfWasteAdapter(this, R.layout.row_item_waste, mWasteList);
        wasteList = findViewById(R.id.lv_typeOfWaste);
        wasteList.setAdapter(listAdapter);
    }

    private void onSelectedGarbageChecked() {
        selectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String selectedWasteFinal;
                String tittle = "Jenis sampah Anda:\n";
                selectedWaste = "-";
                int flag = 0;

                ArrayList<TypeOfWasteModel> wasteList = listAdapter.getWasteList();

                //get first garbage name
                for (int i = 0; i < wasteList.size(); i++) {
                    TypeOfWasteModel model = wasteList.get(i);
                    if (model.isChecked()) {
                        selectedWaste = model.getName();
                        break;
                    }
                }

                // continue after first garbage name
                for (int i = 0; i < wasteList.size(); i++) {
                    TypeOfWasteModel model = wasteList.get(i);
                    if (model.isChecked()) {
                        if (flag == 0) {
                            flag = 1;
                        } else {
                            selectedWaste = selectedWaste + ", " + model.getName();
                        }
                    }
                }
                selectedWasteFinal = tittle + selectedWaste;
                hideListOfWaste();
                backgroundTransparent.setVisibility(View.GONE);
                selectTypeOfWaste.setText(selectedWasteFinal);
            }
        });
    }

    private void navigationView() {
        btnShowTypeOfWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListOfWaste();
                hideListOfWeight();
            }
        });

        btnShowWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListOfWeight();
                hideListOfWaste();
            }
        });

        backgroundTransparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideListOfWeight();
                hideListOfWaste();
                backgroundTransparent.setVisibility(View.GONE);
            }
        });
    }

    private void showListOfWaste() {
        typeOfWasteLayout.setVisibility(View.VISIBLE);
        backgroundTransparent.setVisibility(View.VISIBLE);
    }

    private void hideListOfWaste() {
        typeOfWasteLayout.setVisibility(View.GONE);
    }

    private void showListOfWeight() {
        weightLayout.setVisibility(View.VISIBLE);
        helper.setVisibility(View.VISIBLE);
        backgroundTransparent.setVisibility(View.VISIBLE);
    }

    private void hideListOfWeight() {
        weightLayout.setVisibility(View.GONE);
        helper.setVisibility(View.GONE);
    }


    private void saveSellData() {
        final String message;
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideListOfWaste();
                hideListOfWeight();
                backgroundTransparent.setVisibility(View.GONE);
                if (location.getLatitude() == 0 && location.getLongitude() == 0) {
                    showAlertDialog("Anda belum menentukan lokasi");
                } else if (selectedWaste.equalsIgnoreCase("-")) {
                    showAlertDialog("Anda belum mengisikan jenis sampah");
                } else if (weight == 0) {
                    showAlertDialog("Anda belum mengisikan berat sampah");
                }
                // if all data is not null
                else {
                    String formatMessage = String.format("lokasi:\n%s\n\njenis sampah:\n%s\n\ntotal berat:\n%s Kg",
                            placeName, selectedWaste, weight);
                    AlertDialog alert = new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("Detail Informasi")
                            .setMessage(formatMessage)
                            .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    uploadToTransactionCollection();
                                }
                            })
                            .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //to do here
                                }
                            })
                            .show();
                }
            }
        });
    }

//    private void uploadToSellCollection() {
//        date = Calendar.getInstance().getTime();
//        sellData = new SellModel(
//                userId,
//                userEmail,
//                date,
//                selectedWaste,
//                weight,
//                location,
//                "unprocessed");
//        db.collection(COLLECTION_SELL).add(sellData)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        //upload data to Transaction Collection
//                        uploadToTransactionCollection();
//                        Toast.makeText(MapsActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
//                        AlertDialog alert = new AlertDialog.Builder(MapsActivity.this)
//                                .setTitle("Perhatian")
//                                .setMessage("Jual sampah lagi?")
//                                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        selectedWaste = "-";
//                                        weight = 0;
//                                        location = new GeoPoint(0, 0);
//                                        selectedWeight.setText(null);
//                                        selectTypeOfWaste.setText(null);
//                                        tvLocation.setText(null);
//                                    }
//                                })
//                                .setNegativeButton("LAIN KALI", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        finish();
//                                    }
//                                })
//                                .show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MapsActivity.this, "Gagal menyimpan", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    private void uploadToTransactionCollection() {
        //get random code
        String transactionCode = generateString(new Random(), SOURCES, 6);

        date = Calendar.getInstance().getTime();
        transactionData = new TranModel(
                transactionCode,
                userId,
                userEmail,
                TRANSACTION_TYPE,
                date,
                selectedWaste,
                weight,
                placeName,
                location,
                0,
                "default",
                false,
                UN_PROCESS);
        db.collection(COLLECTION_TRANSACTION).add(transactionData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d("Tran collection: ", "success");
                        Toast.makeText(MapsActivity.this, "Data berhasil diproses", Toast.LENGTH_SHORT).show();
                        AlertDialog alert = new AlertDialog.Builder(MapsActivity.this)
                                .setTitle("Perhatian")
                                .setMessage("Jual sampah lagi?")
                                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        selectedWaste = "-";
                                        weight = 0;
                                        location = new GeoPoint(0, 0);
                                        selectedWeight.setText(null);
                                        selectTypeOfWaste.setText(null);
                                        tvLocation.setText(null);
                                    }
                                })
                                .setNegativeButton("LAIN KALI", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                });
    }

    private void showAlertDialog(String message) {
        AlertDialog alert = new AlertDialog.Builder(MapsActivity.this)
                .setTitle("Peringatan")
                .setMessage(message)
                .setPositiveButton("mengerti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void backClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MapsActivity.this, HomeActivity.class);
                startActivity(in);
            }
        });
    }

    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }
}

