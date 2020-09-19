package com.trifonov.common.storage;

import java.io.Serializable;

public class StorageTemplate implements Serializable{
	
	private static final long serializedVersionId=1L;
	
	private String text;

	public StorageTemplate(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "StorageTemplate [text=" + text + "]";
	}
}
