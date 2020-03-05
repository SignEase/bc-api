# 省心签API 接入文档

本文档主要阐述如何对接省心签API。`master`分支基于jdk1.8，`jdkv1.7`基于jdk1.7以上版本可运行。


## 接入步骤
1. 创建并登录省心签账户，[官网网址](https://sxqian.com)。
2. 在`账户管理`->`基本资料`里申请并获取`AppKey`和`AppSecret`。
3. 下载并集成sxq-client到本地工程中。Maven项目可以按照如下pom配置导入。
4. 参考`com.ichaoj.sxq.client.SxqClientTest`里的用例进行调试。

```
仓库地址：
<repositories>
    <repository>
        <id>sxq-repo</id>
        <name>sxq repository</name>
        <url>http://120.79.243.35:8081/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
<dependency>
    <groupId>com.ichaoj.sxq</groupId>
    <artifactId>sxq-client</artifactId>
    <version>0.1.5</version>
</dependency>
```

## API列表

#### 环境感知

Env.java中可以设置访问不同的环境，在您初始化一个YclClient时您需要传入合适的环境。

**初始化示例**
```
SxqClient sxqClient = new SxqClient("您的appKey","您的appSercret",Env.LOCAL);
```

---

#### 接口一 PING

测试服务器是否连通

##### *参数*
无

##### *请求成功*

```
{
success:"true", 
message:"服务器连通"
}
```

##### *请求失败*

```
{
success:"false",
message:"Connection refused: connect"
}
```

##### *返回参数说明*
无

##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#ping

---

#### 接口二 文件保全

对文件进行保全，目前仅支持`Base64`编码格式。

##### *请求参数*

|字段            |       类型|空      |默认    |注释        |
|:----          |:-------   |:---   |---    |------      |
|fileBase64     |String     |否      |       | 文件内容    |
|storeName      |String     |否      |       | 存储名称    |
|fileName       |String     |否      |       | 文件名称    |
|isPublic       |String     |否      |       |  是否公开   |


##### *请求成功*

```
{
success:"true", 
message:"保存成功", 
storeNo:"YC0000000662"
}
```

##### *请求失败*

```
{
success:"false", 
message:"失败信息"
}
```

##### *返回参数说明*

|字段        |类型        |空   |默认|注释          |
|:----      |:-------   |:--- |--- |------      |
|success    |boolean    |否   |    | 是否成功，true为成功，false为失败 |
|message    |String     |否   |    |   描述      |
|storeNo    |String     |否   |    |   存证编号  |


##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#fileStore

---

#### 接口三 电子签约

用户发起一次电子签约，签约调用方必须包含甲方。可以有`多签约人`和`多签约方`，目前支持`自动签约`和`手动签约`两种方式。
- 一次电子签约过程中，可以为不同的签约人选择`自动签约`与`手动签约`，即一份签约文件里面可以存在多种签约方式。
- 自动签约：为签约人指定`自动签约`时，将不会发送签约邀请链接，系统会自动为该签约人完成签约并加盖印章。
- 手动签约: 为签约人指定`手动签约`时，系统将会发送一条签约邀请给该签约人。通过邮件或短信进行发送，用户需根据指引完成签约。
- 信息脱敏：可以为用户的姓名和身份证号等敏感信息设置是否使用掩码进行脱敏。`realNameMask-姓名掩码`和`certNoMask-身份证掩码`: 在SignatoryApiOrder类中配置是全局的，也可以在YclSignatory类为每个签约人单独配置。

##### *请求参数*

详见SignatoryApiOrder.java

|字段|类型|空|默认|注释|
|:----          |:-------       |:---|---|------                                           |
|pdfFileBase64  |String         |否  |   | 文件内容（格式要求为: 文件名 + @ + 文件的Base64编码）   |
|yclDataStore   |YclDataStore   |否  |   | 合同基本信息                                       |
|yclSignatory   |YclSignatory   |否  |   | 签约人信息                                        |
|realNameMask   |Boolean        |是  |   | true: 所有签约人姓名打掩码。仅显示姓，其余的显示*号      |
|certNoMask     |Boolean        |是  |   | true: 所有签约人证件号打掩码。后四位显示*号          |


##### *请求成功*

```
{
success:"true", 
message:"保存成功", 
storeNo:"YC0000000662"
}
```

##### *请求失败*

```
{
success:"false", 
message:"错误原因", 
storeNo:""
}
```

##### *返回参数说明*

|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|success    |boolean     |否 |  | 是否成功 true 为成功，false 为失败  |
|message |String |否 |    |   描述  |
|storeNo |String |否 |    |   存证编号  |


##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#signature
```
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
	
	public StoreResult signatory(SignatoryApiOrder order) {
            StoreResult resultBase = new StoreResult();
            try {
                order.check();
            } catch (Exception e) {
                resultBase.setMessage(e.getMessage());
                return resultBase;
            }
    
            Map<String, String> params = new HashMap<>();
            params.put("yclDataStore.appKey", this.appKey);
            params.put("yclDataStore.appSecret", this.appSecret);
            params.put("pdfFileBase64", order.getPdfFileBase64());
    
            params.put("yclDataStore.userBizNumber", order.getSxqDataStore().getUserBizNumber());
            params.put("yclDataStore.storeName", order.getSxqDataStore().getStoreName());
            params.put("yclDataStore.isPublic", order.getSxqDataStore().getIsPublic());
    
            for (int i = 0; i < order.getSxqSignatoryList().size(); i++) {
    
                if (order.getRealNameMask() != null) {
                    order.getSxqSignatoryList().get(i).setRealNameMask(order.getRealNameMask());
                }
                if (order.getCertNoMask() != null) {
                    order.getSxqSignatoryList().get(i).setCertNoMask(order.getCertNoMask());
                }
                // 设置必填参数
                params.put("yclSignatoryList[" + i + "].realName", order.getSxqSignatoryList().get(i).getRealName());
                params.put("yclSignatoryList[" + i + "].sealType", order.getSxqSignatoryList().get(i).getSealType());
                params.put("yclSignatoryList[" + i + "].signatoryAuto", order.getSxqSignatoryList().get(i).getSignatoryAuto());
                params.put("yclSignatoryList[" + i + "].signatoryUserType", order.getSxqSignatoryList().get(i).getSignatoryUserType());
                params.put("yclSignatoryList[" + i + "].signatoryTime", order.getSxqSignatoryList().get(i).getSignatoryTime());
                params.put("yclSignatoryList[" + i + "].groupName", order.getSxqSignatoryList().get(i).getGroupName());
                params.put("yclSignatoryList[" + i + "].groupChar", order.getSxqSignatoryList().get(i).getGroupChar());
                // 设置可选参数
    
    
                if (order.getSxqSignatoryList().get(i).getEmail() != null) {
                    params.put("yclSignatoryList[" + i + "].email", order.getSxqSignatoryList().get(i).getEmail());
                }
                if (order.getSxqSignatoryList().get(i).getPhone() != null) {
                    params.put("yclSignatoryList[" + i + "].phone", order.getSxqSignatoryList().get(i).getPhone());
                }
                if (StringUtils.isNotEmpty(order.getSxqSignatoryList().get(i).getKeywords())) {
                    params.put("yclSignatoryList[" + i + "].keywords", order.getSxqSignatoryList().get(i).getKeywords());
                }
                if (order.getSxqSignatoryList().get(i).getSignatureX() != null) {
                    params.put("yclSignatoryList[" + i + "].signatureX", String.valueOf(order.getSxqSignatoryList().get(i).getSignatureX()));
                }
                if (order.getSxqSignatoryList().get(i).getSignatureY() != null) {
                    params.put("yclSignatoryList[" + i + "].signatureY", String.valueOf(order.getSxqSignatoryList().get(i).getSignatureY()));
                }
                if (order.getSxqSignatoryList().get(i).getSignaturePage() != null) {
                    params.put("yclSignatoryList[" + i + "].signaturePage", String.valueOf(order.getSxqSignatoryList().get(i).getSignaturePage()));
                }
    
                if (order.getSxqSignatoryList().get(i).getCertNo() != null) {
                    params.put("yclSignatoryList[" + i + "].certNo", order.getSxqSignatoryList().get(i).getCertNo());
                    params.put("yclSignatoryList[" + i + "].certType", order.getSxqSignatoryList().get(i).getCertType());
                }
    
                if (order.getSxqSignatoryList().get(i).getSealPurpose() != null) {
                    params.put("yclSignatoryList[" + i + "].sealPurpose", order.getSxqSignatoryList().get(i).getSealPurpose());
                }
    
                if (order.getSxqSignatoryList().get(i).getRealNameMask() != null) {
                    params.put("yclSignatoryList[" + i + "].realNameMask", order.getSxqSignatoryList().get(i).getRealNameMask() + "");
                }
    
                if (order.getSxqSignatoryList().get(i).getCertNoMask() != null) {
                    params.put("yclSignatoryList[" + i + "].certNoMask", order.getSxqSignatoryList().get(i).getCertNoMask() + "");
                }
    
                if (order.getSxqSignatoryList().get(i).getSealSn() != null) {
                    params.put("yclSignatoryList[" + i + "].sealSn", order.getSxqSignatoryList().get(i).getSealSn());
                }
            }
    
            String signStr = DigestUtil.digest(params, this.appSecret, DigestALGEnum.MD5);
            params.put("sign", signStr);
            try {
                String json = YclNetUtil.doPost(env.getCode() + SxqServiceEnum.SIGNATORY.getCode(), params, 60000, 60000);
    
                JSONObject jsonObject = JSONObject.parseObject(json);
                resultBase.setSuccess(jsonObject.getBooleanValue("success"));
                resultBase.setMessage(jsonObject.getString("message"));
                resultBase.setStoreNo(jsonObject.getString("storeNo"));
                return resultBase;
            } catch (IOException e) {
                resultBase.setMessage(e.getMessage());
                return resultBase;
            }
        }
```
---

#### 接口四 司法存证
将一组数据和文件进行上链存证。


##### *请求参数*
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|ocsvs      |List  |否   |       |存证内容数组               |
|storeId    |Long  |是   |null   |存储编号               |
|isPublic   |String|是   |PRIVATE|是否公开               |
|callback   |String|是   |null   |司法存证完成后的回调接口|
|storeName  |String|是   |null   |设置存证名称（只有当storeId 的值为空的时候，storeName才有效）|

##### *请求成功*
```
{
"code":200,
"data":1045544,
"message":"保全提交成功，正在进行保全",
"success":true
}
```

##### *请求失败*
```
{
"message":"错误原因",
"success":false
}
```

##### *返回参数说明*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|success  |boolean  |否 |    | 是否成功 true 为成功，false 为失败  |
|message  |String   |否 |    |   描述，失败时为失败原因  |
|data     |Long     |是 |    |   存证编号  |
|code     |Integer  |是 |    |   请求码  |

##### *回调监听*
在设置的回调接收接口，接收参数名为 hashCode 的参数即可获取到上链的豆匣链hash与司法链hash（因上链时间不一样，所以，回调的也不是固定的）

回调返回参数中data具体参数:

字段      |类型     |空   |注释                                 |
|:----    |:------- |:--- |------                              |
|storeId  |Long     |否   | 是否成功 true 为成功，false 为失败  |
|sepId    |Long     |否   | 描述，失败时为失败原因              |
|hash     |Long     |是   | 存证编号                           |

##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#ocsv

---

#### 接口五 取回文件
取回电子签约或存证的文件，返回的是数据流。

##### *参数*

|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|storeNo   |String   |否   |   |存储编号   |

##### *请求成功示例*
无

##### *返回参数说明*
无

##### *请求失败示例*
无

##### *返回参数说明*
无 

##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#fetchFile


#### 通用对象属性

##### *文件存证对象*
SxqDataStore.java
 
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|userBizNumber   |String   |否   |   |用户业务ID 具体生成方式请看示例代码   |
|storeName   |String   |否   |   |签约文件名称   |
|isPublic   |String   |否   |   |是否公开（PUBLIC or PRIVATE ）   |
|transAbs   |String   |是   |   |签约说明   |
|contractTemplateId   |Long   |是   |   |使用的合同模板ID（与pdfFileBase64不能同时为空）   |


##### *签约人对象*
SxqSignatory.java
 
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|realName   |String   |否   |   |签约人姓名   |
|sealType   |String   |否   |   |签章类型， 公章（official）or 私章(personal)  |
|signatoryAuto   |String   |否   |   |是否自动签约 自动（YES） or 手动（NO）   |
|signatoryUserType   |String   |否   |   |签约用户类型 个人（PERSONAL） or 企业（ENTERPRISE）   |
|signatoryTime   |String   |否   |   |签约时间 (格式：2018-12-25 14:39   |
|groupChar  |String   |否   |   |签约人所属签约方编号   |
|groupName  |String   |否   |   |签约人所属签约方名称   |
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
|certNoMask   |Boolean   |是   |   |签章证件号是否掩码 （为true时后四位用 * 号代替）|
|sealSn   |String   |是   |   |章编号（防伪码）|


##### *SignatoryApiOrder.java*
```
public class SignatoryApiOrder {

	private Pattern pattern = Pattern.compile("^\\S*?(?=@{1})");
	
	private SxqDataStore sxqDataStore;
	
	private List<SxqSignatory> sxqSignatoryList = new ArrayList<>();
	
	private String pdfFileBase64;

	/**
	 * 所有签章签约人真实姓名打码
	 */
	private Boolean realNameMask;

	/**
	 *  所有签章签约人证件号码打码
	 */
	private Boolean certNoMask;

	public String getFileName(){
		return pattern.matcher(this.pdfFileBase64).group(0);
	}


	public void check(){
			//检查合同
			Assert.notNull(sxqDataStore.getUserBizNumber(), "UserBizNumber不能为空");
			Assert.notNull(sxqDataStore.getStoreName(), "StoreName不能为空");
		Assert.notNull(sxqDataStore.getIsPublic(), "IsPublic不能为空");
		Assert.notNull(sxqSignatoryList, "签约人不能为空");

		checkSignatoryGroupNum();

		Assert.isTrue(pattern.matcher(this.pdfFileBase64).find(),"pdfFileBase64格式错误，需要格式:文件名@base64编码字符串");

		for(SxqSignatory ys: sxqSignatoryList){
			Assert.notNull(ys, "签约人不能为空");

			Assert.notNull(ys.getRealName(), "RealName不能为空");
			Assert.notNull(ys.getSignatoryTime(), "SignatoryTime不能为空");
			Assert.notNull(ys.getSignatoryAuto(), "SignatoryAuto不能为空");
			Assert.notNull(ys.getSignatoryUserType(), "SignatoryUserType不能为空");

			Assert.isTrue(isEnterpriseOrPersonal(ys.getSignatoryUserType()),"signatory_user_type参数有误！");

			checkContactWay(ys.getPhone(),ys.getEmail());
			if(StringUtils.isNotEmpty(ys.getCertNo())){
				Assert.notNull(ys.getCertType(),"CertType不能为空");
			}

			Assert.isTrue(checkSealPosition(ys),"签章定位参数错误");
			Assert.notNull(ys.getGroupChar(), "groupChar不能为空");
			Assert.notNull(ys.getGroupName(), "groupName不能为空");
			Assert.notNull(ys.getSealType(), "sealType不能为空");

		}

	}

	public List<SxqSignatory> getSxqSignatoryList() {
		return sxqSignatoryList;
	}

	public void setSxqSignatoryList(List<SxqSignatory> sxqSignatoryList) {
		this.sxqSignatoryList = sxqSignatoryList;
	}


	public SxqDataStore getSxqDataStore() {
		return sxqDataStore;
	}

	public void setSxqDataStore(SxqDataStore sxqDataStore) {
		this.sxqDataStore = sxqDataStore;
	}

	public String getPdfFileBase64() {
		return pdfFileBase64;
	}

	public void setPdfFileBase64(String pdfFileBase64) {
		this.pdfFileBase64 = pdfFileBase64;
	}

	public Boolean getRealNameMask() {
		return realNameMask;
	}

	public void setRealNameMask(Boolean realNameMask) {
		this.realNameMask = realNameMask;
	}

	public Boolean getCertNoMask() {
		return certNoMask;
	}

	public void setCertNoMask(Boolean certNoMask) {
		this.certNoMask = certNoMask;
	}

	/**
	 * 验证签章位置 关键字 与 坐标（x,y,pageNo） 必须有一个不能为空
	 */
	private boolean checkSealPosition(SxqSignatory ys){
		if(ys == null){
			return false;
		}

		if(StringUtils.isNotEmpty(ys.getKeywords())){
			return true;
		}else if(ys.getSignatureX() != null && ys.getSignatureY() != null && ys.getSignaturePage() != null){
			return true;
		}else {
			return false;
		}
	}
	/**
	 *  验证联系方式
	 */
	private void checkContactWay(String phone, String email){
		if(StringUtils.isEmpty(phone) && StringUtils.isEmpty(email)){
			throw new IllegalArgumentException("phone 与 email 必选一个");
		}

	}

	private boolean isEnterpriseOrPersonal(String signatoryUserType){
		if (signatoryUserType == null){
			return false;
		}
		return "ENTERPRISE".equalsIgnoreCase(signatoryUserType) || "PERSONAL".equalsIgnoreCase(signatoryUserType);
	}
	/**
	 * 检查签约方
	 */
	private void checkSignatoryGroupNum(){
		int minGroupNum = 2;
		if(sxqSignatoryList == null || sxqSignatoryList.isEmpty()){
			throw new IllegalArgumentException("缺少签约方");
		}

		Set<String> groupChar = new HashSet<>();
		for (SxqSignatory sxqSignatory : sxqSignatoryList) {
			groupChar.add(sxqSignatory.getGroupChar());
			if(groupChar.size() >= minGroupNum){
				return;
			}
		}
		throw new IllegalArgumentException("签约方至少需要" + minGroupNum + "方");
	}
}
```

