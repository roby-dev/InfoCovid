package com.example.infocovid_proyecto.subvistas.cifras;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocovid_proyecto.models.Country;
import com.example.infocovid_proyecto.subvistas.MainActivity;
import com.example.infocovid_proyecto.libraries.NetUtilities;
import com.example.infocovid_proyecto.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class CifrasFragment extends Fragment {

    private ProgressBar progressNuevos;
    private ProgressBar progressProcesadas;
    private ProgressBar progressRecuperadas;
    private ProgressBar progressRecuperadasHoy;
    private ProgressBar progressFallecidosHoy;
    private ProgressBar progressFallecidos;
    private ProgressBar progressNegative;
    private ProgressBar progressTotal;
    private AlertDialog dialog;
    public static Country country;
    private CountryAdapter mAdapter;
    private List<Country> listCountrys = new ArrayList<>();
    private ImageButton ib;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cifras,container,false);

        setting(view);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CifrasPeruFragment fr = new CifrasPeruFragment();
                fr.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,fr)
                        .addToBackStack(null)
                        .commit();
            }
        });

        MainActivity.navigationView.setCheckedItem(R.id.nav_cifras);
        return view;
    }

    private void setting(View view) {
        progressTotal=(ProgressBar)view.findViewById(R.id.pTotalCasos);
        progressNuevos=(ProgressBar)view.findViewById(R.id.pCasosNuevos);
        progressProcesadas=(ProgressBar)view.findViewById(R.id.pMuestras);
        progressRecuperadas=(ProgressBar)view.findViewById(R.id.pRecuperadas);
        progressRecuperadasHoy=(ProgressBar)view.findViewById(R.id.pRecuperadasHoy);
        progressFallecidos=(ProgressBar)view.findViewById(R.id.pTotalDeaths);
        progressFallecidosHoy=(ProgressBar)view.findViewById(R.id.pFallecidosHoy);
        progressNegative=(ProgressBar)view.findViewById(R.id.pNegative);
        ib= (ImageButton)view.findViewById(R.id.ibCasosConfirmados);
    }

    public void getCountries(){
        String url="http://192.168.0.15:3000/api/cases/countries";
        new Countries().execute(url);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(listCountrys.size()<=0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("");
            builder.setMessage(R.string.cargando);
            dialog= builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            getCountries();
        }else{
            AutoCompleteTextView txtCountry = getActivity().findViewById(R.id.atCountries);
            mAdapter = new CountryAdapter(getContext(),listCountrys);
            txtCountry.setAdapter(mAdapter);
            txtCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object item = parent.getItemAtPosition(position);
                    if(item instanceof Country){
                        Country country = (Country) item;
                        getNumbers(country.getCountry());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("");
                        builder.setMessage(R.string.cargando_cifras);
                        dialog= builder.create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }
            });
        }
    }

    public void getNumbers(String country){
        String url="http://192.168.0.15:3000/api/cases/country/"+country;
        new Cases().execute(url);
    }

    public class Cases extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return NetUtilities.getCases(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject result = jsonObject.getJSONObject("result");
                String pais = result.getString("pais");
                int totalPruebas = result.getInt("totalPruebas");
                int casosNegativos = result.getInt("casosNegativos");
                int casos = result.getInt("casos");
                int casosHoy = result.getInt("casosHoy");
                int fallecidos = result.getInt("fallecidos");
                int fallecidosHoy = result.getInt("fallecidosHoy");
                String letalidad = result.getString("letalidad");
                int recuperados = result.getInt("recuperados");
                int recuperadosHoy = result.getInt("recuperadosHoy");
                int criticos = result.getInt("criticos");
                String flag = result.getString("flag");
                country = new Country(pais,totalPruebas,casosNegativos,casos,casosHoy,fallecidos,fallecidosHoy,letalidad,recuperados,recuperadosHoy,criticos,flag);
                int pNuevos =country.getTodayCases()*100/country.getCasesConfirmed();
                int pCasos = country.getCasesConfirmed()*100/country.getTotalCases();
                int pRecuperadas = country.getRecovered()*100/country.getCasesConfirmed();
                int pRecuperadasHoy = country.getRecoveredToday()*100/country.getRecovered();
                int pFallecidos = country.getDeaths()*100/country.getCasesConfirmed();
                int pFallecidosHoy = country.getTodayDeaths()*100/country.getDeaths();
                int pNegativo = country.getNegativeCases()*100/country.getTotalCases();
                if(pNuevos<1 ){
                    pNuevos=1;
                }
                if(pRecuperadasHoy<1){
                    pRecuperadasHoy=1;
                }
                if(pFallecidosHoy<1){
                    pFallecidosHoy=1;
                }

                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
                symbols.setGroupingSeparator(' ');

                DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);

                progressProcesadas.setProgress(100,true);
                progressNegative.setProgress(pNegativo,true);
                progressTotal.setProgress(pCasos,true);
                progressNuevos.setProgress(pNuevos,true);
                progressRecuperadas.setProgress(pRecuperadas,true);
                progressRecuperadasHoy.setProgress(pRecuperadasHoy,true);
                progressFallecidos.setProgress(pFallecidos,true);
                progressFallecidosHoy.setProgress(pFallecidosHoy,true);
                TextView txTTotal = (TextView) getActivity().findViewById(R.id.txtTotal);
                TextView txtNegativo = (TextView) getActivity().findViewById(R.id.txtNegative);
                TextView txtTotalCases = (TextView) getActivity().findViewById(R.id.txtTotalCases);
                TextView txtNuevos = (TextView) getActivity().findViewById(R.id.txtNewCases);
                TextView txtRecuperadas = (TextView) getActivity().findViewById(R.id.txtRecovered);
                TextView txtRecuperadasHoy = (TextView) getActivity().findViewById(R.id.txtRecoveredToday);
                TextView txtFallecidos = (TextView) getActivity().findViewById(R.id.txtDeaths);
                TextView txtFallecidosHoy = (TextView) getActivity().findViewById(R.id.txtTodayDeaths);
                TextView txtCountry = (TextView)getActivity().findViewById(R.id.txtCountry);
                txtCountry.setText(country.getCountry());
                txTTotal.setText("+ "+formatter.format(country.getTotalCases()));
                txtNegativo.setText("+ "+formatter.format(country.getNegativeCases()));
                txtTotalCases.setText("+ "+formatter.format(country.getCasesConfirmed()));
                txtNuevos.setText("+ "+formatter.format(country.getTodayCases()));
                txtRecuperadas.setText("+ "+formatter.format(country.getRecovered()));
                txtRecuperadasHoy.setText("+ "+formatter.format(country.getRecoveredToday()));
                txtFallecidos.setText("+ "+formatter.format(country.getDeaths()));
                txtFallecidosHoy.setText("+ "+formatter.format(country.getTodayDeaths()));

                if(country.getCountry().equals("Peru")){
                    ib.setVisibility(View.VISIBLE);
                }else{
                    ib.setVisibility(View.INVISIBLE);
                }

            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(getContext(), "Problemas internos, espere...", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }

    public class Countries extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            return NetUtilities.getCases(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray countries = jsonObject.getJSONArray("countries");
                for(int i=0;i<countries.length();i++){
                    JSONObject country =countries.getJSONObject(i);
                    String pais = country.getString("country");
                    String flag = country.getString("flag");
                    listCountrys.add(new Country(pais,flag));
                    AutoCompleteTextView txtCountry = getActivity().findViewById(R.id.atCountries);
                    mAdapter = new CountryAdapter(getContext(),listCountrys);
                    txtCountry.setAdapter(mAdapter);
                    txtCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Object item = parent.getItemAtPosition(position);
                            if(item instanceof Country){
                                Country country = (Country) item;
                                getNumbers(country.getCountry());
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("");
                                builder.setMessage(getString(R.string.cargando_cifras));
                                dialog= builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            } catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(getContext(), "Problemas internos, espere...", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        }
    }


}