package Decoding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Model.TypeMessage.MessageType18;
import Model.TypeMessage.MessageType26;
import Model.message.Message;

public class MT18 {

	private Message message;
	private MessageType18 payload;
	
	// Example of message to decoding
	@Test
	public void DecodingTest() {
		
		System.out.println("+");
		System.out.println(" Init Test of Decoding Message Type 18.");
		System.out.println();
		message = new Message("120 15 01 05 04 00 05 18 C6495800003F800007C0000000000000000000000000000000000000353BCA80", 1);
		assertEquals("ERROR: PRN must be '120' ", 120, message.getPrn());
		assertEquals("ERROR: Year must be '15' ", 15, message.getYear());
		assertEquals("ERROR: Month must be '01' ", 1, message.getMonth());
		assertEquals("ERROR: Day must be '05' ", 5, message.getDay());
		assertEquals("ERROR: Hour must be '04' ", 4, message.getHour());
		assertEquals("ERROR: Minute must be '00' ", 0, message.getMinute());
		assertEquals("ERROR: Second must be '05' ", 5, message.getSeconds());
		assertEquals("ERROR: Message Type must be '18' ", 18, message.getMessagetype());
		System.out.println(" Message Test -->  OK.");

		payload = (MessageType18) message.getPayload();
		assertEquals("ERROR: Band Number must be '6' ", 6, payload.getBandnumber());
		assertEquals("ERROR: Number of Bands must be '5' ", 5, payload.getNumberofbands());
		assertEquals("ERROR: Iodi must be '0' ", 0, payload.getIodi());

		// check de Gives and IGP
		String[] resultados = "19,20,21,22,23,24,25,46,47,48,49,50".split(",");
		for (int i = 0; i < payload.getOrden().size(); i++) {
			assertEquals("ERROR: Data must be '" + resultados[i] + "'", resultados[i], payload.getOrden().get(i).toString());
		}
		
		System.out.println(" Payload Order Test -->  OK.");
		System.out.println();
		System.out.println(" End Test of Decoding Message Type 18.");
		System.out.println();

	}

}
