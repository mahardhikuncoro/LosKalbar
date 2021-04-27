package base.sqlite;

public class RateBungaModel {

    private Integer idSqlite;
    private Long id;
    private String tipe;
    private String produk;
    private String indicator_tahun;
    private Double rate_tahun_1;
    private Double rate_tahun_2;
    private Double rate_tahun_3;
    private Double rate_tahun_4;

    private Double rate_tahun_5;

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

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getIndicatorTahun() {
        return indicator_tahun;
    }

    public void setIndicatorTahun(String indicatorTahun) {
        this.indicator_tahun = indicatorTahun;
    }

    public Double getRateTahun1() {
        return rate_tahun_1;
    }

    public void setRateTahun1(Double rateTahun1) {
        this.rate_tahun_1 = rateTahun1;
    }

    public Double getRateTahun2() {
        return rate_tahun_2;
    }

    public void setRateTahun2(Double rateTahun2) {
        this.rate_tahun_2 = rateTahun2;
    }

    public Double getRateTahun3() {
        return rate_tahun_3;
    }

    public void setRateTahun3(Double rateTahun3) {
        this.rate_tahun_3 = rateTahun3;
    }

    public Double getRateTahun4() { return rate_tahun_4; }

    public void setRateTahun4(Double rateTahun4) { this.rate_tahun_4 = rateTahun4; }

    public Double getRateTahun5() { return rate_tahun_5; }

    public void setRateTahun5(Double rate_tahun_5) { this.rate_tahun_5 = rate_tahun_5; }
}
