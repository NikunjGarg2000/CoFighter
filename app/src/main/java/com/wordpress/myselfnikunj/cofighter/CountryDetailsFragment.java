package com.wordpress.myselfnikunj.cofighter;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class CountryDetailsFragment extends Fragment {

    private int positionCountry;
    TextView tvCountry, tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTodayDeaths, tvTotalDeaths;
    ImageView flagImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_details, container, false);

        tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        tvCases = (TextView) view.findViewById(R.id.tvCases);
        tvRecovered = (TextView) view.findViewById(R.id.tvRecovered);
        tvCritical = (TextView) view.findViewById(R.id.tvCritical);
        tvActive = (TextView) view.findViewById(R.id.tvActive);
        tvTodayCases = (TextView) view.findViewById(R.id.tvTodayCases);
        tvTodayDeaths = (TextView) view.findViewById(R.id.tvTodayDeaths);
        tvTotalDeaths = (TextView) view.findViewById(R.id.tvDeaths );
        flagImageView = (ImageView) view.findViewById(R.id.flagImageView);

        positionCountry = getArguments().getInt("position");

        if (UpdatesFragment.fromAffectedCountriesFragment) {
            Glide.with(getContext()).load(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getFlag()).into(flagImageView);
            tvCountry.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getCountry());
            tvCases.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getCases());
            tvRecovered.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getRecovered());
            tvCritical.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getCritical());
            tvActive.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getActive());
            tvTodayCases.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getTodayCases());
            tvTodayDeaths.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getTodayDeaths());
            tvTotalDeaths.setText(AffectedCountriesFragment.countryNamesModelList.get(positionCountry).getDeaths());
        } else {
            Glide.with(getContext()).load(UpdatesFragment.countryNamesModelList.get(positionCountry).getFlag()).into(flagImageView);
            tvCountry.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getCountry());
            tvCases.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getCases());
            tvRecovered.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getRecovered());
            tvCritical.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getCritical());
            tvActive.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getActive());
            tvTodayCases.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getTodayCases());
            tvTodayDeaths.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getTodayDeaths());
            tvTotalDeaths.setText(UpdatesFragment.countryNamesModelList.get(positionCountry).getDeaths());

        }

        return view;
    }
}