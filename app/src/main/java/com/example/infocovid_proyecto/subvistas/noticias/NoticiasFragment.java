package com.example.infocovid_proyecto.subvistas.noticias;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.libraries.NetUtilities;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.models.Noticia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoticiasFragment extends Fragment {

    private Spinner atOpciones;
    private AlertDialog dialog;
    private View view;

    private List<Noticia> noticias = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_noticias, container, false);
        String[] opciones = getResources().getStringArray(R.array.opciones);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.opciones,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        settings(view, adapter);

        MainActivity.navigationView.setCheckedItem(R.id.nav_noticias);
        return view;
    }

    private void settings(View view, ArrayAdapter<CharSequence> adapter) {

        atOpciones = (Spinner) view.findViewById(R.id.spOpciones);
        atOpciones.setAdapter(adapter);


        atOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getNoticias("es");
                        break;
                    case 1:
                        getNoticias("en");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getNoticias(String language){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("");
        builder.setMessage("Cargando noticias");
        dialog= builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        String url="http://192.168.0.15:3000/api/news/"+language;
        noticias.clear();
        new Noticias().execute(url);
    }


    public class Noticias extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return NetUtilities.getCases(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            NoticiaAdapter adapter;
            ViewPager viewPager;
            ArgbEvaluator argbEvaluator = new ArgbEvaluator();
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject response = jsonObject.getJSONObject("response");
                JSONArray articles = response.getJSONArray("articles");
                for(int i=0;i<articles.length();i++){
                    JSONObject article =articles.getJSONObject(i);
                    JSONObject source = article.getJSONObject("source");
                    String title = article.getString("title");
                    String description = article.getString("description");
                    String url = article.getString("url");
                    String publishedAt= article.getString("publishedAt");
                    String publicado = publishedAt.split("T")[0];
                    String img = article.getString("urlToImage");
                    String fuente = source.getString("name");
                    Noticia obNoticia = new Noticia(img,url,title,description,publicado,fuente);
                    noticias.add(obNoticia);
                }

                adapter = new NoticiaAdapter(noticias,getContext());
                viewPager = view.findViewById(R.id.viewNoticias);
                viewPager.setAdapter(adapter);
                viewPager.setPadding(130,0,130,0);

            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(getContext(), "Problemas internos, espere...", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }

}