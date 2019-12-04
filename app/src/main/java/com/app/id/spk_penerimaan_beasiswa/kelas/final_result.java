package com.app.id.spk_penerimaan_beasiswa.kelas;


public class final_result {

    String nama_akhir;
    String nisns_akhir;
    String hasil_akhir;

    public final_result(){

    }

    public final_result(String nama_akhir, String nisns_akhir, String hasil_akhir) {
        this.nama_akhir = nama_akhir;
        this.nisns_akhir = nisns_akhir;
        this.hasil_akhir = hasil_akhir;
    }

    public String getNama_akhir() {
        return nama_akhir;
    }

    public void setNama_akhir(String nama_akhir) {
        this.nama_akhir = nama_akhir;
    }

    public String getNisns_akhir() {
        return nisns_akhir;
    }

    public void setNisns_akhir(String nisns_akhir) {
        this.nisns_akhir = nisns_akhir;
    }

    public String getHasil_akhir() {
        return hasil_akhir;
    }

    public void setHasil_akhir(String hasil_akhir) {
        this.hasil_akhir = hasil_akhir;
    }

}
