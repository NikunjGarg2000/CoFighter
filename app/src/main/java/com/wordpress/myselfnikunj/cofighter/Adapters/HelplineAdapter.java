package com.wordpress.myselfnikunj.cofighter.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wordpress.myselfnikunj.cofighter.Model.HelplineModel;
import com.wordpress.myselfnikunj.cofighter.R;

import java.util.List;

public class HelplineAdapter extends ArrayAdapter<HelplineModel> {

    private Context context;
    private List<HelplineModel> helplineModelList;

    public HelplineAdapter(@NonNull Context context, List<HelplineModel> helplineModelList) {
        super(context, R.layout.helplineitem, helplineModelList);
        this.context = context;
        this.helplineModelList = helplineModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.helplineitem, null, true);

        TextView stateName = view.findViewById(R.id.stateName);
        final TextView stateHelpline = view.findViewById(R.id.stateHelpline);
        TextView callNow = view.findViewById(R.id.callNow);

        stateName.setText(helplineModelList.get(position).getStateName());
        stateHelpline.setText(helplineModelList.get(position).getNumber());

        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = stateHelpline.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                getContext().startActivity(intent);

            }
        });

        return view;
    }
}
