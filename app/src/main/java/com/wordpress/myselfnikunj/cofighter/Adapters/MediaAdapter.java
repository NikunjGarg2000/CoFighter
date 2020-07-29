package com.wordpress.myselfnikunj.cofighter.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.wordpress.myselfnikunj.cofighter.Model.MediaModel;
import com.wordpress.myselfnikunj.cofighter.R;
import com.wordpress.myselfnikunj.cofighter.WebActivity;

import java.util.ArrayList;
import java.util.List;

public class  MediaAdapter extends ArrayAdapter<MediaModel> {

    private Context context;
    private List<MediaModel> mediaModelList;

    public MediaAdapter(@NonNull Context context,List<MediaModel> mediaModelList) {
        super(context, R.layout.mediaitem, mediaModelList);
        this.context = context;
        this.mediaModelList = mediaModelList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mediaitem, null, true);

        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView updatedTextView = (TextView) view.findViewById(R.id.updatedTextView);
        ImageView urlImageView = (ImageView) view.findViewById(R.id.urlImageView);

        titleTextView.setText(mediaModelList.get(position).getTitle());
        descriptionTextView.setText(mediaModelList.get(position).getDescription());
        updatedTextView.setText("Published: " + mediaModelList.get(position).getPublishedAt());
        Glide.with(context).load(mediaModelList.get(position).getImageLink()).into(urlImageView);

        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra("link", mediaModelList.get(position).getUrl());
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
