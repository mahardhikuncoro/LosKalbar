package base.sqlite;

public class ParamKategoriAsuransiModel {

    private Integer idSqlite;
    private Long id;
    private Integer batas_atas;
    private Integer batas_bawah;
    private String value;

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

    public Integer getBatasAtas() {
        return batas_atas;
    }

    public void setBatasAtas(Integer batasAtas) {
        this.batas_atas = batasAtas;
    }

    public Integer getBatasBawah() {
        return batas_bawah;
    }

    public void setBatasBawah(Integer batasBawah) {
        this.batas_bawah = batasBawah;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
