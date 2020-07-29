package com.wordpress.myselfnikunj.cofighter.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wordpress.myselfnikunj.cofighter.MainActivity;
import com.wordpress.myselfnikunj.cofighter.Model.CountryNamesModel;
import com.wordpress.myselfnikunj.cofighter.R;
import com.wordpress.myselfnikunj.cofighter.RecyclerClickListener;
import com.wordpress.myselfnikunj.cofighter.UpdatesFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class CountryRecyclerViewAdapter extends RecyclerView.Adapter<CountryRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private List<CountryNamesModel> countryNamesModelList;

    public CountryRecyclerViewAdapter(Context context, List<CountryNamesModel> countryNamesModelList) {
        this.context = context;
        this.countryNamesModelList = countryNamesModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.countrynameslist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(countryNamesModelList.get(position).getFlag())
                .into(holder.category_image);
        holder.txt_category_name.setText(countryNamesModelList.get(position).getCountry());
        holder.setListener(new RecyclerClickListener() {
            @Override
            public void onItemClickListener(View view, int pos) {
                Bundle position = new Bundle();
                position.putInt("position", pos);
                UpdatesFragment.fromAffectedCountriesFragment = false;
                Navigation.findNavController(view).navigate(R.id.action_updatesFragment_to_countryDetailsFragment,position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return countryNamesModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Unbinder unbinder;

        @BindView(R.id.txt_category_name)
        TextView txt_category_name;
        @BindView(R.id.category_image)
        CircleImageView category_image;

        RecyclerClickListener listener;

        public void setListener(RecyclerClickListener listener) {
            this.listener = listener;
        }

        //private Context context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onItemClickListener(view, getAdapterPosition());
        }

    }
}
