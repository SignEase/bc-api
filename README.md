# 云存API 接入文档

**简要描述：**

云存链接入文档.


## 接入步骤
1. 创建云存账户
2. 向云存申请一个appKey 和 appSecret
3. 集成到项目中，maven项目可以直接使用下面的pom配置引入
```
	todo
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
 ~

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


- 备注：无




 **返回成功示例**

```
{success:"true", message:"保存成功"}
```


 **返回失败示例**

```
{success:"false", message:"签名校验失败"}
```

 **返回参数说明**


|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|success    |boolean     |否 |  | 是否成功 true 为成功，false 为失败  |
|message |String |否 |    |   描述  |


- 备注：无



 

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





