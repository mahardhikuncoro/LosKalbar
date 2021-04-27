package base.network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christian on 4/6/18.
 */

public class BranchJson {

    public class BranchRequest{
        private Integer version;
        private Long branchid;

        public Integer getVersion() {
            return version;
        }

        public void setVersion(Integer version) {
            this.version = version;
        }

        public Long getBranchid() {
            return branchid;
        }

        public void setBranchid(Long branchid) {
            this.branchid = branchid;
        }
    }



    public class BranchCallback{

        private List<Branch> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<Branch> getL() {
            return l;
        }

        public List<Branch> getList() {
            return l;
        }

        public class Branch {
//            private Long branchid;
//            private String name;
//            private String address1;
//            private String address2;
//            private String telp;
//            private String fax;
//            private String city;
//            private Long postalcode;
//            private String branchtype;
//            private Long parentid;
//            private String email_cabang;
//            private Long wilayah;
//            private String view;

            private Long branchid;
            private String name;
            private String branchtype;
            private Long parentid;
            private Integer wilayah;

            //Untuk Branch detail
            private String address1;
            private String address2;
            private String telp;
            private String fax;
            private String city;
            private Long postalcode;
            private String latitude;
            private String longitude;

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

            public String getAddress1() {
                return address1;
            }

            public void setAddress1(String address1) {
                this.address1 = address1;
            }

            public String getAddress2() {
                return address2;
            }

            public void setAddress2(String address2) {
                this.address2 = address2;
            }

            public String getTelp() {
                return telp;
            }

            public void setTelp(String telp) {
                this.telp = telp;
            }

            public String getFax() {
                return fax;
            }

            public void setFax(String fax) {
                this.fax = fax;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public Long getPostalcode() {
                return postalcode;
            }

            public void setPostalcode(Long postalcode) {
                this.postalcode = postalcode;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String langitude) {
                this.longitude = langitude;
            }
        }
    }
}
