package com.trifonov.client.storage;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
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



import com.trifonov.common.storage.StorageTemplate;
import com.trifonov.common.storage.TempFileMessage;

public class ClientMain {

	public static void main(String[] args) throws IOException {
		
		byteClientReal();
			
	}
	public static void serialazedClient() {
		try (Socket socket=new Socket("localhost",8081)){
			ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
			
			TempFileMessage message=new TempFileMessage(Paths.get("../file.txt"));
			oos.writeObject(message);
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	public static void byteClientExample() {
		
		try(Socket socket=new Socket("localhost",8081)){
		
			byte[]bytes= {'h','r','e','n'};
			socket.getOutputStream().write(bytes);
				
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void byteClientReal() throws IOException {
		
		
		
		try(Socket socket=new  Socket("localhost",8081);
				DataOutputStream os=new DataOutputStream(socket.getOutputStream())
				
				) {
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
			Path path=Paths.get("../file.txt");
			String name=path.getFileName().toString();
			int nameSize=name.getBytes().length;
			
			int fileSize=(int) Files.size(path);
					
			
			outputStream.write(ByteBuffer.allocate(4).putInt(nameSize).array());
			outputStream.write(ByteBuffer.allocate(4).putInt(fileSize).array());
			
			outputStream.write(name.getBytes());
			outputStream.write(Files.readAllBytes(path));
			os.writeByte(1);
			
			os.write(outputStream.toByteArray());
				
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
