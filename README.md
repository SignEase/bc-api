# 省心签API 接入文档

**简要描述**

本文档主要阐述如何对接省心签API。`master`分支基于jdk1.8，`jdkv1.7`基于jdk1.7以上版本可运行。


## 接入步骤
1. 创建省心签账户
2. 申请`appKey`和`appSecret`
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

#### 接口一 PING

测试服务器是否连通

##### *参数*
无

##### *返回成功*

```
{
success:"true", 
message:"服务器连通"
}
```

**返回失败**

```
{
success:"false",
message:"Connection refused: connect"
}
```

**返回参数说明**
无

**示例代码**

com.ichaoj.sxq.client.SxqClientTest#ping



#### 接口二 文件保全

**概述**

对文件进行保全，目前仅支持`Base64`编码格式。

**参数**

|字段            |       类型|空      |默认    |注释        |
|:----          |:-------   |:---   |---    |------      |
|fileBase64     |String     |否      |       | 文件内容    |
|storeName      |String     |否      |       | 存储名称    |
|fileName       |String     |否      |       | 文件名称    |
|isPublic       |String     |否      |       |  是否公开   |


**返回成功**

```
{
success:"true", 
message:"保存成功", 
storeNo:"YC0000000662"
}
```

**返回失败**

```
{
success:"true", 
message:"保存成功", 
storeNo:"YC0000000662"
}
```

**返回参数说明**

|字段        |类型        |空   |默认|注释          |
|:----      |:-------   |:--- |--- |------      |
|success    |boolean    |否   |    | 是否成功，true为成功，false为失败 |
|message    |String     |否   |    |   描述      |
|storeNo    |String     |否   |    |   存证编号  |


**示例代码**

com.ichaoj.sxq.client.SxqClientTest#fileStore

#### 接口三 电子签约

**概述**

用户发起一次电子签约，签约调用方必须包含甲方。可以有`多签约人`和`多签约方`，目前支持`自动签约`和`手动签约`两种方式。
- 一次电子签约过程中，可以为不同的签约人选择`自动签约`与`手动签约`，即一份签约文件里面可以存在多种签约方式。
- 自动签约：为签约人指定`自动签约`时，将不会发送签约邀请链接，系统会自动为该签约人完成签约并加盖印章。
- 手动签约: 为签约人指定`手动签约`时，系统将会发送一条签约邀请给该签约人。通过邮件或短信进行发送，用户需根据指引完成签约。
- 信息脱敏：可以为用户的姓名和身份证号等敏感信息设置是否使用掩码进行脱敏。`realNameMask-姓名掩码`和`certNoMask-身份证掩码`: 在SignatoryApiOrder类中配置是全局的，也可以在YclSignatory类为每个签约人单独配置。

**请求参数**

详见SignatoryApiOrder.java

|字段|类型|空|默认|注释|
|:----          |:-------       |:---|---|------                                           |
|pdfFileBase64  |String         |否  |   | 文件内容（格式要求为: 文件名 + @ + 文件的Base64编码）   |
|yclDataStore   |YclDataStore   |否  |   | 合同基本信息                                       |
|yclSignatory   |YclSignatory   |否  |   | 签约人信息                                        |
|realNameMask   |Boolean        |是  |   | true: 所有签约人姓名打掩码。仅显示姓，其余的显示*号      |
|certNoMask     |Boolean        |是  |   | true: 所有签约人证件号打掩码。后四位显示*号          |


**返回成功**

```
{
success:"true", 
message:"保存成功", 
storeNo:"YC0000000662"
}
```

**返回失败**

```
{
success:"false", 
message:"pdfFileBase64格式错误，需要格式:文件名@base64编码字符串", 
storeNo:""
}
```

**返回参数说明**

|字段|类型|空|默认|注释|
|:----    |:-------    |:--- |---|------      |
|success    |boolean     |否 |  | 是否成功 true 为成功，false 为失败  |
|message |String |否 |    |   描述  |
|storeNo |String |否 |    |   存证编号  |


**示例代码**

com.ichaoj.sxq.client.SxqClientTest#signature


#### 接口四 司法存证
**概述**

将一组数据和文件进行上链存证。


**参数**
TODO

|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|storeNo   |String   |否   |   |存储编号   |

**返回成功示例**
TODO

**返回参数说明**
TODO

**返回失败示例**
TODO

**返回参数说明**
TODO

**回调监听**
TODO

**示例代码**

com.ichaoj.sxq.client.SxqClientTest#ocsv


#### 接口五 取回文件
**概述**

取回电子签约或存证的文件，返回的是数据流。


**参数**

|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|storeNo   |String   |否   |   |存储编号   |

**返回成功示例**
无

**返回参数说明**
无

**返回失败示例**
无

**返回参数说明**
无 

**示例代码**

com.ichaoj.sxq.client.SxqClientTest#fetchFile



#### 通用对象属性

**文件存证对象**
SxqDataStore.java
 
|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|userBizNumber   |String   |否   |   |用户业务ID 具体生成方式请看示例代码   |
|storeName   |String   |否   |   |签约文件名称   |
|isPublic   |String   |否   |   |是否公开（PUBLIC or PRIVATE ）   |
|transAbs   |String   |是   |   |签约说明   |


**签约人对象**
SxqSignatory.java
 
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
|certNoMask   |Boolean   |是   |   |签章证件号是否掩码 （为true时后四位用 * 号代替）|
|sealSn   |String   |是   |   |章编号（防伪码）|
