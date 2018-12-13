package com.ichaoj.ycl.client;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.ichaoj.ycl.client.beans.*;
import com.ichaoj.ycl.client.compoment.BusinessNumberUtil;
import com.ichaoj.ycl.client.compoment.ResultBase;
import com.ichaoj.ycl.client.compoment.StoreResult;
import com.ichaoj.ycl.client.enums.*;
import org.junit.BeforeClass;
import org.junit.Test;

import sun.misc.BASE64Encoder;

import static com.ichaoj.ycl.client.enums.SignatoryUserTypeEnum.*;

public class YclClientTest {

	public static YclClient yclClient;
	
	@BeforeClass
	public static void init(){
		yclClient = new YclClient("您的appKey","您的appSecret",Env.TEST);

	}


	/**
	 * 测试服务器是否连通
	 */
	@Test
	public void ping(){
		ResultBase result = yclClient.ping();
		System.out.println(result.toString());
	}

	/**
	 * 电子签约
	 */
	@Test
	public void signtory(){
		BASE64Encoder encoder = new BASE64Encoder();
		SignatoryApiOrder order = new SignatoryApiOrder();
		YclDataStore yclDataStore = new YclDataStore();
		try {

			String filePath = "C:\\Users\\fan\\Desktop\\买卖合同.pdf";
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			order.setPdfFileBase64("demo8.pdf@"+encoder.encodeBuffer(bytes));

		} catch (Exception e) {
			e.printStackTrace();
		}
		//合同基本信息
		yclDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
		yclDataStore.setStoreName("《合同名称》");
		yclDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
        yclDataStore.setTransAbs("这是存证说明");
		order.setYclDataStore(yclDataStore);

		//甲方
		YclSignatory yclSignatory1 = new YclSignatory();
		// 签约人姓名 必填
		yclSignatory1.setRealName("姓名");
		// 签章类型 必填
		yclSignatory1.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		yclSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		yclSignatory1.setSignatoryUserType(PERSONAL.getCode());
		// 签约时间 必填
		yclSignatory1.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory1.setGroup(GroupsEnum.PARTY_A);
		yclSignatory1.setGroupName("甲");

		//签约人手机邮箱 选填
		yclSignatory1.setEmail("zjq115097475@qq.com");
		//签约方证件号 选填
//		yclSignatory1.setCertNo("24324342342323234243");
		//填了证件号就必选填证件类型
		yclSignatory1.setCertType(CertTypeEnum.INSTITUTION_CODE.getCode());
		//签章x坐标 （不填写时系统自动生成）
		yclSignatory1.setSignatureX(100.0);
		//签章y坐标 （不填写时系统自动生成）
		yclSignatory1.setSignatureY(100.0);
		//签章页 （不填时默认最后一页）
		yclSignatory1.setSignaturePage(1);
		yclSignatory1.setKeywords("开户银行");


		//乙方
		YclSignatory yclSignatory2 = new YclSignatory();
		// 签约人姓名 必填
		yclSignatory2.setRealName("姓名");
		// 签章类型 必填
		yclSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		yclSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		yclSignatory2.setSignatoryUserType(PERSONAL.getCode());
		// 签约时间 必填
		yclSignatory2.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory2.setGroup(GroupsEnum.PARTY_B);
		yclSignatory2.setGroupName("乙方");

		//签约人手机邮箱 选填
//		yclSignatory2.setEmail("888888@qq.com");
		yclSignatory2.setPhone("15123164744");
		//签约方证件号 选填
		yclSignatory2.setCertNo("4355343544353ssss54");
		//填了证件号就必选填证件类型
		yclSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		yclSignatory2.setSignatureX(100.0);
		//签章y坐标 （不填写时系统自动生成）
		yclSignatory2.setSignatureY(100.0);
		//签章页 （不填时默认最后一页）
		yclSignatory2.setSignaturePage(1);
		yclSignatory2.setKeywords("开户银行");

		//乙方
		YclSignatory yclSignatory3 = new YclSignatory();
		// 签约人姓名 必填
		yclSignatory3.setRealName("开户银行");
		// 签章类型 必填
		yclSignatory3.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		yclSignatory3.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		yclSignatory3.setSignatoryUserType(PERSONAL.getCode());
		// 签约时间 必填
		yclSignatory3.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory3.setGroup(GroupsEnum.PARTY_B);
		yclSignatory3.setGroupName("乙方");

		//签约人手机邮箱 选填
//		yclSignatory3.setEmail("2222222@qq.com");
		yclSignatory3.setPhone("15123164744");
		//签约方证件号 选填
		yclSignatory3.setCertNo("4355343544353");
		//填了证件号就必选填证件类型
		yclSignatory3.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());

		//签章页 （不填时默认最后一页）
//		yclSignatory3.setSignaturePage(1);
		yclSignatory3.setKeywords("开户银行");

		List<YclSignatory> yclSignatorylist = order.getYclSignatoryList();
		yclSignatorylist.add(yclSignatory1);
		yclSignatorylist.add(yclSignatory2);
		yclSignatorylist.add(yclSignatory3);

		StoreResult result = yclClient.signatory(order);
		System.out.println(result.toString());
	}

	/**
	 * 文件存证
	 */
	@Test
	public void store(){
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
        ResultBase result = yclClient.filePreservation(order);
        System.out.println(result.toString());
	}

	/**
	 * 存证文件取回
	 */
	@Test
	public void fileDownloadTest() throws IOException {


        byte[] fileContent = yclClient.downloadFile("YC0000051112");
        FileOutputStream outputStream = new FileOutputStream("I:\\testdownload2.pdf");


       outputStream.write(fileContent);
    }
}
