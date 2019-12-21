package com.app.id.spk_penerimaan_beasiswa;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.id.spk_penerimaan_beasiswa.JSON.JSONparser;
import com.app.id.spk_penerimaan_beasiswa.JSON.ServiceHandler;
import com.app.id.spk_penerimaan_beasiswa.adapter.adapter;
import com.app.id.spk_penerimaan_beasiswa.kelas.gaji;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class fragment_hasil extends Fragment {

    private static adapter a;
    private ListView lv;
    private Button btnInput;
    private get_hasil g = new get_hasil();
    String hasil = "";

    private static String add_keputusan = "http://10.0.2.2/spk_beasiswa/add_keputusan.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_SALARY = "skala";

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONparser jsonParser = new JSONparser();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_hasil, container, false);

        lv = (ListView) view.findViewById(R.id.lvLog);


        a = new adapter(view.getContext() , R.layout.list_hasil , g.getHasil());
        lv.setAdapter(a);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.input_data, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add){
            new addKeputusan().execute();
            return true;
        }else if(id == R.id.delete){
            g.getHasil().clear();
            a.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class addKeputusan extends AsyncTask<String, String, String> {

        private final Context ctx = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Add Data");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
//            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String getDate = formatter.format(date);

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("hasil", g.getAkhir()));

            params.add(new BasicNameValuePair("tanggal", getDate));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(add_keputusan,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                } else {
//                    Toast.makeText(getActivity(),"Gagal tambah data", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(),"Sukses tambah data", Toast.LENGTH_SHORT).show();
            // dismiss the dialog once done
//            pDialog.dismiss();
        }

    }


}
