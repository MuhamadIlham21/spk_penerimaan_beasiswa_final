package com.app.id.spk_penerimaan_beasiswa;

import com.app.id.spk_penerimaan_beasiswa.kelas.*;

import java.util.ArrayList;

public class get_hasil {
    private static ArrayList<final_result> hasil = new ArrayList<>();

    private static String akhir;

    public get_hasil(){

    }

    public ArrayList<final_result> getHasil() {
        return hasil;
    }

    public void setHasil(ArrayList<final_result> hasil) {
        this.hasil = hasil;
    }

    public String getAkhir() {
        return akhir;
    }

    public void setAkhir(String akhir) {
        this.akhir = akhir;
    }

}
