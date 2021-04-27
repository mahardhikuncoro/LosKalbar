package base.sqlite;

public class RateBiayaModel {

    private Integer idSqlite;
    private Long id;
    private String tipe;
    private String kategori;
    private String produk;
    private String namaBiaya;
    private Integer Tenor;
    private Double value;

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

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getNamaBiaya() {
        return namaBiaya;
    }

    public void setNamaBiaya(String namaBiaya) {
        this.namaBiaya = namaBiaya;
    }

    public Integer getIdSqlite() {
        return idSqlite;
    }

    public void setIdSqlite(Integer idSqlite) {
        this.idSqlite = idSqlite;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getTenor() {
        return Tenor;
    }

    public void setTenor(Integer tenor) {
        Tenor = tenor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
