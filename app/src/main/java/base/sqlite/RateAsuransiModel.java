package base.sqlite;

public class RateAsuransiModel {

    private Integer idSqlite;
    private Long id;
    private String tipe;
    private String indicator_harga;
    private String kategori_asuransi;
    private Double rate_wilayah_1;
    private Double rate_wilayah_2;
    private Double rate_wilayah_3;

    public Integer getId() {
        return idSqlite;
    }

    public void setId(Integer id) {
        this.idSqlite = id;
    }

    public Long getBackendId() {
        return id;
    }

    public void setBackendId(Long id) {
        this.id = id;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getIndicatorHarga() {
        return indicator_harga;
    }

    public void setIndicatorHarga(String indicatorHarga) {
        this.indicator_harga = indicatorHarga;
    }

    public void setKategoriAsuransi(String kategoriAsuransi) {
        this.kategori_asuransi = kategoriAsuransi;
    }

    public String getKategoriAsuransi() {
        return kategori_asuransi;
    }

    public Double getRateWilayah1() {
        return rate_wilayah_1;
    }

    public void setRateWilayah1(Double rateWilayah1) {
        this.rate_wilayah_1 = rateWilayah1;
    }

    public Double getRateWilayah2() {
        return rate_wilayah_2;
    }

    public void setRateWilayah2(Double rateWilayah2) {
        this.rate_wilayah_2 = rateWilayah2;
    }

    public Double getRateWilayah3() {
        return rate_wilayah_3;
    }

    public void setRateWilayah3(Double rateWilayah3) {
        this.rate_wilayah_3 = rateWilayah3;
    }
}
