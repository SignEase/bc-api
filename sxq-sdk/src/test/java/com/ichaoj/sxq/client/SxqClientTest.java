package com.ichaoj.sxq.client;


import com.ichaoj.sxq.client.beans.*;
import com.ichaoj.sxq.client.compoment.*;
import com.ichaoj.sxq.client.enums.*;
import com.yiji.openapi.tool.fastjson.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SxqClientTest {

	public static SxqClient sxqClient;
	
	@BeforeClass
	public static void init(){
		sxqClient = new SxqClient("20200303093507658157","3daca3b13ef04e7f8a751d74c8318a1f", Env.TEST);
	}
	/**
	 * 测试服务器是否连通
	 */
	@Test
	public void ping(){
		ResultBase result = sxqClient.ping();
		System.out.println(result.toString());
	}

	/********************************** 注意 ***************************************/
	/** #1 需要注意如果签约人设置了手机号或邮箱的，会作为账户的唯一标识：
	 /** a）如果该标识找到已存在账户的，会直接使用已存在账户下的信息进行签约，如：实名信息。
	 /** b）如果该标识没有查找到已存在账户的，则会用设置的信息创建新的账户信息，并完成签合约。
	 /**
	 /** #2 区块链身份证功能在测试环境无法正常扫码查看，会跳转到线上环境。需要在线上环境才能正常使用。
	 /**
	 /** #3 签约的合同底板位于Contract/BaseBoard，Fetch方法取回的合同位于Contract/Signed
	 /*******************************************************************************/

	/**
	 * 电子签约（甲乙两人签约）
	 */
	@Test
	public void signatureTwoPeople(){
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		String filePath = "C:\\Users\\admin\\Desktop\\AAA.pdf";
//		把pdf文件放进参数中
		getFileToPath(filePath, order);
//		用户业务编号
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
//		合同名称
		sxqDataStore.setStoreName("《合同名称》");
//		是否公开
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
//		签约的说明
        sxqDataStore.setTransAbs("这是存证说明");
//		这是签约完成后回调通知的连接
        sxqDataStore.setCallBackUrl("请求地址");
//      是否按照设置的签约顺序进行签约（默认false）
        sxqDataStore.setSignSequence(false);
//      是否对非签约人员展示合同预览（默认false）
        sxqDataStore.setShowSnapshot(false);
//      是否强制要求签约人在签署合同完成后设置登陆密码（默认false）
        sxqDataStore.setSignatoryAddPassword(false);
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("甲方");
		//签约方证件号
		sxqSignatory1.setCertNo("430511198702173516");
		//证件类型
		sxqSignatory1.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.NO.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setPhone("15923641267");
//		sxqSignatory1.setEmail("test1@qq.com");
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(544d);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);
		//签约方签约的截止时间（如果截止时间设置为空，则默认设置为七天）
		sxqSignatory1.setValidTime(DateUtil.getAfterDay(new Date(), 3));
		//签章定位关键词（与x.y 必须二选一）
//		sxqSignatory1.setKeywords("甲方签章");
		//章的用途(签章类型为企业才有效)
//		sxqSignatory1.setSealPurpose("合同专用章");
		//签章姓名是否掩码 （为true时仅显示姓，其余的 * 号代替）
//		sxqSignatory1.setRealNameMask(true);
		//签章证件号是否掩码 （为true时后四位用 * 号代替）
//		sxqSignatory1.setCertNoMask(true);
//		是否需要省心签进行实名认证（默认true，
//		仅对传输了实名信息的用户有效，未传输实名信息则必定进行实名认证）
//		sxqSignatory1.setSxqVerified(true);
//		签约顺序（仅当sxqDataStore.setSignSequence(true);时有效）
//		sxqSignatory1.setSignSequenceNo(0);
//		该签约方设置下一个签约方
//		sxqSignatory1.setSetNext(false);
//		使用该签约方的审批流
//		sxqSignatory1.setUseApproval(false);
//		使用该签约方的审批流编号（仅sxqSignatory1.setUseApproval(true);时有效）
//		sxqSignatory1.setApprovalNo(null);



		//乙方
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("乙方");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.NO.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory2.setPhone("15923641266");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(377d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(544d);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);

		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);

//		发送请求
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * 电子签约（企业和个人签约）
	 */
	@Test
	public void signaturePersonalAndEnterprise(){
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		String filePath = "C:\\Users\\11044\\Desktop\\test.pdf";
//		把pdf文件放进参数中
		getFileToPath(filePath, order);
//		用户业务编号
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
//		合同名称
		sxqDataStore.setStoreName("《合同名称》");
//		是否公开
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
//		签约的说明
		sxqDataStore.setTransAbs("这是存证说明");
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("甲方");
		//签约方证件号
		sxqSignatory1.setCertNo("430511198702173516");
		//证件类型
		sxqSignatory1.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setPhone("15923641267");
		sxqSignatory1.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(544d);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);

		//乙方
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("乙方企业账户账户");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.OFFICIAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.ENTERPRISE.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory2.setPhone("15923641265");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("91500000MA5UCYU7XX");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.BUSINESS_LICENCE.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(377d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(544d);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);

		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);

