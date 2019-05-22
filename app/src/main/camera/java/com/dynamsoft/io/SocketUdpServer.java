package com.dynamsoft.io;

import com.dynamsoft.data.BufferManager;
import com.dynamsoft.data.DataListener;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUdpServer extends Thread {
	private DatagramSocket mServer;
	private DataListener mDataListener;
	private BufferManager mBufferManager;

	public SocketUdpServer() {
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {
			mServer = new DatagramSocket(1111);
			byte data[] = new byte[1024];
			DatagramPacket receivePacket = new DatagramPacket(data, data.length);

			mServer.receive(receivePacket);
			String result = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
			// JSON analysis
			JsonParser parser = new JsonParser();
			boolean isJSON = true;
			JsonElement element = null;
			try {
				element =  parser.parse(result);
			}
			catch (JsonParseException e) {
				e.getMessage();
				isJSON = false;
			}
			if (isJSON && element != null) {
				JsonObject obj = element.getAsJsonObject();
				element = obj.get("type");
				if (element != null && element.getAsString().equals("data")) {
					element = obj.get("length");
					int length = element.getAsInt();
					element = obj.get("width");
					int width = element.getAsInt();
					element = obj.get("height");
					int height = element.getAsInt();
				}
			}



			//接收数据
			while (true) {
				mServer.receive(receivePacket);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {


		}
	}

	public void setOnDataListener(DataListener listener) {
		mDataListener = listener;
	}
}
