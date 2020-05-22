# 省心签API 接入文档

本文档主要阐述如何对接省心签API。`master`分支基于jdk1.8，`jdkv1.7`基于jdk1.7以上版本可运行，所有请求均为 POST 请求


## 接入步骤
1. 创建并登录省心签账户，[官网网址](https://sxqian.com)。
2. 在`账户管理`->`基本资料`里申请并获取`AppKey`和`AppSecret`。
3. 下载并集成sxq-client到本地工程中。Maven项目可以按照如下pom配置导入。
4. 参考`com.ichaoj.sxq.client.SxqClientTest`里的用例进行调试。
5. 请求参数与请求事例参数有出入请以请求参数为准，请求事例含有的参数而请求参数中没有的参数为sdk生成。

```
仓库地址：
<repositories>
    <repository>
        <id>sxq-repo</id>
        <name>sxq repository</name>
        <url>http://nexus.ichaoj.com/nexus/content/groups/public/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
<dependency>
    <groupId>com.ichaoj.sxq</groupId>
    <artifactId>sxq-sdk-java</artifactId>
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

#### 请求地址

|环境          |      HTTPS请求环境地址
|:----         |:-------   
|正式环境      |https://sxqian.com   
|测试环境      |https://mock.sxqian.com

---

#### 接口一 PING

测试服务器是否连通

##### *具体请求*
```
/api/ping.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/ping.json
```

##### *参数*
```
无
```

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
|字段          |      注释
|:----         |:-------   
|success      |是否成功，true为成功，false为失败
|message      |返回信息

##### *示例代码*
```
com.ichaoj.sxq.client.SxqClientTest#ping
```
---

---

#### 接口二 用户快速实名认证接口

给用户（个人、企业）进行注册并且实名认证

##### *具体请求*
```
/api/realNameCertification.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/realNameCertification.json?realName=张三
&certNo=4355343544353ssss54
&mobile=13511111111
&type=GR
```

##### *参数*
```
|字段                   |       类型|可否空  |默认    |注释              |
|:----                 |:-------   |:---   |---    |------             |
|realName              |String     |否     |       | [企业法人]姓名    |
|certNo                |String     |否     |       | [法人]身份证号    |
|enterpriseRealName    |String     |是     |       | 企业名称         |
|enterpriseCertNo      |String     |是     |       |  企业证书号      |
|enterpriseCertType    |String     |是     |       | 企业证书类型      |
|mobile                |String     |是     |       |  [法人]手机号    |
|mail                  |String     |是     |       |  [法人]邮箱      |
|type                  |String     |否     |       |  个人用户("GR") | 企业用户 ("JG") |
```

##### *请求成功*

```
{
success:"true", 
message:"实名认证成功"
}
```

##### *请求失败*

```
{
success:"false",
message:"invalid idcard"
}
```

##### *返回参数说明*
|字段          |      注释
|:----         |:-------   
|success      |是否成功，true为成功，false为失败
|message      |返回信息

##### *示例代码*
```
com.ichaoj.sxq.client.SxqClientTest#realNameAuthPerson
```
---


#### 接口三 文件保全

对文件进行保全，目前仅支持`Base64`编码格式。

##### *具体请求*
```
/api/filePreservation.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/filePreservation.json?fileName=abaac.pdf
&sign=704c5227a18151f5ab72edd4739a12fb
&isPublic=PUBLIC
&appKey=%E6%82%A8%E7%9A%84appKey
&appSecret=%E6%82%A8%E7%9A%84appSecret
&storeName=mystorae
&fileBase64=保全文件的base64
```

##### *请求参数*

|字段            |       类型|空      |默认    |注释        |
|:----          |:-------   |:---   |---    |------      |
|fileBase64     |String     |否      |       | 文件内容    |
|storeName      |String     |否      |       | 存储名称    |
|fileName       |String     |否      |       | 文件名称    |
|isPublic       |String     |否      |       |  是否公开   |
|appKey         |String     |否      |       | 用户appkey    |
|appSecret      |String     |否      |       |  用户appSecret   |


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

#### 接口四 电子签约

用户发起一次电子签约，签约调用方必须包含甲方。可以有`多签约人`和`多签约方`，目前支持`自动签约`和`手动签约`两种方式。
- 一次电子签约过程中，可以为不同的签约人选择`自动签约`与`手动签约`，即一份签约文件里面可以存在多种签约方式。
- 自动签约：为签约人指定`自动签约`时，将不会发送签约邀请链接，系统会自动为该签约人完成签约并加盖印章。
- 手动签约: 为签约人指定`手动签约`时，系统将会发送一条签约邀请给该签约人。通过邮件或短信进行发送，用户需根据指引完成签约。
- 信息脱敏：可以为用户的姓名和身份证号等敏感信息设置是否使用掩码进行脱敏。`realNameMask-姓名掩码`和`certNoMask-身份证掩码`: 在SignatoryApiOrder类中配置是全局的，也可以在YclSignatory类为每个签约人单独配置。

##### *具体请求*
```
/api/signatory.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/signatory.json?pdfFileBase64=demo8.pdf%40PDF文件的base64
&yclSignatoryList[1].certType=ID
&yclSignatoryList[0].groupChar=a
&yclSignatoryList[2].certNo=4355343544353
&yclSignatoryList[1].sealType=PERSONAL
&sign=e393bc5aa0bca81034ce0a59532ef26f
&yclDataStore.storeName=%E3%80%8A%E5%90%88%E5%90%8C%E5%90%8D%E7%A7%B0%E3%80%8B
&yclSignatoryList[1].keywords=%E5%BC%80%E6%88%B7%E9%93%B6%E8%A1%8C
&yclSignatoryList[1].certNo=4355343544353ssss54
&yclSignatoryList[2].signatoryTime=2018-2-28
&yclSignatoryList[0].realName=%E5%A7%93%E5%90%8D
&yclSignatoryList[1].realName=%E5%A7%93%E5%90%8D
&yclSignatoryList[1].groupChar=b
&yclSignatoryList[2].signatoryAuto=YES
&yclSignatoryList[0].groupName=%E7%94%B2%E6%96%B9
&yclSignatoryList[0].signaturePage=1
&yclSignatoryList[1].signatoryTime=2018-2-28
&yclSignatoryList[1].phone=15123164744
&yclSignatoryList[1].signatoryUserType=PERSONAL
&yclDataStore.isPublic=PUBLIC
&yclSignatoryList[0].sealType=OFFICIAL
&yclSignatoryList[0].signatoryUserType=PERSONAL
&yclSignatoryList[1].groupName=%E4%B9%99%E6%96%B9
&yclSignatoryList[2].signatoryUserType=PERSONAL
&yclSignatoryList[1].signaturePage=1
&yclDataStore.userBizNumber=20200305175927990718
&yclSignatoryList[2].certType=ID
&yclSignatoryList[2].groupChar=b
&yclSignatoryList[2].phone=15123164744
&yclSignatoryList[1].signatoryAuto=YES
&yclSignatoryList[2].sealType=PERSONAL
&yclSignatoryList[0].signatoryTime=2018-2-28
&yclSignatoryList[2].signaturePage=2
&yclSignatoryList[0].email=zjq115097475%40qq.com
&yclSignatoryList[2].signatureY=20.0
&yclSignatoryList[2].groupName=%E4%B9%99%E6%96%B9
&yclSignatoryList[2].signatureX=20.0
&yclSignatoryList[2].realName=%E5%BC%80%E6%88%B7%E9%93%B6%E8%A1%8C
&yclSignatoryList[0].signatoryAuto=YES
&yclDataStore.appSecret=%E6%82%A8%E7%9A%84appSecret
&yclSignatoryList[0].signatureY=100.0
&yclSignatoryList[0].sealPurpose=%E5%90%88%E5%90%8C%E4%B8%93%E7%94%A8%E7%AB%A0
&yclSignatoryList[0].signatureX=100.0
&yclSignatoryList[1].signatureY=100.0
&yclDataStore.appKey=%E6%82%A8%E7%9A%84appKey
&yclSignatoryList[1].signatureX=100.0
```
##### *请求参数*

详见SignatoryApiOrder.java

|字段|类型|空|默认|注释|
|:----          |:-------       |:---|---|------                                           |
|pdfFileBase64  |String         |否  |   | 文件内容（格式要求为: 文件名 + @ + 文件的Base64编码）   |
|yclDataStore   |YclDataStore   |否  |   | 合同基本信息                                       |
|yclSignatory   |YclSignatory   |否  |   | 签约人信息                                        |
|realNameMask   |Boolean        |是  |   | true: 所有签约人姓名打掩码。仅显示姓，其余的显示*号      |
|certNoMask     |Boolean        |是  |   | true: 所有签约人证件号打掩码。后四位显示*号          |
|appKey         |String         |否  |   | 用户appkey    |
|appSecret      |String         |否  |   |  用户appSecret   |


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

---

#### 接口五 司法存证
将一组数据和文件进行上链存证。

##### *具体请求*
```
/api/ocsv.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/ocsv.json
{
    "appKey":"您的appKey",
    "appSecret":"您的appSecret",
    "callBackUrl":"http://127.0.0.1:7878/api/callback.json",
    "data":"[{"name":"测试key","subOcsv":[{"fileName":"农事灌溉图","name":"农事灌溉图","type":"FILE","value":"https://oss.sxqian.com/app/mock/server/images/404_icon.png"},{"name":"测试子元素key","type":"INFOMATION","value":"测试子元素value"}],"subOcsvStr":"[{\"fileName\":\"农事灌溉图\",\"name\":\"农事灌溉图\",\"type\":\"FILE\",\"value\":\"https://oss.sxqian.com/app/mock/server/images/404_icon.png\"},{\"name\":\"测试子元素key\",\"type\":\"INFOMATION\",\"value\":\"测试子元素value\"}]","type":"INFOMATION","value":"测试value"}]",
    "env":"https://mock.sxqian.com",
    "isPublic":"PUBLIC",
    "sign":"60f4a817ea220fba93523687b4a491d4",
    "storeName":"测试存证名称"
}
```
##### *请求参数*
|字段          |类型          |可为空        |默认          |注释          |
| ------------ | ------------ | ------------ | ------------ | ------------ |
|ocsvs         |List          |否            |              |存证内容数组               |
|storeId       |Long          |是            |null          |存储编号               |
|isPublic      |String        |是            |PRIVATE       |是否公开               |
|callback      |String        |是            |null          |司法存证完成后的回调接口|
|storeName     |String        |是            |null          |设置存证名称（只有当storeId 的值为空的时候，storeName才有效）|
|appKey        |String        |否            |              | 用户appkey    |
|appSecret     |String        |否            |              |  用户appSecret   |

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

#### 接口六 取回文件
取回电子签约或存证的文件，返回的是数据流。

##### *具体请求*
```
/api/fileNotary.json
```

##### *请求示例*
```
https://mock.sxqian.com/api/fileNotary.json?appKey=%E6%82%A8%E7%9A%84appKey
&appSecret=%E6%82%A8%E7%9A%84appSecret
&storeNo=YC0001046427
```

##### *参数*

|字段|类型|可为空|默认|注释|
| ------------ | ------------ | ------------ | ------------ | ------------ |
|storeNo   |String   |否   |   |存储编号   |
|appKey         |String         |否  |   | 用户appkey    |
|appSecret      |String         |否  |   |  用户appSecret   |
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


#### 接口七 查询已设置的自定义logo
查询已设置的自定义logo，返回logo的预览网址

##### *具体请求*
```
/api/queryCustomizedLogo.json
```

##### *请求header*
```
x-sxq-open-accesstoken: 您的appkey
x-sxq-open-accesssecret: 您的appsecret
```

##### *请求示例*
```
https://mock.sxqian.com/api/queryCustomizedLogo.json
```

##### *参数*
无

##### *请求成功示例*
```
{
    "code":200,
    "data":"logo预览地址",
    "message":"查询成功",
    "success":true
}
```

##### *返回参数说明*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|success  |boolean  |否 |    |   是否成功 true 为成功，false 为失败  |
|message  |String   |否 |    |   描述，失败时为失败原因  |
|data     |String   |是 |    |   logo预览地址  |
|code     |Integer  |是 |    |   请求码  |

##### *请求失败示例*
{
    "code":500,
    "message":"查询失败",
    "success":false
}

##### *返回参数说明*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|success  |boolean  |否 |    |   是否成功 true 为成功，false 为失败  |
|message  |String   |否 |    |   描述，失败时为失败原因  |
|code     |Integer  |是 |    |   请求码  |

##### *示例代码*
com.ichaoj.sxq.client.SxqClientTest#queryCustomizedLogo


#### 接口八 设置或修改的自定义logo
设置或修改的自定义logo

##### *具体请求*
```
/api/setCustomizedLogo.json
```

##### *请求header*
```
x-sxq-open-accesstoken: 您的appkey
x-sxq-open-accesssecret: 您的appsecret
```

##### *请求示例*
```
https://mock.sxqian.com/api/setCustomizedLogo.json
{
    "logoBase64":自定义logo的base64,
}
```

##### *参数*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|logoBase64  |String  |否 |    |   自定义logo文件转换成的base64  |

##### *请求成功示例*
```
{
    "code":200,
    "message":"设置自定义logo成功",
    "success":true
}
```

##### *返回参数说明*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|success  |boolean  |否 |    |   是否成功 true 为成功，false 为失败  |
|message  |String   |否 |    |   描述，失败时为失败原因  |
|code     |Integer  |是 |    |   请求码  |

##### *请求失败示例*
{
    "code":500,
    "message":"设置失败",
    "success":false
}

##### *返回参数说明*
字段      |类型     |空|默认|注释|
|:----    |:------- |:--- |---|------      |
|success  |boolean  |否 |    |   是否成功 true 为成功，false 为失败  |
|message  |String   |否 |    |   描述，失败时为失败原因  |
|code     |Integer  |是 |    |   请求码  |

##### *示例代码*

com.ichaoj.sxq.client.SxqClientTest#setCustomizedLogo



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
