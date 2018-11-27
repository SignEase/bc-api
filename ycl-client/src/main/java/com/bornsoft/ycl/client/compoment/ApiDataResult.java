package com.bornsoft.ycl.client.compoment;

public class ApiDataResult extends ResultBase{

	private String hash;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "ApiDataResult [hash=" + hash + "]";
	}
	
}
