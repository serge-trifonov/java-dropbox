package com.trifonov.client.storage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.trifonov.common.storage.TempFileMessage;

public class ClientMain {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		//serialazedClient();
		byteClientReal();				
	}
	public static void serialazedClient()  {
		try (Socket socket=new Socket("localhost",8081)){
			
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());	
			TempFileMessage message=new TempFileMessage(Paths.get("fileClient.txt"));
			oos.writeObject(message);
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	public static void byteClientReal()  {
		
		try(Socket socket=new  Socket("localhost",8081);
				DataOutputStream os=new DataOutputStream(socket.getOutputStream())) {
			
			Path path=Paths.get("fileClient.txt");
			
			String name=path.getFileName().toString();
			int nameSize=name.getBytes().length;
			long fileSize=Files.size(path);
			
			os.writeByte(1);
			
			os.writeInt(nameSize);
			os.write(name.getBytes());	
			os.writeLong(fileSize);
			
			byte[] buff = new byte[1024];
			try (FileInputStream in = new FileInputStream(name)) {
				int n=in.read(buff);
				
				while (n!=-1) {	
					os.write(buff, 0, n);
					n=in.read(buff);				
				}
			}				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
