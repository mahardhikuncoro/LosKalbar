package base.network;

import java.util.ArrayList;

import base.location.network.BaseNetworkCallback;
import base.sqlite.FieldModel;

public class SetDataJson {

    public class SetDataRequest {
        private String regno, userid, tc, formName, tableName, status;
        private ArrayList<FieldModel> field;

        public String getRegno() {
            return regno;
        }

        public void setRegno(String regno) {
            this.regno = regno;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTc() {
            return tc;
        }

        public void setTc(String tc) {
            this.tc = tc;
        }

        public String getFormName() {
            return formName;
        }

        public void setFormName(String formName) {
            this.formName = formName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public ArrayList<FieldModel> getField() {
            return field;
        }

        public void setField(ArrayList<FieldModel> field) {
            this.field = field;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public class Field{
            private String fieldName, fieldValue;

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldValue() {
                return fieldValue;
            }

            public void setFieldValue(String fieldValue) {
                this.fieldValue = fieldValue;
            }
        }

    }

    public class SetDataCallback extends BaseNetworkCallback {
//        private String status, message;
//        private ArrayList<Data> data;
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//
//        public ArrayList<Data> getData() {
//            return data;
//        }
//
//        public void setData(ArrayList<Data> data) {
//            this.data = data;
//        }
//
//        public class Data{
//            private String formName, tableName, sectionName;
//            private ArrayList<Field> field;
//
//            public String getFormName() {
//                return formName;
//            }
//
//            public void setFormName(String formName) {
//                this.formName = formName;
//            }
//
//            public String getTableName() {
//                return tableName;
//            }
//
//            public void setTableName(String tableName) {
//                this.tableName = tableName;
//            }
//
//            public ArrayList<Field> getField() {
//                return field;
//            }
//
//            public void setField(ArrayList<Field> field) {
//                this.field = field;
//            }
//
//            public String getSectionName() {
//                return sectionName;
//            }
//
//            public void setSectionName(String sectionName) {
//                this.sectionName = sectionName;
//            }
//
//            public class Field{
//                private String fieldName, fieldValue, fieldId;
//
//                public String getFieldName() {
//                    return fieldName;
//                }
//
//                public void setFieldName(String fieldName) {
//                    this.fieldName = fieldName;
//                }
//
//                public String getFieldValue() {
//                    return fieldValue;
//                }
//
//                public void setFieldValue(String fieldValue) {
//                    this.fieldValue = fieldValue;
//                }
//
//                public String getFieldId() {
//                    return fieldId;
//                }
//            }
//
//        }
    }

}

