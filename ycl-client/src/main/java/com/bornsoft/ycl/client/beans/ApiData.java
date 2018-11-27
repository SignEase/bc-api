package com.bornsoft.ycl.client.beans;

import java.util.Date;

public class ApiData {
	
	private static final long serialVersionUID = 3258685517640902289L;
	private Long id;//主键
	private Long userId;//会员id(买家)
	private Long supplierId;//供应商id
	private Long cashierUserId;////收银员id
	private String qrCodeNo;//二维码编号
	private Long operatorId;//订单发起炒作人员id
	private String orderStatus;//订单状态
	private String orderType;//订单类型
	private String payType;//支付方式
	private Long totalAmount;//总共价格
	private Long gainMoeny;//优惠券
	private Long userDiscount;//会员折扣价

	private Long useCardId;
	private Long userPoint;//用户积分
	private Long userPointAmount;//用户积分对应的优惠价格
	private Long userBalance;//使用金额
	private Long userCash;//使用现金
	private Long payAmount;//在线支付金额
	private Long charge;//手续费
	private Long postFee;//运费
	private Long packingFee;//包装费
	private Long settlementAmount;//结算金额(卖家上账金额)
	private String paymentFlowId;//付款流水号
	private Date payFinishTime;//支付完成时间

	private Long buyMemberCardId;//购买卡卷ID
	private Long buyMemberCardExtendId;//购买卡卷背景ID
	private Long buyMemberCardGiftId;//购买卡卷礼品ID
	private Long shareUserId;//分享者ID
	private String remark;//买家备注
	private String goodsJson;//商品信息json格式，包含商品图片、名称、单价、数量
	private Date deliveryTime;//发货时间
	private Date confirmReceiptTime;//确认收货时间
	private String canAfterSale;//是否允许售后
	private String source;//来源
	private Date rawAddTime;//新增时间
	private Date rawUpdateTime;//更新时间
	
	
	public ApiData() {
		super();
	}
	
