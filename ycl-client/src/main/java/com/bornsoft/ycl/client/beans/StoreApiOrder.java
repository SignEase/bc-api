package com.bornsoft.ycl.client.beans;

import com.bornsoft.ycl.client.util.Assert;

public class StoreApiOrder extends YclDataStore {

	private static final long serialVersionUID = 7727707124769160285L;
	
	private String fileBase64;
	
	public void check(){
		Assert.notNull(this.getFileName(), "filename为空");
		Assert.notNull(this.getFileBase64(), "FileBase64为空");
		Assert.notNull(this.getStoreName(), "StoreName为空");
		Assert.notNull(this.getIsPublic(), "IsPublic为空");
	}

	public String getFileBase64() {
		return fileBase64;
	}

	public void setFileBase64(String fileBase64) {
		this.fileBase64 = fileBase64;
	}

}
