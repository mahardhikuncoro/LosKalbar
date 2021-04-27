package base.network;

import java.util.ArrayList;
import java.util.List;

import base.network.BaseResponseCode;

/**
 * Created by christian on 4/7/18.
 */

public class KorwilJson {
    public class Callback{
        private List<Korwil> l = new ArrayList<>();
        private String rs;

        public String getRs() {
            return rs;
        }

        public List<Korwil> getL() {
            return l;
        }

        public class Korwil{
            private Long branchid;
            private String name;

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
        }
    }
}
