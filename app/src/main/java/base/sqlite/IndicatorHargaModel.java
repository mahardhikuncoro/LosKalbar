package base.sqlite;

public class IndicatorHargaModel {

    private Integer idSqlite;
    private Long id;
    private Double batas_atas;
    private Double batas_bawah;
    private String indicator_harga;

    public void setIndicator_harga(String indicator_harga) {
        this.indicator_harga = indicator_harga;
    }

    public String getIndicator_harga() {
        return indicator_harga;
    }

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

    public Double getBatasAtas() {
        return batas_atas;
    }

    public void setBatasAtas(Double batasAtas) {
        this.batas_atas = batasAtas;
    }

    public Double getBatasBawah() {
        return batas_bawah;
    }

    public void setBatasBawah(Double batasBawah) {
        this.batas_bawah = batasBawah;
    }

}
