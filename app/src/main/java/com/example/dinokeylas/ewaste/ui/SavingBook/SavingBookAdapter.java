package com.example.dinokeylas.ewaste.ui.SavingBook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.SavingBookModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SavingBookAdapter extends RecyclerView.Adapter<SavingBookAdapter.SavingBookViewHolder> {

    private Context context;
    private List<SavingBookModel> list;
    private SavingBookModel savingBook;

    public SavingBookAdapter(Context context, List<SavingBookModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SavingBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SavingBookViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_saving_book_activity, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(SavingBookViewHolder holder, int position) {
        savingBook = list.get(position);

        Date date = savingBook.getDate();
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String tgl = ""+simpleDateFormat.format(date);

        String str = ""+(position+1);
        holder.nomor.setText(str);
        holder.tanggal.setText(tgl);
        holder.debet.setText(String.valueOf(savingBook.getDebit()));
        holder.kredit.setText(String.valueOf(savingBook.getCredit()));
        holder.saldo.setText(String.valueOf(savingBook.getBalance()));
        holder.keterangan.setText(String.valueOf(savingBook.getExplanation()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SavingBookViewHolder extends RecyclerView.ViewHolder {
        TextView nomor, tanggal, debet, kredit, saldo, keterangan;

        public SavingBookViewHolder(View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.tv_nomor);
            tanggal = itemView.findViewById(R.id.tv_tanggal);
            debet = itemView.findViewById(R.id.tv_debet);
            kredit = itemView.findViewById(R.id.tv_kredit);
            saldo = itemView.findViewById(R.id.tv_saldo);
            keterangan = itemView.findViewById(R.id.tv_keterangan);
        }
    }
}
