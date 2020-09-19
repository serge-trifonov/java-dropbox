package com.trifonov.common.storage;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class TempFileMessage implements Serializable{
	
	
	private static final long serialVersionUID = -9102876216548268467L;
	private String name;
	private long size;
	private byte[] bytes;
	
	 
	
	public TempFileMessage(Path path) {
		
		try {
			this.name=path.getFileName().toString();
			this.size=Files.size(path);
			this.bytes=Files.readAllBytes(path);
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("invalid");
			
		}	
	}



	@Override
	public String toString() {
		return "TempFileMessage [name=" + name + ", size=" + size + ", bytes=" + Arrays.toString(bytes) + "]";
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public long getSize() {
		return size;
	}



	public void setSize(long size) {
		this.size = size;
	}



	public byte[] getBytes() {
		return bytes;
	}



	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}	
}
