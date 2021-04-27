package base.sqlite;



public class RentalListDetailModel {
    private String dueDate;
    private String paymentDate;
    private String osBalance;
    private String rental;
    private String dueYear;


    public String getDueYear() {
        return dueYear;
    }

    public void setDueYear(String dueYear) {
        this.dueYear = dueYear;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOsBalance() {
        return osBalance;
    }

    public void setOsBalance(String osBalance) {
        this.osBalance = osBalance;
    }

    public String getRental() {
        return rental;
    }

    public void setRental(String rental) {
        this.rental = rental;
    }
}
