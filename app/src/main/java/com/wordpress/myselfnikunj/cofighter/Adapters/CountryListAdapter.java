package com.wordpress.myselfnikunj.cofighter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wordpress.myselfnikunj.cofighter.AffectedCountriesFragment;
import com.wordpress.myselfnikunj.cofighter.Model.CountryNamesModel;
import com.wordpress.myselfnikunj.cofighter.R;
import com.wordpress.myselfnikunj.cofighter.UpdatesFragment;

import java.util.ArrayList;
import java.util.List;

public class CountryListAdapter extends ArrayAdapter<CountryNamesModel> {

    private Context context;
    private List<CountryNamesModel> countryModelList;
    private List<CountryNamesModel> countryModelListFiltered;

    public CountryListAdapter( Context context, List<CountryNamesModel> countryModelList) {
        super(context, R.layout.countryitem, countryModelList);
        this.context = context;
        this.countryModelList = countryModelList;
        this.countryModelListFiltered = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countryitem, null, true);
        TextView tvCountryName = view.findViewById(R.id.countryName);
        ImageView imageView = view.findViewById(R.id.countryImageFlag);

        tvCountryName.setText(countryModelListFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelListFiltered.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelListFiltered.size();
    }

    @Nullable
    @Override
    public CountryNamesModel getItem(int position) {
        return countryModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position)               {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
         Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null
                        || charSequence.length() == 0
                ) {
                    filterResults.count = countryModelList.size();
                    filterResults.values = countryModelList;
                } else {
                    List<CountryNamesModel> resultsModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();

                    for (CountryNamesModel itemsModel: countryModelList) {
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                countryModelListFiltered = (List<CountryNamesModel>) results.values;
                AffectedCountriesFragment.countryNamesModelList = (List<CountryNamesModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
