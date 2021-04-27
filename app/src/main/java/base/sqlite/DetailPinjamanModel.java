package base.sqlite;


public class DetailPinjamanModel {
    private Integer tenor;
    private String customerName;
    private String vaNumber;
    private String ppk;
    private String dueDate;

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getVaNumber() {
        return vaNumber;
    }

    public void setVaNumber(String vaNumber) {
        this.vaNumber = vaNumber;
    }

    public String getPpk() {
        return ppk;
    }

    public void setPpk(String ppk) {
        this.ppk = ppk;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String terminateDate) {
        this.dueDate = terminateDate;
    }
}