	public ApiData(Long id, Long userId, Long supplierId, Long cashierUserId, String qrCodeNo, Long operatorId,
			String orderStatus, String orderType, String payType, Long totalAmount, Long gainMoeny, Long userDiscount,
			Long userPoint, Long userPointAmount, Long userBalance, Long userCash, Long payAmount, Long charge,
			Long useCardId, Long postFee, Long packingFee, Long settlementAmount, String paymentFlowId, Date payFinishTime,
			Long buyMemberCardId, Long buyMemberCardExtendId, Long buyMemberCardGiftId,
			Long shareUserId, String remark, String goodsJson, Date deliveryTime, Date confirmReceiptTime,
			String canAfterSale, String source, Date rawAddTime, Date rawUpdateTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.supplierId = supplierId;
		this.cashierUserId = cashierUserId;
		this.qrCodeNo = qrCodeNo;
		this.operatorId = operatorId;
		this.orderStatus = orderStatus;
		this.orderType = orderType;
		this.payType = payType;
		this.totalAmount = totalAmount;
		this.gainMoeny = gainMoeny;
		this.userDiscount = userDiscount;
		this.userPoint = userPoint;
		this.userPointAmount = userPointAmount;
		this.userBalance = userBalance;
		this.userCash = userCash;
		this.payAmount = payAmount;
		this.charge = charge;
		this.postFee = postFee;
		this.packingFee = packingFee;
		this.settlementAmount = settlementAmount;
		this.paymentFlowId = paymentFlowId;
		this.payFinishTime = payFinishTime;
		this.useCardId = useCardId;
		this.buyMemberCardId = buyMemberCardId;
		this.buyMemberCardExtendId = buyMemberCardExtendId;
		this.buyMemberCardGiftId = buyMemberCardGiftId;
		this.shareUserId = shareUserId;
		this.remark = remark;
		this.goodsJson = goodsJson;
		this.deliveryTime = deliveryTime;
		this.confirmReceiptTime = confirmReceiptTime;
		this.canAfterSale = canAfterSale;
		this.source = source;
		this.rawAddTime = rawAddTime;
		this.rawUpdateTime = rawUpdateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getCashierUserId() {
		return cashierUserId;
	}

	public void setCashierUserId(Long cashierUserId) {
		this.cashierUserId = cashierUserId;
	}

	public String getQrCodeNo() {
		return qrCodeNo;
	}

	public void setQrCodeNo(String qrCodeNo) {
		this.qrCodeNo = qrCodeNo;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getGainMoeny() {
		return gainMoeny;
	}

	public void setGainMoeny(Long gainMoeny) {
		this.gainMoeny = gainMoeny;
	}

	public Long getUserDiscount() {
		return userDiscount;
	}

	public void setUserDiscount(Long userDiscount) {
		this.userDiscount = userDiscount;
	}

	public Long getUseCardId() {
		return useCardId;
	}

	public void setUseCardId(Long useCardId) {
		this.useCardId = useCardId;
	}

	public Long getUserPoint() {
		return userPoint;
	}

	public void setUserPoint(Long userPoint) {
		this.userPoint = userPoint;
	}

	public Long getUserPointAmount() {
		return userPointAmount;
	}

	public void setUserPointAmount(Long userPointAmount) {
		this.userPointAmount = userPointAmount;
	}

	public Long getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(Long userBalance) {
		this.userBalance = userBalance;
	}

	public Long getUserCash() {
		return userCash;
	}

	public void setUserCash(Long userCash) {
		this.userCash = userCash;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public Long getCharge() {
		return charge;
	}

	public void setCharge(Long charge) {
		this.charge = charge;
	}

	public Long getPostFee() {
		return postFee;
	}

	public void setPostFee(Long postFee) {
		this.postFee = postFee;
	}

	public Long getPackingFee() {
		return packingFee;
	}

	public void setPackingFee(Long packingFee) {
		this.packingFee = packingFee;
	}

	public Long getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Long settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getPaymentFlowId() {
		return paymentFlowId;
	}

	public void setPaymentFlowId(String paymentFlowId) {
		this.paymentFlowId = paymentFlowId;
	}

	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public Long getBuyMemberCardId() {
		return buyMemberCardId;
	}

	public void setBuyMemberCardId(Long buyMemberCardId) {
		this.buyMemberCardId = buyMemberCardId;
	}

	public Long getBuyMemberCardExtendId() {
		return buyMemberCardExtendId;
	}

	public void setBuyMemberCardExtendId(Long buyMemberCardExtendId) {
		this.buyMemberCardExtendId = buyMemberCardExtendId;
	}

	public Long getBuyMemberCardGiftId() {
		return buyMemberCardGiftId;
	}

	public void setBuyMemberCardGiftId(Long buyMemberCardGiftId) {
		this.buyMemberCardGiftId = buyMemberCardGiftId;
	}

	public Long getShareUserId() {
		return shareUserId;
	}

	public void setShareUserId(Long shareUserId) {
		this.shareUserId = shareUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGoodsJson() {
		return goodsJson;
	}

	public void setGoodsJson(String goodsJson) {
		this.goodsJson = goodsJson;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getConfirmReceiptTime() {
		return confirmReceiptTime;
	}

	public void setConfirmReceiptTime(Date confirmReceiptTime) {
		this.confirmReceiptTime = confirmReceiptTime;
	}

	public String getCanAfterSale() {
		return canAfterSale;
	}

	public void setCanAfterSale(String canAfterSale) {
		this.canAfterSale = canAfterSale;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getRawAddTime() {
		return rawAddTime;
	}

	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}

	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}

	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}

	@Override
	public String toString() {
		return "ApiData [id=" + id + ", userId=" + userId + ", supplierId=" + supplierId + ", cashierUserId="
				+ cashierUserId + ", qrCodeNo=" + qrCodeNo + ", operatorId=" + operatorId + ", orderStatus="
				+ orderStatus + ", orderType=" + orderType + ", payType=" + payType + ", totalAmount=" + totalAmount
				+ ", gainMoeny=" + gainMoeny + ", userDiscount=" + userDiscount + ", useCardId=" + useCardId
				+ ", userPoint=" + userPoint + ", userPointAmount=" + userPointAmount + ", userBalance=" + userBalance
				+ ", userCash=" + userCash + ", payAmount=" + payAmount + ", charge=" + charge + ", postFee=" + postFee
				+ ", packingFee=" + packingFee + ", settlementAmount=" + settlementAmount + ", paymentFlowId="
				+ paymentFlowId + ", payFinishTime=" + payFinishTime + ", buyMemberCardId=" + buyMemberCardId
				+ ", buyMemberCardExtendId=" + buyMemberCardExtendId + ", buyMemberCardGiftId=" + buyMemberCardGiftId
				+ ", shareUserId=" + shareUserId + ", remark=" + remark + ", goodsJson=" + goodsJson + ", deliveryTime="
				+ deliveryTime + ", confirmReceiptTime=" + confirmReceiptTime + ", canAfterSale=" + canAfterSale
				+ ", source=" + source + ", rawAddTime=" + rawAddTime + ", rawUpdateTime=" + rawUpdateTime + "]";
	}
}
