package base.sqlite;


public class RentalListModel {
    private Integer IdQue;
    private Integer id;
    private String telp;
    private String action;
    private String parameter;
    private String date_request;
    private String status;
    private String customer_name;
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Integer getIdQue() {
        return IdQue;
    }

    public void setIdQue(Integer idQue) {
        IdQue = idQue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDate_request() {
        return date_request;
    }

    public void setDate_request(String date_request) {
        this.date_request = date_request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
