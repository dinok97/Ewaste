package com.example.dinokeylas.ewaste.ui.Sell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.dinokeylas.ewaste.R;
import com.example.dinokeylas.ewaste.model.TypeOfWasteModel;

import java.util.ArrayList;

public class TypeOfWasteAdapter extends ArrayAdapter<TypeOfWasteModel> {

    private ArrayList<TypeOfWasteModel> wasteList;
    private Context mContext;
    private TypeOfWasteModel wasteModel;

    public TypeOfWasteAdapter(Context context, int textViewResourceId, ArrayList<TypeOfWasteModel> wasteList) {
        super(context, textViewResourceId, wasteList);
        this.wasteList = new ArrayList<TypeOfWasteModel>();
        this.wasteList.addAll(wasteList);
        this.mContext = context;
    }

    public ArrayList<TypeOfWasteModel> getWasteList() {
        return wasteList;
    }

    private static class ViewHolder {
        TextView wasteName;
        CheckBox checkBox;
    }

    @Override
    public int getCount() {
        return wasteList.size();
    }

    @Override
    public TypeOfWasteModel getItem(int position) {
        return wasteList.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder = null;
        Log.v("Convert View", String.valueOf(position));

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert vi != null;
            convertView = vi.inflate(R.layout.row_item_waste, null, true);

            viewHolder = new ViewHolder();
            viewHolder.wasteName = convertView.findViewById(R.id.tv_wasteName);
            viewHolder.checkBox = convertView.findViewById(R.id.cb_checkBox);

            convertView.setTag(viewHolder);

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    TypeOfWasteModel model = (TypeOfWasteModel) cb.getTag();
                    model.setChecked(cb.isChecked());
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        wasteModel = wasteList.get(position);
        viewHolder.wasteName.setText(String.valueOf(wasteModel.getName()));
        viewHolder.checkBox.setChecked(wasteModel.isChecked());
        viewHolder.checkBox.setTag(wasteModel);

        return convertView;
    }
}
