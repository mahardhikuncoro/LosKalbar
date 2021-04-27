package base.network;

import java.util.ArrayList;
import java.util.List;

import base.location.network.BaseNetworkCallback;
import base.sqlite.ParameterModel;

public class FormCascadingJson {

    public class RequestForm{
        private String type, field ;
        private ArrayList<ParameterModel> parameter;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public ArrayList<ParameterModel> getParameter() {
            return parameter;
        }

        public void setParameter(ArrayList<ParameterModel> parameter) {
            this.parameter = parameter;
        }
    }

    public class CallbackForm extends BaseNetworkCallback{
        private List<DataCascading> data = new ArrayList<>();

        public List<DataCascading> getData() {
            return data;
        }
        public void setData(List<DataCascading> data) {
            this.data = data;
        }

    public class DataCascading{
        private String field;
        private List<Content> content = new ArrayList<>();

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public List<Content> getContent() {
            return content;
        }

        public void setContent(List<Content> content) {
            this.content = content;
        }



    public class Content{
            private String dataId, dataDesc;

        public String getDataId() {
            return dataId;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public String getDataDesc() {
            return dataDesc;
        }

        public void setDataDesc(String dataDesc) {
            this.dataDesc = dataDesc;
        }
        }
    }
    }

}
