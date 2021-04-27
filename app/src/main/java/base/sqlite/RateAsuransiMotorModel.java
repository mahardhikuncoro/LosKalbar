package base.sqlite;

public class RateAsuransiMotorModel {

    private Integer idSqlite;
    private Integer id;
    private String produk;
    private String tipe;
    private Integer tenor;
    private Double value_asuransi;

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

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public Double getValue_asuransi() {
        return value_asuransi;
    }

    public void setValue_asuransi(Double value_asuransi) {
        this.value_asuransi = value_asuransi;
    }
}
