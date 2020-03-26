package com.ichaoj.sxq.client.compoment;

/**
 * @author fan
 * @date 2018-11-28 17:33
 */
public class StoreResult extends ResultBase {

    /**
     * 存储编号
     */
    private Long contractId;

    /**
     * 签署链接
     */
    private String signUrl;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    @Override
    public String toString() {
        return "StoreResult{" +
                "contractId=" + contractId +
                ", signUrl='" + signUrl + '\'' +
                "} " + super.toString();
    }
}
