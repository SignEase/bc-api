package com.ichaoj.sxq.client.beans;

import com.ichaoj.sxq.client.util.Assert;

import java.text.DecimalFormat;

public class FilePreservationOrder extends SxqDataStore {

	private static final long serialVersionUID = 7211932916427656058L;
	private DecimalFormat format = new DecimalFormat("YC0000000000");
	private Integer days;
	
	@Override
	public String getStoreNo(){
		if(getStoreId() == null)
			return null;
		else
			return format.format(this.getStoreId());
	}
	
	public void check(){
		Assert.notNull(this.getOwnerId(), "ownerid不能为空");
		Assert.notNull(this.getContractStatus());
	}


	@Override
	public String toString() {
		return super.toString();
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}
