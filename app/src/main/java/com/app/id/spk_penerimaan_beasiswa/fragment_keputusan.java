package com.app.id.spk_penerimaan_beasiswa;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.id.spk_penerimaan_beasiswa.JSON.*;
import com.app.id.spk_penerimaan_beasiswa.kelas.*;
import com.app.id.spk_penerimaan_beasiswa.adapter.adapter;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class fragment_keputusan extends Fragment {

    private static String get_salary = "http://10.0.2.2/spk_beasiswa/getSalary.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_SALARY = "skala";

    JSONArray gjl = null;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    private ArrayList<gaji> gajiOrtu;

    private ServiceHandler service = new ServiceHandler();

    JSONparser jsonParser = new JSONparser();

    private Spinner spSalary;
    private Button btnInput,btnProses, btnInputTo;
    private EditText txtNama, txtNISN, txtRaport, popup;
    private RadioGroup rg1,rg2,rg3,rg4;
    private RadioButton rb11,rb12,rb13,rb21,rb22,rb31,rb32,rb41,rb42,rb43;

    private String NilaiRaport, KeaktifanOrganisai, BykPenghargaan, PenghasilanOrtu, JmlSaudara, Attitude;
    private String nama,NISN, tempSalary;
    private boolean cek = false;
    private int tempKolom = 8;
    private int  kolom = 6;
    private String[][] tempInput = new String[100][tempKolom];
    private double[][] Nilai;
    private double[][] Normalisasi;
    private double[][] Normalisasi_terbobot;
    private double[] EachColumn;
    private double[] resulEachColumn;
    private double[] max;
    private double[] min;
    private double[] ideal_plus;
    private double[] ideal_min;
    private int[] bobot = {5,3,4,2,2,3};
    String[] Anama;
    String[] ANISN;
    double[] hasil_akhir;
    String[] tempHasil;
    String[] tempAll;
    private int i = 0;

    private static adapter a;
    ArrayList<final_result> hasilAkhir = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fg_keputusan, container, false);

        spSalary = (Spinner) view.findViewById(R.id.spPenghasilan);
        btnInput = (Button) view.findViewById(R.id.btnInput);
        btnProses = (Button) view.findViewById(R.id.btnProses);
        rg1 = (RadioGroup) view.findViewById(R.id.rgKeaktifan);
        rg2 = (RadioGroup) view.findViewById(R.id.rgPenghargaan);
        rg3 = (RadioGroup) view.findViewById(R.id.rgSaudara);
        rg4 = (RadioGroup) view.findViewById(R.id.rgAttitude);
        txtNama = (EditText) view.findViewById(R.id.txtNama);
        txtNISN = (EditText) view.findViewById(R.id.txtNISN);
        txtRaport = (EditText) view.findViewById(R.id.txtRaport);
        rb11 = (RadioButton) view.findViewById(R.id.rbAktif);
        rb12 = (RadioButton) view.findViewById(R.id.rbKurangAktif);
        rb13 = (RadioButton) view.findViewById(R.id.rbTidakAktif);
        rb21 = (RadioButton) view.findViewById(R.id.rbPernah);
        rb22 = (RadioButton) view.findViewById(R.id.rbTidak_pernah);
        rb31 = (RadioButton) view.findViewById(R.id.rbPunya);
        rb32 = (RadioButton) view.findViewById(R.id.rbTidak_punya);
        rb41 = (RadioButton) view.findViewById(R.id.rbBaik);
        rb42 = (RadioButton) view.findViewById(R.id.rbCukup);
        rb43 = (RadioButton) view.findViewById(R.id.rbBuruk);

        gajiOrtu = new ArrayList<gaji>();

        new loadSalary().execute();

        //popup banyak penghargaan
        rb21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = fragment_keputusan.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.popup_input, null);
                builder1.setTitle("Input Banyak (Jika >5 cukup inputkan 5 saja)");
                builder1.setCancelable(false);

                popup = (EditText) view.findViewById(R.id.txtBanyak);
                popup.setText("1");

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                BykPenghargaan = popup.getText().toString();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                pDialog.dismiss();
                            }
                        });

                builder1.setView(view);
                AlertDialog al = builder1.create();
                al.show();
            }
        });

        rb31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = fragment_keputusan.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.popup_input, null);
                builder1.setTitle("Banyak Saudara (jika > 5 cukup input 5)");
                builder1.setCancelable(false);

                popup = (EditText) view.findViewById(R.id.txtBanyak);
                popup.setText("1");

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                JmlSaudara = popup.getText().toString();
                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                pDialog.dismiss();
                            }
                        });

                builder1.setView(view);
                AlertDialog al = builder1.create();
                al.show();
            }
        });



            btnInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setTitle("INPUT KANDIDAT BEASISWA LAGI?");
                        builder1.setCancelable(false);
                            getNama();
                            getNISN();
                            getRaport();
                            getGaji();
                            getKeaktifan();
                            getPenghargaan();
                            getJmlSaudara();
                            getAttitude();
                        builder1.setPositiveButton(
                                "YA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                    }
                                });

                        builder1.setNegativeButton(
                                "TIDAK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        btnInput.setEnabled(false);
                                        cek = false;
                                    }
                                });

                        AlertDialog al = builder1.create();
                        al.show();

                        tempInput[i][0] = nama;
                        tempInput[i][1] = NISN;
                        tempInput[i][2] = NilaiRaport;
                        tempInput[i][3] = KeaktifanOrganisai;
                        tempInput[i][4] = BykPenghargaan;
                        tempInput[i][5] = PenghasilanOrtu;
                        tempInput[i][6] = JmlSaudara;
                        tempInput[i][7] = Attitude;
                        i++;

                        reset();
                    }
            });

            btnProses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Nilai = new double[i][kolom];
                    Normalisasi = new double[i][kolom];
                    Normalisasi_terbobot = new double[i][kolom];
                    Anama = new String[i];
                    ANISN = new String[i];
                    EachColumn = new double[kolom];
                    resulEachColumn = new double[kolom];
                    max = new double[kolom];
                    min = new double[kolom];
                    ideal_plus = new double[i];
                    ideal_min = new double[i];
                    hasil_akhir = new double[i];
                    tempAll = new String[i];

                    //clustering value for process and not for process
                    for(int a=0;a<i;a++){
                        for(int b = 0; b<tempKolom - 2; b++){
                            Anama[a] = tempInput[a][0];
                            ANISN[a] = tempInput[a][1];
                            Nilai[a][b] = Double.parseDouble(tempInput[a][b+2]);
                        }
                    }

                    //checking value have clustered or have not
                    for(int a=0;a<i;a++){
                        for(int b = 0; b<kolom; b++){
//                            Log.d("Nilai ", String.valueOf(Nilai[a][b]));
                        }
                    }

                    //get value each column
                    for(int a=0;a<kolom;a++){
                        double tempNormalisasi = 0;
                        double pangkat = 0;
                        double hasilNormalisasi = 0;
                        for(int b = 0; b<i; b++){
                            EachColumn[a] = Nilai[b][a];
                            tempNormalisasi =  tempNormalisasi + Math.pow(Nilai[b][a], 2);
                            pangkat = Math.sqrt(tempNormalisasi);
                            resulEachColumn[a] = pangkat;
                        }

                    }

                    //normalisasi
                    for(int a=0;a<i;a++){
                        for(int b = 0; b<kolom; b++){
                            Normalisasi[a][b] = Nilai[a][b] / resulEachColumn[b];
                        }
                    }

                    //normalisasi terbobot
                    for(int a=0;a<i;a++){
                        for(int b = 0; b<kolom; b++){
                            Normalisasi_terbobot[a][b] = Normalisasi[a][b] * bobot[b];
                            Log.d("Normalisasi ", "[" + a + "] [" + b +"]" + String.valueOf(Normalisasi_terbobot[a][b]));
                        }
                    }

                    //mencari nilai max dan min
                    for(int a=0;a<kolom;a++){
                        double temp = Normalisasi_terbobot[0][a];
                        double temp2 = Normalisasi_terbobot[0][a];
                        for(int b = 0; b<i; b++){
                            if(Normalisasi_terbobot[b][a] >= temp){
                                temp = Normalisasi_terbobot[b][a];
                            }

                            if(Normalisasi_terbobot[b][a] <= temp2){
                                temp2 = Normalisasi_terbobot[b][a];
                            }
                        }
                        max[a] = temp;
                        min[a] = temp2;
                    }

                    for(int a=0;a<kolom;a++){
                        Log.d("MAX ", String.valueOf(max[a]));
                        Log.d("MIN ", String.valueOf(min[a]));
                    }

                    for(int a=0;a<i;a++){
                        double temp1max = 0;
                        double temp1min = 0;
                        double temp2max = 0;
                        double temp2min = 0;
                        for(int b = 0; b<kolom; b++){
                            double temp11 = max[b] - Normalisasi_terbobot[a][b];
                            double temp21 = Normalisasi_terbobot[a][b] - min[b];
                            temp1max = temp1max + Math.pow(temp11, 2);
                            temp1min = temp1min+ Math.pow(temp21, 2);
//                            Log.d("satu ", String.valueOf(temp1max));
//                            Log.d("dua ", String.valueOf(temp1min));
                            temp2max = Math.sqrt(temp1max);
                            temp2min = Math.sqrt(temp1min);
                        }
                        ideal_plus[a] = temp2max;
                        ideal_min[a] = temp2min;
                    }

                    for(int a=0;a<i;a++){
                        hasil_akhir[a] = ideal_min[a] / (ideal_min[a] + ideal_plus[a]);
                    }


                    for(int a=0;a<i;a++){
                        Log.d("Hasil Akhir ", String.valueOf(hasil_akhir[a]));
//                        Log.d("Ideal - ", String.valueOf(ideal_min[a]));
                    }

                    DecimalFormat df = new DecimalFormat("#.######");

                    double temp = 0;
                    String tempNama;
                    String tempNISN;
                    final_result f = new final_result();
                    ArrayList<String> temp1;
                    ArrayList<String> temp2;
                    ArrayList<String> temp3;
                    tempHasil = new String[i];
                    for (int a = 0; a < i; a++) {
                        for (int j = 1; j < (i - a); j++) {
                            if (hasil_akhir[j-1] < hasil_akhir[j]){
                                temp = hasil_akhir[j-1];
                                tempNama = Anama[j-1];
                                tempNISN = ANISN[j-1];
                                hasil_akhir[j-1] = hasil_akhir[j];
                                Anama[j-1] = Anama[j];
                                ANISN[j-1] = ANISN[j];
                                hasil_akhir[j] = temp;
                                Anama[j] = tempNama;
                                ANISN[j] = tempNISN;
                            }
                        }
                    }

                    for(int a=0;a<i;a++){
                        tempHasil[a] = String.valueOf(df.format(hasil_akhir[a]));
                        Log.d("Hasil ", tempHasil[a]);
                    }

                    get_hasil g = new get_hasil();


                    for(int a=0;a<i;a++){
                        hasilAkhir.add(new final_result(Anama[a],ANISN[a],tempHasil[a]));
//                        Log.d("Hasil ", "Nama : " + g.getNama() + " NISN : " + g.getNisn() + " Score : " + g.getScore());
                    }


                    for(int a=0;a<i;a++){
//                        Log.d("Hasil ", " NISN : " + ANISN[a] + " Nama : " + Anama[a] + " Score : " + tempHasil[a]);
                        tempAll[a] = "\n"+(a+1) + "."+ " NISN : " + ANISN[a] + "\n    Nama : " + Anama[a] + "\n    Score : " + tempHasil[a] + "\n";
                    }

                    String ttt = Arrays.toString(tempAll);
                    Log.d("Hasil ", ttt);

                    g.setHasil(hasilAkhir);
                    g.setAkhir(ttt);

                    Toast.makeText(view.getContext(), "Silahkan melihat hasilnya pada tab HASIL ", Toast.LENGTH_SHORT).show();

                }
            });



        return view;
    }

    public void reset(){
        txtNama.setText("");
        txtNISN.setText("");
        txtRaport.setText("");
        rg1.clearCheck();
        rg2.clearCheck();
        rg3.clearCheck();
        rg4.clearCheck();
    }

    public void openReuslt(){
        fragment_hasil someFragment = new fragment_hasil();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, someFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class loadSalary extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Memuat Data");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            String json = service.makeServiceCall(get_salary, service.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray categories = jsonObj
                                .getJSONArray("data");

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = (JSONObject) categories.get(i);
                            gaji cat = new gaji(catObj.getString("skala"));
                            Log.d("skala", String.valueOf(cat));
                            gajiOrtu.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
                populateSpinner();
        }

    }

    private void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < gajiOrtu.size(); i++) {
            lables.add(gajiOrtu.get(i).getSkalaGaji());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spSalary.setAdapter(spinnerAdapter);
    }

    private void getNama(){
        nama = txtNama.getText().toString();
    }

    private void getNISN(){
        NISN = txtNISN.getText().toString();
    }

    private void getRaport(){
        NilaiRaport = txtRaport.getText().toString();
        double temp = Double.parseDouble(NilaiRaport);
        double temp2 = temp / 20;
        NilaiRaport = String.valueOf(temp2);
    }

    private void getGaji(){
        tempSalary = spSalary.getSelectedItem().toString();

        if(tempSalary.equals("<600rb")){
            PenghasilanOrtu = "5";
        }else if(tempSalary.equals("600rb-1,5jt")){
            PenghasilanOrtu = "4";
        }else if(tempSalary.equals("1,5jt-3,5jt")){
            PenghasilanOrtu = "3";
        }else if(tempSalary.equals("3,5jt-5jt")){
            PenghasilanOrtu = "2";
        }else if(tempSalary.equals(">5jt")){
            PenghasilanOrtu = "1";
        }
    }

    private void getKeaktifan(){
        int selectedId = rg1.getCheckedRadioButtonId();

        if(selectedId == rb11.getId()){
            KeaktifanOrganisai = "5";
        }else if (selectedId == rb12.getId()){
            KeaktifanOrganisai = "3";
        }else if (selectedId == rb13.getId()){
            KeaktifanOrganisai = "1";
        }
    }

    private void getPenghargaan(){
        int selectedId = rg2.getCheckedRadioButtonId();

        if(selectedId == rb22.getId()){
            BykPenghargaan = "1";
        }
    }

    private void getJmlSaudara(){
        int selectedId = rg3.getCheckedRadioButtonId();

        if(selectedId == rb32.getId()){
            JmlSaudara = "1";
        }
    }

    private void getAttitude(){
        int selectedId = rg4.getCheckedRadioButtonId();

        if(selectedId == rb41.getId()){
            Attitude = "5";
        }else if (selectedId == rb42.getId()){
            Attitude = "3";
        }else if (selectedId == rb43.getId()){
            Attitude = "1";
        }
    }

}
