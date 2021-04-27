package base.sqlite;

public class ParameterModel {
    private String parameterName,parameterValue, parameterFieldValue;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterFieldValue() {
        return parameterFieldValue;
    }

    public void setParameterFieldValue(String parameterFieldValue) {
        this.parameterFieldValue = parameterFieldValue;
    }
}
