package base.location;

/**
 * Created by ADMINSMMF on 1/16/2018.
 */

public class MobileConfig {

    private Long mobileConfigId;
    private String key;
    private String value;

    public MobileConfig() {

    }

    public MobileConfig(
            Long mobileConfigId, String key, String value) {
        this.mobileConfigId = mobileConfigId;
        this.key = key;
        this.value = value;
    }

    public Long getMobileConfigId() {
        return mobileConfigId;
    }

    public void setMobileConfigId(Long mobileConfigId) {
        this.mobileConfigId = mobileConfigId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
