package com.example.dinokeylas.ewaste.ui.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.TranModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder> {

    private String DATE_FORMAT = "dd/MM/yyyy";

    private Context context;
    private List<TranModel> transactionList;
    private TranModel tranModel;

    public TransactionListAdapter(Context context, List<TranModel> transactionList) {
        this.context = context;
        this.transactionList = transactionList;
    }

    @Override
    public TransactionListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TransactionListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transactions, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(TransactionListViewHolder holder, int position) {
        tranModel = transactionList.get(position);

        Date date;
        String sDate, typeOfTran, nominal;

        date = tranModel.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        sDate = "" + simpleDateFormat.format(date);
        typeOfTran = tranModel.getTransactionType();
        nominal = "Rp " + tranModel.getNominal();

        holder.datee.setText(sDate);
        holder.tranType.setText(typeOfTran);
        holder.nominal.setText(nominal);
        holder.cardView.setOnClickListener(onClickListener(position));
    }

    private View.OnClickListener onClickListener (final int position){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date;
                String tranCode, tranType, sDate, garbageName, nominals, isDone, garbageWeight, location;

                date = transactionList.get(position).getDate();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
                sDate = "" + simpleDateFormat.format(date);
                tranCode = transactionList.get(position).getTransactionCode();
                tranType = transactionList.get(position).getTransactionType();
                garbageName = transactionList.get(position).getGarbageName();
                nominals = "Rp "+ transactionList.get(position).getNominal();
                garbageWeight = transactionList.get(position).getGarbageWeight()+" Kg";
                location = transactionList.get(position).getLocation();
                if (transactionList.get(position).isDone()){
                    isDone = "Selesai";
                }
                else {
                    isDone = "Dalam Proses";
                }

                String formatMessage =
                        String.format("Kode Transaksi:\n%s\n\nJenis Transaksi:\n%s\n\nWaktu Transaksi:\n%s\n\nJenis Sampah:\n%s\n\nTotal Berat:\n%s\n\nNominal:\n%s\n\nLokasi:\n%s\n\nStatus:\n%s\n\n",
                        tranCode,
                        tranType,
                        sDate,
                        garbageName,
                        garbageWeight,
                        nominals,
                        location,
                        isDone);

                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Detail Transaksi")
                        .setMessage(formatMessage)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //to do here
                            }
                        })
                        .show();
            }
        };
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class TransactionListViewHolder extends RecyclerView.ViewHolder {
        TextView nominal, datee, tranType;
        CardView cardView;

        public TransactionListViewHolder(View itemView) {
            super(itemView);
            tranType = (TextView) itemView.findViewById(R.id.tv_transaction_type);
            datee = (TextView) itemView.findViewById(R.id.tv_time_of_transaction);
            nominal = (TextView) itemView.findViewById(R.id.tv_transaction_nominal);
            cardView = (CardView) itemView.findViewById(R.id.cv_transactionItem);
        }
    }
}