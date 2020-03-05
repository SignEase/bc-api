package com.ichaoj.sxq.client;


import com.ichaoj.sxq.client.beans.*;
import com.ichaoj.sxq.client.compoment.BusinessNumberUtil;
import com.ichaoj.sxq.client.compoment.ResultBase;
import com.ichaoj.sxq.client.compoment.StoreResult;
import com.ichaoj.sxq.client.enums.*;
import com.ichaoj.sxq.client.compoment.ResultInfo;
import com.yiji.openapi.tool.fastjson.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SxqClientTest {

	public static SxqClient sxqClient;
	
	@BeforeClass
	public static void init(){
		sxqClient = new SxqClient("您的appKey","您的appSecret", Env.LOCAL);
	}


	/**
	 * 测试服务器是否连通
	 */
	@Test
	public void ping(){
		ResultBase result = sxqClient.ping();
		System.out.println(result.toString());
	}

	/**
	 * 电子签约
	 */
	@Test
	public void signature(){
		BASE64Encoder encoder = new BASE64Encoder();
		SignatoryApiOrder order = new SignatoryApiOrder();
		SxqDataStore sxqDataStore = new SxqDataStore();
		try {

			String filePath = "C:\\Users\\11044\\Desktop\\aaa.pdf";
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			order.setPdfFileBase64("demo8.pdf@"+encoder.encodeBuffer(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//合同基本信息
		sxqDataStore.setUserBizNumber(BusinessNumberUtil.gainNumber());
		sxqDataStore.setStoreName("《合同名称》");
		sxqDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
        sxqDataStore.setTransAbs("这是存证说明");
		order.setSxqDataStore(sxqDataStore);

		//甲方
		SxqSignatory sxqSignatory1 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory1.setRealName("姓名");
		// 签章类型 必填
		sxqSignatory1.setSealType(SealTypeEnum.OFFICIAL.getCode());
		// 是否自动签约  必填
		sxqSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory1.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory1.setSignatoryTime("2018-2-28");
		//签约方 必填
		sxqSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约人手机邮箱 选填
		sxqSignatory1.setEmail("zjq115097475@qq.com");
//		sxqSignatory1.setPhone("15123164744");
		//签约方证件号 选填
//		yclSignatory1.setCertNo("24324342342323234243");
		//填了证件号就必选填证件类型
//		sxqSignatory1.setCertType(CertTypeEnum.INSTITUTION_CODE.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureX(100.0);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory1.setSignatureY(100.0);
		//签章页 （不填时默认最后一页）
		sxqSignatory1.setSignaturePage(1);
		//签章定位关键词（与x.y 必须二选一）
//		sxqSignatory1.setKeywords("开户银行");
		//章的用途(签章类型为企业是必填)
		sxqSignatory1.setSealPurpose("合同专用章");
		//签章姓名是否掩码 （为true时仅显示姓，其余的 * 号代替）
//		sxqSignatory1.setRealNameMask(true);
		//签章证件号是否掩码 （为true时后四位用 * 号代替）
//		sxqSignatory1.setCertNoMask(true);
		//章编号（防伪码）
//		sxqSignatory1.setSealSn("123456789456123");


		//乙方
		SxqSignatory sxqSignatory2 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory2.setRealName("姓名");
		// 签章类型 必填
		sxqSignatory2.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory2.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory2.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory2.setSignatoryTime("2018-2-28");
		//签约方 必填
		sxqSignatory2.setGroup(GroupsEnum.PARTY_B);

		//签约人手机邮箱 选填
//		yclSignatory2.setEmail("888888@qq.com");
		sxqSignatory2.setPhone("15123164744");
		//签约方证件号 选填
		sxqSignatory2.setCertNo("4355343544353ssss54");
		//填了证件号就必选填证件类型
		sxqSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureX(100.0);
		//签章y坐标 （不填写时系统自动生成）
		sxqSignatory2.setSignatureY(100.0);
		//签章页 （不填时默认最后一页）
		sxqSignatory2.setSignaturePage(1);
		sxqSignatory2.setKeywords("开户银行");

		//乙方
		SxqSignatory sxqSignatory3 = new SxqSignatory();
		// 签约人姓名 必填
		sxqSignatory3.setRealName("开户银行");
		// 签章类型 必填
		sxqSignatory3.setSealType(SealTypeEnum.PERSONAL.getCode());
		// 是否自动签约  必填
		sxqSignatory3.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		sxqSignatory3.setSignatoryUserType(SignatoryUserTypeEnum.PERSONAL.getCode());
		// 签约时间 必填
		sxqSignatory3.setSignatoryTime("2018-2-28");
		//签约方 必填
		sxqSignatory3.setGroup(GroupsEnum.PARTY_B);

		//签约人手机邮箱 选填
//		yclSignatory3.setEmail("2222222@qq.com");
		sxqSignatory3.setPhone("15123164744");
		//签约方证件号 选填
		sxqSignatory3.setCertNo("4355343544353");
		//填了证件号就必选填证件类型
		sxqSignatory3.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());


		List<SxqSignatory> sxqSignatorylist = order.getSxqSignatoryList();
		sxqSignatorylist.add(sxqSignatory1);
		sxqSignatorylist.add(sxqSignatory2);
		sxqSignatorylist.add(sxqSignatory3);
		sxqSignatory3.setSignatureX(20.0);
		sxqSignatory3.setSignatureY(20.0);
		sxqSignatory3.setSignaturePage(2);
		StoreResult result = sxqClient.signatory(order);
		System.out.println(result.toString());
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
        byte[] fileContent = sxqClient.downloadFile("YC0001046427");
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
}
