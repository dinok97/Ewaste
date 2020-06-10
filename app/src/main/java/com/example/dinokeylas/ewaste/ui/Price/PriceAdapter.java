package com.example.dinokeylas.ewaste.ui.Price;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.PriceModel;

import java.util.List;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.PriceViewHolder> {

    private Context context;
    private List<PriceModel> priceList;
    private PriceModel price;

    public PriceAdapter(Context context, List<PriceModel> priceList) {
        this.context = context;
        this.priceList = priceList;
    }

    @Override
    public PriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_price, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(PriceViewHolder holder, int position) {
        price = priceList.get(position);

        String savingPriceText = "Rp "+String.valueOf(price.getSavingPrice())+" ";
        String directPriceText = "Rp "+String.valueOf(price.getDirectPrice())+" ";

        holder.garbageName.setText(price.getGarbageName());
        holder.garbagePriceDirect.setText(directPriceText);
        holder.garbagePriceSaving.setText(savingPriceText);
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class PriceViewHolder extends RecyclerView.ViewHolder {

        TextView garbageName, garbagePriceDirect, garbagePriceSaving;

        public PriceViewHolder(View itemView) {
            super(itemView);
            garbageName = itemView.findViewById(R.id.tv_garbageName);
            garbagePriceDirect = itemView.findViewById(R.id.tv_garbagePriceDirect);
            garbagePriceSaving = itemView.findViewById(R.id.tv_garbagePriceSaving);
        }
    }
}
