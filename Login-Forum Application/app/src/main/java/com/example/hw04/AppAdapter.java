package com.example.hw04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// INCLASS 05 ASSIGNMENT
// LEROY PHOMMA AND BINLY KEONAKHONE

import org.w3c.dom.Text;

import java.util.List;

public class AppAdapter extends ArrayAdapter<DataServices.App> {


    public AppAdapter(@NonNull Context context, int resource, @NonNull List<DataServices.App> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.app_Name);
            viewHolder.textViewArtistName = convertView.findViewById(R.id.artist_Name);
            viewHolder.textViewReleaseDate = convertView.findViewById(R.id.release_Date);
            convertView.setTag(viewHolder);
        }

        DataServices.App dataServices = getItem(position);
        ViewHolder viewHolder = (ViewHolder)convertView.getTag();

        viewHolder.textViewName.setText(dataServices.name);
        viewHolder.textViewArtistName.setText(dataServices.artistName);
        viewHolder.textViewReleaseDate.setText(dataServices.releaseDate);

        return convertView;
    }



    private static class ViewHolder{
        TextView textViewName;
        TextView textViewArtistName;
        TextView textViewReleaseDate;
    }
}