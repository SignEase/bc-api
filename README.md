# 云存API 接入文档

**简要描述：**

云存链接入文档. master 分支 的基于jdk1.8运行。  jdkv1.7 基于jdk1.7以上版本可运行.


## 接入步骤
1. 创建云存账户
2. 向云存申请一个appKey 和 appSecret
3. 集成到项目中，maven项目可以直接使用下面的pom配置引入

```
	仓库地址：http://120.79.243.35:8081/nexus/content/groups/public/
	<dependency>
	  <groupId>com.ichaoj.ycl</groupId>
	  <artifactId>ycl-client</artifactId>
	  <version>0.1.2</version>
	</dependency>
```

## API 列表

**简要描述：**

您需要为不同的场景选择不同的环境。在为您提供的sdk中Env.java 中有详细的配置。在您初始化一个YclClient 时您需要传入合适的环境。

**初始化示例：**
```
	YclClient yclClient = new YclClient("您的appKey","您的appSercret",Env.LOCAL);
```

**为您提供了完整的示例，更多请见 YclClientTest.java 文件**

#### 接口一 测试服务器是否连通
**简要描述：**

- 测试服务器是否连通


**参数：**
无

 **返回成功示例**

```
{success:"true", message:"服务器连通"}
```

 **返回参数说明**
~

 **返回失败示例**

```
{
	success:"false"，
	message:"Connection refused: connect"
}
```

 **返回参数说明**
 

 **示例代码**

	public static YclClient yclClient;
	
	@BeforeClass
	public static void init(){
		yclClient = new YclClient("您的appKey","您的appSercret",Env.LOCAL);
	}

	@Test
	public void ping(){
		ResultBase result = yclClient.ping();
		System.out.println(result.toString());
	}

 **备注**

 #### 接口二 文件保全

**简要描述：**

- 对文件保全。目前仅支持Base64 编码格式进行上传。


**参数：**

|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|fileBase64    |String     |否 |  | 文件内容            |
|storeName |String |否 |    |   存储名称  |
|fileName |Stirng |否   |    |   文件名称    |
|isPublic     |String |否   |    |    是否公开     |


 **备注**




 **返回成功示例**

```
{success:"true", message:"保存成功", storeNo:"YC0000000662"}
```


 **返回失败示例**

```
{success:"true", message:"保存成功", storeNo:"YC0000000662"}
```

 **返回参数说明**


