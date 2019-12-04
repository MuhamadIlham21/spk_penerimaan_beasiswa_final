package com.app.id.spk_penerimaan_beasiswa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.id.spk_penerimaan_beasiswa.JSON.*;
import com.app.id.spk_penerimaan_beasiswa.adapter.secondAdapter;
import com.app.id.spk_penerimaan_beasiswa.kelas.*;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class fragment_instruksi extends Fragment {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONparser jParser = new JSONparser();

    List<hasil> listHasil;
    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list
    private static String getAll_decision = "http://10.0.2.2/spk_beasiswa/getAll_decision.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_HASIL = "hasil";
    private static final String TAG_TANGGAL = "tanggal";

    // products JSONArray
    JSONArray list = null;

    secondAdapter second;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_instruksi, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rHasil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listHasil = new ArrayList<>();

        new loadHasil().execute();

        return view;
    }

    class loadHasil extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(getAll_decision, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Data: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    list = json.getJSONArray(TAG_DATA);

                    // looping through All Products
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject c = list.getJSONObject(i);

                        // Storing each json item in variable
                        String Hasil = c.getString(TAG_HASIL);
                        String Tanggal = c.getString(TAG_TANGGAL);

                        listHasil.add(new hasil(Hasil,Tanggal));
                    }

                }else{
                    Toast.makeText(getActivity(),"Belum ada Data!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            second = new secondAdapter(getActivity(), listHasil);
            recyclerView.setAdapter(second);
        }

    }
}
