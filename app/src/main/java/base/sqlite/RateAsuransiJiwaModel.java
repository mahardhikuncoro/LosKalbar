package base.sqlite;

public class RateAsuransiJiwaModel {

    private Integer idSqlite;
    private Integer id;
    private String keterangan;
    private Double batas_atas;
    private Double batas_bawah;
    private Double biaya_tahun_1;
    private Double biaya_tahun_2;
    private Double biaya_tahun_3;
    private Double biaya_tahun_4;


    public Integer getId() {
        return idSqlite;
    }

    public void setId(Integer id) {
        this.idSqlite = id;
    }

    public Integer getBackendId() {
        return id;
    }

    public void setBackendId(Integer backendId) {
        this.id = backendId;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Double getBatas_atas() {
        return batas_atas;
    }

    public void setBatas_atas(Double batas_atas) {
        this.batas_atas = batas_atas;
    }

    public Double getBatas_bawah() {
        return batas_bawah;
    }

    public void setBatas_bawah(Double batas_bawah) {
        this.batas_bawah = batas_bawah;
    }

    public Double getBiaya_tahun_1() {
        return biaya_tahun_1;
    }

    public void setBiaya_tahun_1(Double biaya_tahun_1) {
        this.biaya_tahun_1 = biaya_tahun_1;
    }

    public Double getBiaya_tahun_2() {
        return biaya_tahun_2;
    }

    public void setBiaya_tahun_2(Double biaya_tahun_2) {
        this.biaya_tahun_2 = biaya_tahun_2;
    }

    public Double getBiaya_tahun_3() {
        return biaya_tahun_3;
    }

    public void setBiaya_tahun_3(Double biaya_tahun_3) {
        this.biaya_tahun_3 = biaya_tahun_3;
    }

    public Double getBiaya_tahun_4() {
        return biaya_tahun_4;
    }

    public void setBiaya_tahun_4(Double biaya_tahun_4) {
        this.biaya_tahun_4 = biaya_tahun_4;
    }
}


