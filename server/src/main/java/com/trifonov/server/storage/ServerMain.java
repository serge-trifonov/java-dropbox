package com.trifonov.server.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.trifonov.common.storage.TempFileMessage;
import com.trifonov.common.storage.StorageTemplate;

public class ServerMain {

	public static void main(String[] args)  {

		//serializedServer();
		byteServerReal();	
	}
	public static void serializedServer() {
		
		try (ServerSocket serverSocket = new ServerSocket(8081)) {
			System.out.println("server is running...");

			try (Socket socket = serverSocket.accept();
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
				TempFileMessage tfm = (TempFileMessage) ois.readObject();
				
				for(byte b:tfm.getBytes()) {				
					System.out.print((char)b);
				}				
				String name=tfm.getName();		
				try (OutputStream os = new BufferedOutputStream(new FileOutputStream(name))) {	
					for(byte b:tfm.getBytes()) {
						os.write(b);
					}	
				}	
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void byteServerReal()  {
		
		byte[]bytes=new byte[1024];	
		try (ServerSocket sc = new ServerSocket(8081)) {
			
			System.out.println("server is running....");
			
			try (Socket socket = sc.accept();
					DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {	
				byte signalByte=dis.readByte();
				int fileNameLength = dis.readInt();
				byte[] fileNameBytes = new byte[fileNameLength];
				dis.read(fileNameBytes);	
				String filename = new String(fileNameBytes);
				
				long fileSize = dis.readLong();
				
				try (OutputStream out = new BufferedOutputStream(new FileOutputStream(filename))) {
					for (long i = 0; i < fileSize; i++) {
						out.write(dis.read());
					}
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
