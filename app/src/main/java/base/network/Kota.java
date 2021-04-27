package base.network;

/**
 * Created by christian on 4/7/18.
 */

public class Kota {

    private Long branchid;
    private String name;
    private String branchtype;
    private Long parentid;
    private Integer wilayah;


//    private Long branchid;
//    private String name;
//    private String address1;
//    private String address2;
//    private String telp;
//    private String fax;
//    private String city;
//    private Long postalcode;
//    private String branchtype;
//    private Long parentid;
//    private String email_cabang;
//    private Long wilayah;
//    private String view;


    public Long getBranchid() {
        return branchid;
    }

    public void setBranchid(Long branchid) {
        this.branchid = branchid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchtype() {
        return branchtype;
    }

    public void setBranchtype(String branchtype) {
        this.branchtype = branchtype;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public Integer getWilayah() {
        return wilayah;
    }

    public void setWilayah(Integer wilayah) {
        this.wilayah = wilayah;
    }
}
