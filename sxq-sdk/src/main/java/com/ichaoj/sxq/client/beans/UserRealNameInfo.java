package com.ichaoj.sxq.client.beans;

import com.ichaoj.sxq.client.util.IdCardUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

/**
 * @author xlang
 * @version 0.0.1
 * @desciption 实名认证基本信息
 * @date 2020/5/21 11:50
 */
@Data
public class UserRealNameInfo {

    /**
     * 个人: 真实姓名, 企业: 法人姓名
     */
    private String realName;
    /**
     * 企业的名称
     */
    private String enterpriseRealName;
    /**
     * 证件号码
     */
    private String certNo;
    /**
     * 企业的社会信用统一号
     */
    private String enterpriseCertNo;
    /**
     * 企业证件类型
     * @see com.yjf.esupplier.ws.enums.CertTypeEnum
     */
    private String enterpriseCertType;
    /**
     * 绑定电话
     */
    private String mobile;
    /**
     * 绑定邮箱
     */
    private String mail;
    /**
     * 用户类型: JG, GR
     * @see com.yjf.esupplier.ws.enums.UserTypeEnum
     */
    private String type;


    public void check() {
        boolean account = StringUtils.isEmpty(mobile) && StringUtils.isEmpty(mail);
        Assert.assertFalse("手机号和邮箱必须填写一个", account);

        boolean isIdcard = IdCardUtils.isIdcard(certNo);
        Assert.assertTrue("身份证号格式错误", isIdcard);

        if ("GR".equals(type)) {
            Assert.assertFalse("姓名不能为空", StringUtils.isEmpty(realName));
        } else if ("JG".equals(type)) {
            boolean notRealName = StringUtils.isEmpty(realName);
            boolean notEntRealName = StringUtils.isEmpty(enterpriseRealName);
            boolean notEntCertNo = StringUtils.isEmpty(enterpriseCertNo);
            boolean notEntCertType = StringUtils.isEmpty(enterpriseCertType);
            Assert.assertFalse("认证信息请填写完整", notRealName || notEntRealName || notEntCertNo || notEntCertType);
        } else {
            throw new RuntimeException("未知的用户类型");
        }
    }

}
