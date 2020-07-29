package com.wordpress.myselfnikunj.cofighter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wordpress.myselfnikunj.cofighter.Model.CountryNamesModel;
import com.wordpress.myselfnikunj.cofighter.Model.IndianStatesNamesModel;
import com.wordpress.myselfnikunj.cofighter.R;

import java.util.List;

public class IndianStatesListAdapter extends ArrayAdapter<IndianStatesNamesModel> {

    private Context context;
    private List<IndianStatesNamesModel> indianStatesNamesModelList;

    public IndianStatesListAdapter(@NonNull Context context,List<IndianStatesNamesModel> indianStatesNamesModelList) {
        super(context, R.layout.stateitem, indianStatesNamesModelList);
        this.context = context;
        this.indianStatesNamesModelList = indianStatesNamesModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stateitem, null, true);
        TextView stateName = view.findViewById(R.id.stateName);
        TextView stateTotalCases = view.findViewById(R.id.stateTotalCases);
        TextView stateRecoveredCases = view.findViewById(R.id.stateRecoveredCases);
        TextView stateActiveCases = view.findViewById(R.id.stateActiveCases);

//        ImageView imageView = view.findViewById(R.id.countryImageFlag);
//
//        tvCountryName.setText(countryModelListFiltered.get(position).getCountry());
//        Glide.with(context).load(countryModelListFiltered.get(position).getFlag()).into(imageView);

        stateName.setText(indianStatesNamesModelList.get(position).getName());
        stateTotalCases.setText(indianStatesNamesModelList.get(position).getCases());
        stateRecoveredCases.setText(indianStatesNamesModelList.get(position).getRecovered());
        stateActiveCases.setText(indianStatesNamesModelList.get(position).getActive());

        return view;
    }
//    @Override
//    public int getCount() {
//        return indianStatesNamesModelList.size();
//    }
//
//    @Nullable
//    @Override
//    public IndianStatesNamesModel getItem(int position) {
//        return indianStatesNamesModelList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position)               {
//        return position;
//    }
}
