package com.ichaoj.ycl.client;


import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.ichaoj.ycl.client.beans.*;
import com.ichaoj.ycl.client.compoment.BusinessNumberUtil;
import com.ichaoj.ycl.client.compoment.ResultBase;
import com.ichaoj.ycl.client.compoment.StoreResult;
import com.ichaoj.ycl.client.enums.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ichaoj.ycl.client.compoment.ApiDataResult;
import sun.misc.BASE64Encoder;

import static com.ichaoj.ycl.client.enums.SignatoryUserTypeEnum.*;

public class YclClientTest {

	public static YclClient yclClient;
	
	@BeforeClass
	public static void init(){
		yclClient = new YclClient("您的appKey","您的appSecret",Env.TEST);
	}
	
	
	@Test
	public void ping(){
		ResultBase result = yclClient.ping();
		System.out.println(result.toString());
	}
	
	@Test
	public void signtory(){
		BASE64Encoder encoder = new BASE64Encoder();
		SignatoryApiOrder order = new SignatoryApiOrder();
		YclDataStore yclDataStore = new YclDataStore();
		try {

			String filePath = "E:\\ichaoj\\innerCA\\signPDF\\demo9.pdf";
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
		yclSignatory1.setSignatoryAuto(BooleanEnum.NO.getCode());
		// 签约用户类型 必填
		yclSignatory1.setSignatoryUserType(PERSONAL.getCode());
		// 签约时间 必填
		yclSignatory1.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory1.setGroup(GroupsEnum.PARTY_A);

		//签约人手机邮箱 选填
		yclSignatory1.setEmail("1111111@qq.com");
		//签约方证件号 选填
		yclSignatory1.setCertNo("4423355343544353ssss54");
		//填了证件号就必选填证件类型
		yclSignatory1.setCertType(CertTypeEnum.INSTITUTION_CODE.getCode());
		//签章x坐标 （不填写时系统自动生成）
		yclSignatory1.setSignatureX(100.0);
		//签章y坐标 （不填写时系统自动生成）
		yclSignatory1.setSignatureY(100.0);
		//签章页 （不填时默认最后一页）
		yclSignatory1.setSignaturePage(1);


		//乙方
		YclSignatory yclSignatory2 = new YclSignatory();
		// 签约人姓名 必填
		yclSignatory2.setRealName("姓名");
		// 签章类型 必填
		yclSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		yclSignatory2.setSignatoryAuto(BooleanEnum.NO.getCode());
		// 签约用户类型 必填
		yclSignatory2.setSignatoryUserType(PERSONAL.getCode());
		// 签约时间 必填
		yclSignatory2.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory2.setGroup(GroupsEnum.PARTY_B);

		//签约人手机邮箱 选填
		yclSignatory2.setEmail("2222222@qq.com");
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


		List<YclSignatory> yclSignatorylist = order.getYclSignatoryList();
		yclSignatorylist.add(yclSignatory1);
		yclSignatorylist.add(yclSignatory2);

		StoreResult result = yclClient.signatory(order);
		System.out.println(result.toString());
	}
	
	@Test
	public void store(){
        StoreApiOrder order = new StoreApiOrder();
        try {
            String filePath = "E:\\ichaoj\\innerCA\\signPDF\\demo10.pdf";
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            order.setFileBase64(Base64.getEncoder().encodeToString(bytes));
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
	
	@Test
	public void data() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ApiDataOrder order = new ApiDataOrder();
		ApiDataOrder order1 = new ApiDataOrder();
		List<ApiData> list = new ArrayList<>();
		ApiData  data = new ApiData(2018102300000012L, 597L, 17L, 17L, null, 0L,
				"YFK", "NORMAL", "BALANCE_PAYMENT", 10000L, 
				0L, 2000L, 0L, 150L, 
				1500L, 6500L, 0L, 0L, 0L, 0L, 0L, 
				0L, "QJB_201810230000096", new Date(2018,10,23,14,7,45), 600L,0L, 
				0L, 1L, "", 
				"[{\"id\":0,\"itemProductId\":145,\"itemProductName\":\"实物商品1\",\"orderId\":2018102300000012,\"picPath\":\"http://image3.cqbornsoft.com/uploadfile/productpic/2018-09/18/middleBig__3136517836.jpg\",\"postFree\":{\"amount\":0.00,\"cent\":0,\"centFactor\":100,\"currency\":\"CNY\",\"standardString\":\"0.00\"},\"proPrice\":{\"amount\":100.00,\"cent\":10000,\"centFactor\":100,\"currency\":\"CNY\",\"standardString\":\"100.00\"},\"ptTag\":\"100\",\"quantity\":1,\"rawAddTime\":1540274865000,\"totalAmountNoShipment\":{\"amount\":100.00,\"cent\":10000,\"centFactor\":100,\"currency\":\"CNY\",\"standardString\":\"100.00\"}}]",
				null, null, "YES", "MALL_APPLET", 
				new Date(2018,10,23,14,7,45), null);
		list.add(data);
		order.setApiDatas(list);
		ApiDataResult result = yclClient.data(order);
		System.out.println(result.toString());
	}
}
