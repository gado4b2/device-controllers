/**
 * 
 */
package com.hpe.iot.dc.mmi.vt15.client;

import java.io.IOException;

import com.hpe.iot.dc.client.AbstractGuiMain;
import com.hpe.iot.dc.mmi.vt15.tcp.client.payload.converters.MMIVT15ClientMessageGenerator;
import com.hpe.iot.dc.tcp.client.GUI;
import com.hpe.iot.dc.tcp.client.payload.converter.ClientMessageConsumer;
import com.hpe.iot.dc.tcp.client.payload.converter.ClientMessageGenerator;

/**
 * @author sveera
 *
 */
class GUIMain extends AbstractGuiMain {

	public static void main(String[] args) throws IOException {
		new GUIMain().main();
	}

	@Override
	protected ClientMessageGenerator getClientMessageGenerator() {
		return new MMIVT15ClientMessageGenerator();
	}

	@Override
	protected ClientMessageConsumer getClientMessageConsumer() {
		return null;
	}

	@Override
	protected GUI getGUI() {
		return new MMIVT15GUI();
	}
}
