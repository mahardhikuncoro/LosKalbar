package base.network;

import java.util.ArrayList;
import java.util.List;

public class PinjamanJson {

    public class PinjamanCallback {

        private String responseStatus;
        private String apiResultCode;
        private String apiResultCounter;
        private List<Result> apiResultDetail = new ArrayList<>();

        public String getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(String responseStatus) {
            this.responseStatus = responseStatus;
        }

        public String getApiResultCode() {
            return apiResultCode;
        }

        public void setApiResultCode(String apiResultCode) {
            this.apiResultCode = apiResultCode;
        }

        public String getApiResultCounter() {
            return apiResultCounter;
        }

        public void setApiResultCounter(String apiResultCounter) {
            this.apiResultCounter = apiResultCounter;
        }

        public List<Result> getApiResultDetail() {
            return apiResultDetail;
        }

        public void setApiResultDetail(List<Result> apiResultDetail) {
            this.apiResultDetail = apiResultDetail;
        }

        public class Result {
            private String responseCode;
            private Response response;

            public String getResponseCode() {
                return responseCode;
            }

            public Result.Response getResponse() {
                return response;
            }

            public class Response{
                private String responseMessage;
                private String customerName;
                private String vaNumber;
                private String ppk;
                private String terminateDate;
                private Integer tenor;
                private String dueDateCf;
                private List<PaymentHistory> paymentHistory = new ArrayList<>();

                public String getResponseMessage() {
                    return responseMessage;
                }

                public String getCustomerName() {
                    return customerName;
                }

                public String getVaNumber() {
                    return vaNumber;
                }

                public String getPpk() {
                    return ppk;
                }

                public String getTerminateDate() {
                    return terminateDate;
                }

                public Integer getTenor() {
                    return tenor;
                }

                public List<PaymentHistory> getPaymentHistory() {
                    return paymentHistory;
                }

                public class PaymentHistory {
                    private String dueDate;
                    private String paymentDate;
                    private String osBalance;
                    private String rental;
                    private Integer counter;

                    public String getDueDate() {
                        return dueDate;
                    }

                    public String getPaymentDate() {
                        return paymentDate;
                    }

                    public String getOsBalance() {
                        return osBalance;
                    }

                    public String getRental() {
                        return rental;
                    }

                    public Integer getCounter() {
                        return counter;
                    }


                }

                public String getDueDateCf() {
                    return dueDateCf;
                }
            }



        }


    }

    public class PinjamanRequest{
        private String telp;

        public String getTelp() {
            return telp;
        }

        public void setTelp(String telp) {
            this.telp = telp;
        }
    }
}
