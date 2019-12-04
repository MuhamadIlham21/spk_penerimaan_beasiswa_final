package com.app.id.spk_penerimaan_beasiswa.kelas;

public class hasil {
    private String hasil_keputusan;
    private String tanggal_keputusan;

    public hasil(){

    }

    public hasil(String hasil_keputusan, String tanggal_keputusan) {
        this.hasil_keputusan = hasil_keputusan;
        this.tanggal_keputusan = tanggal_keputusan;
    }

    public String getHasil_keputusan() {
        return hasil_keputusan;
    }

    public void setHasil_keputusan(String hasil_keputusan) {
        this.hasil_keputusan = hasil_keputusan;
    }

    public String getTanggal_keputusan() {
        return tanggal_keputusan;
    }

    public void setTanggal_keputusan(String tanggal_keputusan) {
        this.tanggal_keputusan = tanggal_keputusan;
    }



}
