package com.example.infocovid_proyecto.subvistas.noticias;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.Noticia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NoticiaAdapter extends PagerAdapter {

    private List<Noticia> noticias;
    private LayoutInflater layoutInflater;
    private Context context;

    public NoticiaAdapter(List<Noticia> noticias,  Context context) {
        this.noticias = noticias;
        this.context = context;
    }


    @Override
    public int getCount() {
        return noticias.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.noticias_card,container,false);
        CardView cardView;
        ImageView imageView;
        TextView title,desc,fuente;
        cardView = view.findViewById(R.id.notiCard);
        imageView = view.findViewById(R.id.imNoticia);
        title = view.findViewById(R.id.notiTitle);
        desc = view.findViewById(R.id.notiDesc);
        fuente= view.findViewById(R.id.notiFuente);
        if(!noticias.get(position).getImg().isEmpty()){
            Picasso.with(context).load(noticias.get(position).getImg()).into(imageView);
        }
        title.setText(noticias.get(position).getTitle());
        desc.setText(noticias.get(position).getDescription());
        fuente.setText(noticias.get(position).getSource() + " - " +noticias.get(position).getPublishedAt());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse(noticias.get(position).getUrl());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUrl);
                view.getContext().startActivity(launchBrowser);
            }
        });
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
