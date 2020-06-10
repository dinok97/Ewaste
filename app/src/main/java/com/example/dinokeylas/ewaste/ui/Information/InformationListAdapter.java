package com.example.dinokeylas.ewaste.ui.Information;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.InformationModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InformationListAdapter extends RecyclerView.Adapter<InformationListAdapter.InformationListViewHolder> {

    FirebaseFirestore db;

    private Context context;
    private List <InformationModel> informationList;
    private InformationModel informationModel;

    public InformationListAdapter(Context context, List<InformationModel> informationList) {
        this.context = context;
        this.informationList = informationList;
    }

    @Override
    public InformationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InformationListViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_information_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(InformationListViewHolder holder, int position) {
        informationModel = informationList.get(position);

        Date date = informationModel.getiDate();
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String tgl = ""+simpleDateFormat.format(date);

        holder.tanggal.setText(tgl);
        holder.penulis.setText(informationModel.getiAuthor());
        holder.judul.setText(informationModel.getiTittle());
        Glide.with(context)
                .load(informationModel.getiInfoImageUrl())
                .into(holder.gambar);
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    public class InformationListViewHolder extends RecyclerView.ViewHolder{

        TextView tanggal, penulis, judul;
        ImageView gambar;
        CardView cardView;

        public InformationListViewHolder(View itemView) {
            super(itemView);

            tanggal = itemView.findViewById(R.id.tv_informationDate);
            penulis = itemView.findViewById(R.id.tv_informationAuthor);
            judul = itemView.findViewById(R.id.tv_informationTittle);
            gambar = itemView.findViewById(R.id.iv_informationImage);
            cardView = itemView.findViewById(R.id.cv_itemView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InformationModel information = informationList.get(getAdapterPosition());
                    Intent intent = new Intent (context, InformationContentActivity.class);
                    intent.putExtra("information", information);
                    context.startActivity(intent);
                }
            });
        }
    }
}
