package base.sqlite;

public class ContentModel {
    private String dataId, dataDesc;
            Integer indexData;

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

    public Integer getIndexData() {
        return indexData;
    }

    public void setIndexData(Integer indexData) {
        this.indexData = indexData;
    }
}
