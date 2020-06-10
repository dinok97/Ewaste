package com.example.dinokeylas.ewaste.ui.Help;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dinokeylas.ewaste.R;

public class HelpFragment extends Fragment {
    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Klik link untuk informasi lebih lanjut", Toast.LENGTH_SHORT).show();
        View view= inflater.inflate(R.layout.fragment_help, container, false);
        TextView web = (TextView) view.findViewById(R.id.tv_web);
        TextView instagram = (TextView) view.findViewById(R.id.tv_instagram);
        TextView facebook = (TextView) view.findViewById(R.id.tv_facebook);
        TextView address = (TextView) view.findViewById(R.id.tv_address);

        web.setMovementMethod(LinkMovementMethod.getInstance());
        instagram.setMovementMethod(LinkMovementMethod.getInstance());
        facebook.setMovementMethod(LinkMovementMethod.getInstance());
        address.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