//		发送请求
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * 电子签约（企业和企业签约）
	 */
	@Test
	public void signatureEnterpriseAndEnterprise(){
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		String filePath = "C:\\Users\\11044\\Desktop\\test.pdf";
//		把pdf文件放进参数中
		getFileToPath(filePath, order);
//		用户业务编号
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
//		合同名称
		sxqDataStore.setStoreName("《合同名称》");
//		是否公开
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
//		签约的说明
		sxqDataStore.setTransAbs("这是存证说明");
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("甲方企业账户");
		//签约方证件号
		sxqSignatory1.setCertNo("91500000MA5UCYU7XY");
		//证件类型
		sxqSignatory1.setCertType(CertTypeEnum.INSTITUTION_CODE.getCode());
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.OFFICIAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.ENTERPRISE.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setPhone("15923641264");
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);

		//乙方
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("乙方企业账户");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.OFFICIAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.ENTERPRISE.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory2.setPhone("15923641265");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("91500000MA5UCYU7XX");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.BUSINESS_LICENCE.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(377d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);

		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);

//		发送请求
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * 电子签约（多人签约）
	 */
	@Test
	public void signatureMultiplePeople(){
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		String filePath = "C:\\Users\\11044\\Desktop\\test.pdf";
//		把pdf文件放进参数中
		getFileToPath(filePath, order);
//		用户业务编号
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
//		合同名称
		sxqDataStore.setStoreName("《合同名称》");
//		是否公开
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
//		签约的说明
		sxqDataStore.setTransAbs("这是存证说明");
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("甲方");
		//签约方证件号
		sxqSignatory1.setCertNo("430511198702173516");
		//证件类型
		sxqSignatory1.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setPhone("15923641267");
//		sxqSignatory1.setEmail("test1@qq.com");
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);
		//签章定位关键词（与x.y 必须二选一）
//		sxqSignatory1.setKeywords("甲方签章");
		//章的用途(签章类型为企业才有效)
//		sxqSignatory1.setSealPurpose("合同专用章");
		//签章姓名是否掩码 （为true时仅显示姓，其余的 * 号代替）
//		sxqSignatory1.setRealNameMask(true);
		//签章证件号是否掩码 （为true时后四位用 * 号代替）
//		sxqSignatory1.setCertNoMask(true);

		//乙方第一个签约人
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("乙方");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory2.setPhone("15923641266");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(300d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);

		//乙方第二个签约人
		SxqSignatory sxqSignatory3 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory3.setRealName("乙方2");
		// 签章类型 必填
		sxqSignatory3.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory3.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory3.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory3.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory3.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory3.setPhone("15923641263");
		//签约方证件号 选填
		sxqSignatory3.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory3.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory3.setSignatureX(440d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory3.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory3.setSignaturePage(1);

		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);
		sxqSignatorylist.add(sxqSignatory3);

//		发送请求
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * 电子签约（多方签约，每个签约方支持多个签约人）
	 */
	@Test
	public void MutiplePartiesSignMultiplePeople(){
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		String filePath = "C:\\Users\\11044\\Desktop\\test.pdf";
//		把pdf文件放进参数中
		getFileToPath(filePath, order);
//		用户业务编号
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
//		合同名称
		sxqDataStore.setStoreName("《合同名称》");
//		是否公开
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
//		签约的说明
		sxqDataStore.setTransAbs("这是存证说明");
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("甲方");
		//签约方证件号
		sxqSignatory1.setCertNo("430511198702173516");
		//证件类型
		sxqSignatory1.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setPhone("15923641267");
//		sxqSignatory1.setEmail("test1@qq.com");
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);
		//签章定位关键词（与x.y 必须二选一）
//		sxqSignatory1.setKeywords("甲方签章");
		//章的用途(签章类型为企业才有效)
//		sxqSignatory1.setSealPurpose("合同专用章");
		//签章姓名是否掩码 （为true时仅显示姓，其余的 * 号代替）
//		sxqSignatory1.setRealNameMask(true);
		//签章证件号是否掩码 （为true时后四位用 * 号代替）
