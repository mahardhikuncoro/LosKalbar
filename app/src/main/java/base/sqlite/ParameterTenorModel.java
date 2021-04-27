package base.sqlite;

public class ParameterTenorModel {

    private Integer idSqlite;
    private Long id;
    private Double batas_atas;
    private Double batas_bawah;
    private Integer value;
    private String parameter;

    public void setParameter(String parameter1) {
        this.parameter = parameter1;
    }

    public String getParameter() {
        return parameter;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
