package com.ichaoj.sxq.client.compoment;

/**
 * @author fan
 * @date 2018-11-28 17:33
 */
public class StoreResult extends ResultBase {

    /**
     * 存储编号
     */
    private String storeNo;

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    @Override
    public String toString() {
        return "StoreResult{" +
                "storeNo='" + storeNo + '\'' +
                "} " + super.toString();
    }
}
