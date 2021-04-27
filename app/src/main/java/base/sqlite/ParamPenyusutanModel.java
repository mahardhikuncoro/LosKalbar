package base.sqlite;

public class ParamPenyusutanModel {

    private Integer idSqlite;
    private Long id;
    private Integer parameter;
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

    public Integer getParameter() {
        return parameter;
    }

    public void setParameter(Integer parameter) {
        this.parameter = parameter;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
