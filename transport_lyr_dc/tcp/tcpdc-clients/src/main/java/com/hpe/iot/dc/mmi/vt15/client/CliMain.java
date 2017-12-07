package com.hpe.iot.dc.mmi.vt15.client;

import java.io.File;
import java.io.IOException;

import com.hpe.iot.dc.mmi.vt15.tcp.client.payload.converters.MMIVT15ClientToServerMessageGenerator;
import com.hpe.iot.dc.tcp.client.CliTcpClient;

/**
 * @author sveera
 *
 */
public class CliMain {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting the TCP Client. ");
		System.out.println("Console output will be redirected to file : " + System.getProperty("user.dir")
				+ File.separator + "tcpClient.log");
		System.out.println();
		System.out.println();
		System.out.println("Press any Key and Enter to stop the client ..... ");
		new CliTcpClient().runClient(new MMIVT15ClientToServerMessageGenerator());
		System.out.println(" TCP Client program Stopped . Please wait for JVM to Stop.");
	}
}