package base.location.network;


import java.util.List;

import base.location.MobileConfig;

/**
 * Created by ADMINSMMF on 1/16/2018.
 */

public class MobileConfigJson {

    public class Callback extends BaseNetworkCallback {

        private List<MobileConfig> list;

        public List<MobileConfig> getList() {
            return list;
        }

        public void setList(List<MobileConfig> list) {
            this.list = list;
        }

    }
}
