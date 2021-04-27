package base.sqlite;

import android.content.Intent;

public class ParamPinjamanExtendedModel {

    private Integer idSqlite;
    private Long id;
    private String produk;
    private String type;
    private Integer nilai;
    private String satuan_nilai;
    private Integer usia_batas_bawah;
    private Integer usia_batas_atas;
    private String keterangan;


    public Integer getId() {
        return idSqlite;
    }

    public void setId(Integer id) {
        this.idSqlite = id;
    }

    public Long getBackendId() {
        return id;
    }

    public void setBackendId(Long backendId) {
        this.id = backendId;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNilai() {
        return nilai;
    }

    public void setNilai(Integer nilai) {
        this.nilai = nilai;
    }

    public String getSatuan_nilai() {
        return satuan_nilai;
    }

    public void setSatuan_nilai(String satuan_nilai) {
        this.satuan_nilai = satuan_nilai;
    }

    public Integer getUsia_batas_bawah() {
        return usia_batas_bawah;
    }

    public void setUsia_batas_bawah(Integer usia_batas_bawah) {
        this.usia_batas_bawah = usia_batas_bawah;
    }

    public Integer getUsia_batas_atas() {
        return usia_batas_atas;
    }

    public void setUsia_batas_atas(Integer usia_batas_atas) {
        this.usia_batas_atas = usia_batas_atas;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}


