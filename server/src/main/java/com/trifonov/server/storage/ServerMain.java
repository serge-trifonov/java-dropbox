package com.trifonov.server.storage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.trifonov.common.storage.StorageTemplate;
import com.trifonov.common.storage.TempFileMessage;

public class ServerMain {

	public static void main(String[] args) throws InterruptedException {

		byteServerReal();
	}
	public static void serializedServer() {
		try (ServerSocket serverSocket = new ServerSocket(8081)) {
			System.out.println("server is running...");

			System.out.println("client is access...");
			try (Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				
				TempFileMessage st = (TempFileMessage) ois.readObject();
				System.out.println(st);
				for(byte b:st.getBytes()) {
					System.out.print((char)b);
				}	
			}

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("server is not access...");
			e.printStackTrace();
		}
	}
	public static void byteServerReal() throws InterruptedException {
		
		
		byte[]bytes=new byte[1024];
		
		try(ServerSocket serverSocket=new ServerSocket(8081)){
			
			System.out.println("server is running....");
			
			try(Socket socket=serverSocket.accept();
				BufferedInputStream bis=new BufferedInputStream(socket.getInputStream())) {
				
				System.out.println("reading is process...");
				
				bis.read(bytes, 0, 9);
				int nameSize=new BigInteger(toBytes(bytes,1,5)).intValue();
				int fileSize=new BigInteger(toBytes(bytes,5,9)).intValue();
				System.out.println("command is "+bytes[0]);
				
				byte[]bytesNew=new byte[nameSize];
				
				
				bis.read(bytesNew,0,nameSize);
				for(byte b:bytesNew) {
					System.out.print((char)b);
				}
				System.out.println();
				bytesNew=new byte[fileSize];
				bis.read(bytesNew,0,fileSize);
				for(byte b:bytesNew) {
					System.out.print((char)b);
				}
				
				
				
				
					
					
				}
			}	
		 catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	public static byte[]toBytes(byte[]b,int start,int end){
		byte[]out=new byte[end-start];
		int index=0;
		for(int i=start;i<end;i++) {
			
			out[index++]=b[i];
		}
		return out;
		
	}

}