|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|success    |boolean     |否 |  | 是否成功 true 为成功，false 为失败  |
|message |String |否 |    |   描述  |
|storeNo |String |否 |    |   存证编号  |


 **备注**



 

 **示例代码**

	@Test
	public void store(){
		StoreApiOrder order = new StoreApiOrder();
		try {
			//测试读取文本中的base64编码
			BufferedReader reader = new BufferedReader(new FileReader("I:\\Freemarker.pdf"));
			//保全文件base64编码
			order.setFileBase64(reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//保全名称
		order.setStoreName("myst22or1e");
		//文件名
		order.setFileName("aab22c.pdf");
		//是否公开
		order.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
		ResultBase result = yclClient.filePreservation(order);
		System.out.println(result.toString());
	}

 #### 接口三  电子签约

**简要描述：**
- 注：签约方必须包含甲方，

- 用户可以通过云存平台发起一次电子签约，可以有**多签约人**和**多签约方**，目前支持**自动签约**和**手动签约**两种方式。一次电子签约里，可以为不同的签约人选择**自动签约**与**手动签约**，即一份签约文件里面可以存在多种签约方式。

- **自动签约：**为签约人指定**自动签约**时，将不会发送签约邀请链接。系统会自动为该签约人完成签约加上签约印章。
- **手动签约: **为签约人指定**手动签约**时，系统将会发送一条签约邀请到该签约人。如果签约人存邮箱将通过邮件的方式发送，如果存在手机将通过短信进行发送。用户需根据签约链接指引完成签约。

realNameMask : 在SignatoryApiOrder 类中配置是全局的，也可以在 YclSignatory 类为每个签约人单独配置

**参数：(SignatoryApiOrder.java)**

|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|pdfFileBase64    |String     |否 |  | 文件内容    （格式要求为: 文件名 + @ + 文件的Base64编码）        |
|yclDataStore |YclDataStore |否 |    |   合同基本信息  |
|yclSignatory |YclSignatory |否   |    |   签约人信息    |
|realNameMask |Boolean |是   |    |   true:为该笔电子签约所有签约人姓名打掩码 为true时仅显示姓，其余的 * 号代替   |
|certNoMast   |Boolean   |是   |   |true:为该笔电子签约所有签约人证件号打掩码，签章证件号是否掩码 （为true时后四位用 * 号代替）|

 **备注**




 **返回成功示例**

```
{success:"true", message:"保存成功", storeNo:"YC0000000662"}
```


 **返回失败示例**

```
{success:"false", message:"pdfFileBase64格式错误，需要格式:文件名@base64编码字符串", storeNo:""}
```

 **返回参数说明**


|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|success    |boolean     |否 |  | 是否成功 true 为成功，false 为失败  |
|message |String |否 |    |   描述  |
|storeNo |String |否 |    |   存证编号  |


 **备注**



 **示例代码**

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
		yclDataStore.setStoreName("《合同名称》"+ new Date());
		yclDataStore.setIsPublic(StoreVisibleEnum.PUBLIC.getCode());
        	yclDataStore.setTransAbs("这是存证说明"+ new Date());
		order.setYclDataStore(yclDataStore);
		// 真实姓名是否打掩码
		order.setRealNameMask(true);
		// 证件号码是否掩码
		order.setCertNoMask(true);

		//甲方
		YclSignatory yclSignatory1 = new YclSignatory();
		// 签约人姓名 必填
		yclSignatory1.setRealName("我是甲方");
		//章的用途
		yclSignatory1.setSealPurpose("合同专用章");
		// 签章类型 必填
		yclSignatory1.setSealType(SealTypeEnum.OFFICIAL.getCode());
		// 是否自动签约  必填
		yclSignatory1.setSignatoryAuto(BooleanEnum.YES.getCode());
		// 签约用户类型 必填
		yclSignatory1.setSignatoryUserType(ENTERPRISE.getCode());
		// 签约时间 必填
		yclSignatory1.setSignatoryTime("2018-2-28");
		//签约方 必填
		yclSignatory1.setGroup(GroupsEnum.PARTY_A);
		//签约方名称  必填
		yclSignatory1.setGroupName("甲");
		// 章的防伪码
        	yclSignatory1.setSealSn("fangweima2334");
		//签约人手机邮箱 选填
		yclSignatory1.setEmail("fsfxxxx@qq.com");
		//签约方证件号 选填
		yclSignatory1.setCertNo("24324342342323234243");
		//填了证件号就必选填证件类型
		yclSignatory1.setCertType(CertTypeEnum.INSTITUTION_CODE.getCode());
		//签章定位关键词
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
		yclSignatory2.setSignatoryTime("2018-12-25 14:39");
		//签约方 必填
		yclSignatory2.setGroup(GroupsEnum.PARTY_B);
		//签约方名称
		yclSignatory2.setGroupName("乙方");

		//签约人手机邮箱 选填
		yclSignatory2.setPhone("15123164744");
		//签约方证件号 选填
		yclSignatory2.setCertNo("4355343544353ssss54");
		//填了证件号就必选填证件类型
		yclSignatory2.setCertType(CertTypeEnum.IDENTITY_CARD.getCode());
		//签章x坐标
		yclSignatory2.setSignatureX(100.0);
		//签章y坐标
		yclSignatory2.setSignatureY(100.0);
		//签章页
		yclSignatory2.setSignaturePage(1);


		List<YclSignatory> yclSignatorylist = order.getYclSignatoryList();
		yclSignatorylist.add(yclSignatory1);
		yclSignatorylist.add(yclSignatory2);


		StoreResult result = yclClient.signatory(order);
		System.out.println(result.toString());
	}
	


#### 通用对象属性


 **文件基本信息 YclDataStore.java**
 
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|userBizNumber   |String   |否   |   |用户业务ID 具体生成方式请看示例代码   |
|storeName   |String   |否   |   |签约文件名称   |
|isPublic   |String   |否   |   |是否公开（PUBLIC or PRIVATE ）   |
|transAbs   |String   |是   |   |签约说明   |


 **签约人信息 YclSignatory.java**
 
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|realName   |String   |否   |   |签约人姓名   |
|sealType   |String   |否   |   |签章类型， 公章（official）or 私章(personal)  |
|signatoryAuto   |String   |否   |   |是否自动签约 自动（YES） or 手动（NO）   |
|signatoryUserType   |String   |否   |   |签约用户类型 个人（PERSONAL） or 企业（ENTERPRISE）   |
|signatoryTime   |String   |否   |   |签约时间 (格式：2018-12-25 14:39   |
|group   |GroupsEnum   |否   |   |签约方   |
|phone   |String   |是   |   |签约人手机号码（手机邮箱至少选择其中一个）   |
|email   |String   |是   |   |签约人手机邮箱 （手机邮箱至少选择其中一个）  |
|certNo   |String   |是   |   |签约方证件号   |
|certType   |String   |是   |   |签约方证件类型（填了证件号就必选填证件类型）("ID","身份证")("INSTITUTION_CODE","组织机构代码证")BUSINESS_LICENCE("BUSINESS_LICENCE","营业执照")   |
|signatureX   |String   |是   |   |签章x坐标  |
|signatureY   |Double   |是   |   |签章y坐标 |
|signaturePage   |Integer   |是   |   |签章页  |
|keywords   |String   |是   |   |签章定位关键词（与x.y 必须二选一） |
|sealPurpose   |String   |是   |   |章的用途(签章类型为企业是必填) |
|realNameMask   |Boolean   |是   |   |签章姓名是否掩码 （为true时仅显示姓，其余的 * 号代替）|
|certNoMast   |Boolean   |是   |   |签章证件号是否掩码 （为true时后四位用 * 号代替）|
|sealSn   |String   |是   |   |章编号（防伪码）|

#### 接口四 文件下载
**简要描述：**

- 获取存储或电子签约的文件，返回的是数据流


**参数：**

|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|storeNo   |String   |否   |   |存储编号   |

 **返回成功示例**

```

```

 **返回参数说明**
~

 **返回失败示例**

```

```

 **返回参数说明**
 

 **示例代码**

	public static YclClient yclClient;
	
	@BeforeClass
	public static void init(){
		yclClient = new YclClient("您的appKey","您的appSercret",Env.LOCAL);
	}

	/**
	 * 文件取回
	 */
	@Test
	public void fileDownloadTest() throws IOException {


        byte[] fileContent = yclClient.downloadFile("YC0000000336");
        FileOutputStream outputStream = new FileOutputStream("I:\\testdownload2.pdf");


       outputStream.write(fileContent);
    }

 **备注**
