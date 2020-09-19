package com.trifonov.common.storage;

import java.nio.file.Paths;

public class TempFileApp {
	public static void main(String[]args) {
		TempFileMessage message=new TempFileMessage(Paths.get("../file.txt"));
	}
}