//		sxqSignatory1.setCertNoMask(true);

		//乙方第一个签约人
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("乙方");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory2.setPhone("15923641266");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(300d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);

		//乙方第二个签约人
		SxqSignatory sxqSignatory3 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory3.setRealName("乙方2");
		// 签章类型 必填
		sxqSignatory3.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory3.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory3.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory3.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory3.setGroup(GroupsEnum.PARTY_B);
		//签约人手机邮箱 选填
		sxqSignatory3.setPhone("15923641263");
		//签约方证件号 选填
		sxqSignatory3.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory3.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory3.setSignatureX(440d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory3.setSignatureY(545d);
		//签章页 （不填时默认最后一页）
		sxqSignatory3.setSignaturePage(1);

		//丙方
		SxqSignatory sxqSignatory4 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory4.setRealName("丙方");
		// 签章类型 必填
		sxqSignatory4.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory4.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory4.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory4.setSignatoryTime("2020-08-06");
		//签约方 必填
		sxqSignatory4.setGroup(GroupsEnum.PARTY_C);
		//签约人手机邮箱 选填
		sxqSignatory4.setPhone("15923641262");
		//签约方证件号 选填
		sxqSignatory4.setCertNo("430511198702173516");
		//填了证件号就必选填证件类型
		sxqSignatory4.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory4.setSignatureX(106d);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory4.setSignatureY(670d);
		//签章页 （不填时默认最后一页）
		sxqSignatory4.setSignaturePage(1);

		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);
		sxqSignatorylist.add(sxqSignatory3);
		sxqSignatorylist.add(sxqSignatory4);

//		发送请求
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * @param filePath 	pdf文件路径
	 * @param order		参数实体
	 */
	private void getFileToPath(String filePath, SignatoryApiOrder order){
		try {
			order.setPdfFileBase64("demo8.pdf@" + new BASE64Encoder().encodeBuffer(Files.readAllBytes(Paths.get(filePath))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件存证
	 */
	@Test
	public void fileStore(){
        StoreApiOrder order = new StoreApiOrder();
        try {
            String filePath = "E:\\ichaoj\\innerCA\\signPDF\\demo10.pdf";
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			BASE64Encoder encoder = new BASE64Encoder();
            order.setFileBase64(encoder.encodeBuffer(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //保全名称
        order.setStoreName("mystorae");
        //文件名
        order.setFileName("abaac.pdf");
        //是否公开
        order.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
        ResultBase result = sxqClient.filePreservation(order);
        System.out.println(result.toString());
	}

	/**
	 * 存证文件取回
	 */
	@Test
	public void fetchFile() throws IOException {
        byte[] fileContent = sxqClient.downloadFile("1046561");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\11044\\Desktop\\bbb.pdf");
        outputStream.write(fileContent);
    }

    /**
	 * 司法存证
     * @author SOFAS
     * @description  新的存证接口测试用例
     * @date  2019/9/19
     * @return void
     **/
    @Test
	public void ocsv(){
    	/*创建实例*/
		SxqClient sxqClient = new SxqClient("您的appKey","您的appSecret", Env.LOCAL);
    	/*创建最外层元素*/
		ArrayList<Ocsv> ocsvs = new ArrayList<>();
		Ocsv ocsv = new Ocsv("测试key", "测试value", OcsvTypeEnum.INFOMATION.getCode());
    	/*创建元素下面的子元素，展示信息下面的图片等*/
		ArrayList<Ocsv> l1 = new ArrayList<>();
		Ocsv ocsv1 = new Ocsv("测试子元素key", "测试子元素value", OcsvTypeEnum.INFOMATION.getCode());
		Ocsv ocsv2 = new Ocsv("农事灌溉图", "https://oss.sxqian.com/app/mock/server/images/404_icon.png", OcsvTypeEnum.FILE.getCode(), "农事灌溉图");
		l1.add(ocsv2);
		l1.add(ocsv1);
		/*将子元素加入到外层元素内*/
		ocsv.setSubOcsv(l1);
		ocsvs.add(ocsv);
		/*发送请求*/
		ResultInfo resultInfo = sxqClient.ocsv(ocsvs, null, StoreVisibleEnum.PUBLIC.getCode(), "http://127.0.0.1:7878/api/callback.json", "测试存证名称");
		String s = JSONObject.toJSONString(resultInfo);
		System.out.println(s);
	}

	/**
	 * @author SOFAS
	 * @description 查询appkey用户的自定义logo
	 * @date  2020/4/1
	 * @return void
	 **/
	@Test
	public void queryCustomizedLogo(){
		ResultInfo resultInfo = sxqClient.queryCustomizedLogo();
		System.out.println(resultInfo);
		System.out.println(JSONObject.toJSONString(resultInfo));
	}

	/**
	 * @author SOFAS
	 * @description 设置appkey用户的自定义logo
	 * @date  2020/4/1
	 * @return void
	 **/
	@Test
	public void setCustomizedLogo(){
		ResultInfo resultInfo = sxqClient.setCustomizedLogo("C:\\Users\\admin\\Desktop\\AA.png");
		System.out.println(resultInfo);
		System.out.println(JSONObject.toJSONString(resultInfo));
	}
}
