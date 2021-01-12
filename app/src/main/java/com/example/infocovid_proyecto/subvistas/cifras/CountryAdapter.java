package com.example.infocovid_proyecto.subvistas.cifras;

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

import com.example.infocovid_proyecto.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends ArrayAdapter<Country> {

    private Country countryEntry;
    private TextView autoItem;
    private ImageView countryIcon;
    private List<Country> countryEntryList = new ArrayList<Country>();

    public CountryAdapter(Context context, List<Country> paises){
        super(context,0,paises);
        this.countryEntryList = new ArrayList<>(paises);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return countryFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_country,parent,false);
        }
        autoItem = convertView.findViewById(R.id.txt_style_item);
        countryIcon = convertView.findViewById(R.id.img_style_item);
        Country pais = getItem(position);
        if(pais!=null){
            autoItem.setText(pais.getCountry());
            Picasso.with(getContext()).load(pais.getFlag()).into(countryIcon);
        }
        return convertView;
    }

    private Filter countryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults  results = new FilterResults();
            List<Country> suggestion = new ArrayList<>();
            if(constraint==null || constraint.length()==0 || constraint.toString().trim().length()==0){
                suggestion.addAll(countryEntryList);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Country item : countryEntryList){
                    if(item.getCountry().toLowerCase().trim().contains(filterPattern)){
                        suggestion.add(item);
                    }
                }
            }
            results.values=suggestion;
            results.count = suggestion.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return((Country) resultValue).getCountry();
        }
    };

}
