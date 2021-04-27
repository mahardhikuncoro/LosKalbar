package base.sqlite;

public class ParameterPinjamanModel {

    private Integer idSqlite;
    private Long id;
    private String description;
    private Integer value_mobil;
    private Integer value_motor;
    private String parameter;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getValue_mobil() {
        return value_mobil;
    }

    public void setValue_mobil(Integer value_mobil) {
        this.value_mobil = value_mobil;
    }

    public Integer getValue_motor() {
        return value_motor;
    }

    public void setValue_motor(Integer value_motor) {
        this.value_motor = value_motor;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}


