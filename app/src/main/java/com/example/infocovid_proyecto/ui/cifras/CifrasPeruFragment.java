package com.example.infocovid_proyecto.ui.cifras;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infocovid_proyecto.MainActivity;
import com.example.infocovid_proyecto.R;
import com.example.infocovid_proyecto.interfaces.AlertaApi;
import com.example.infocovid_proyecto.models.API;
import com.example.infocovid_proyecto.models.Alertas;
import com.example.infocovid_proyecto.models.ApiCiudad;
import com.example.infocovid_proyecto.models.Ciudad;
import com.example.infocovid_proyecto.ui.inicio.InicioFragment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CifrasPeruFragment extends Fragment {
    private AutoCompleteTextView txtCiudad;
    private TextView ttCiudad;
    private TextView txtTotal;
    private TextView txtFallecidos;
    private TextView txtLetalidad;
    private TextView txtPR;
    private TextView txtPCR;
    private Button btnRegresar;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view = inflater.inflate(R.layout.fragment_cifras_peru,container,false);

         settings(view,savedInstanceState);

         return view;
    }

    private void settings(View view, Bundle savedInstanceState) {
         txtCiudad  = (AutoCompleteTextView)view.findViewById(R.id.atCiudades);

        String[] ciudades = getResources().getStringArray(R.array.ciudades);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,ciudades);
        txtCiudad.setAdapter(adapter);

        listeners(view);

        ttCiudad = (TextView)view.findViewById(R.id.txtCCiudad);
        txtTotal = (TextView)view.findViewById(R.id.txtCTotal);
        txtFallecidos = (TextView)view.findViewById(R.id.txtCFallecidos);
        txtLetalidad = (TextView)view.findViewById(R.id.txtCLetalidad);
        txtPR = (TextView)view.findViewById(R.id.txtCPR);
        txtPCR = (TextView)view.findViewById(R.id.txtCPCR);
        btnRegresar= (Button)view.findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                InicioFragment fr = new InicioFragment();
                fr.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment,fr)
                        .commit();
            }
        });
        MainActivity.navigationView.setCheckedItem(R.id.nav_cifras);
    }

    private static final String DECIMAL_FORMAT = "###,###.#";

    private String formatValue(Number value, String formatString) {
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        formatSymbols.setDecimalSeparator('.');
        formatSymbols.setGroupingSeparator(' ');
        DecimalFormat formatter = new DecimalFormat(formatString, formatSymbols);
        return formatter.format(value);
    }

    private void listeners(View view) {
        txtCiudad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                Retrofit retrofit = ApiCiudad.getRetrofitClient();
                AlertaApi api = retrofit.create(AlertaApi.class);

                Call<Ciudad> listCall = api.getCiudad(item.toString());
                listCall.enqueue(new Callback<Ciudad>() {
                    @Override
                    public void onResponse(Call<Ciudad> call, Response<Ciudad> response) {
                        Ciudad ciudad = response.body();
                        ttCiudad.setText(ciudad.getDEPARTAMENTO());
                        txtTotal.setText(formatValue(ciudad.getTOTAL(),DECIMAL_FORMAT));
                        txtFallecidos.setText(formatValue(ciudad.getFALLECIDOS(),DECIMAL_FORMAT));
                        txtLetalidad.setText(ciudad.getLETALIDAD());
                        txtPR.setText(formatValue(ciudad.getPR(),DECIMAL_FORMAT));
                        txtPCR.setText(formatValue(ciudad.getPCR(),DECIMAL_FORMAT));
                    }

                    @Override
                    public void onFailure(Call<Ciudad> call, Throwable t) {
                        Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}