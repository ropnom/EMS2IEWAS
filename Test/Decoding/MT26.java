package Decoding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Model.TypeMessage.MessageType26;
import Model.TypeMessage.MessageType26.GridPointMT26;
import Model.message.Message;

public class MT26 {

	private Message message;
	private MessageType26 payload;

	// Example of message to decoding
	@Test
	public void DecodingTest() {
		
		System.out.println("+");
		System.out.println(" Init Test of Decoding Message Type 26.");
		System.out.println();
		message = new Message("120 14 10 31 23 00 15 26 C66A43FDFFEFFF7FFBC21A10C08583A81B20D806C836815206902C8038C26880", 1);
		assertEquals("ERROR: PRN must be '120' ", 120, message.getPrn());
		assertEquals("ERROR: Year must be '14' ", 14, message.getYear());
		assertEquals("ERROR: Month must be '10' ", 10, message.getMonth());
		assertEquals("ERROR: Day must be '31' ", 31, message.getDay());
		assertEquals("ERROR: Hour must be '23' ", 23, message.getHour());
		assertEquals("ERROR: Minute must be '00' ", 0, message.getMinute());
		assertEquals("ERROR: Second must be '15' ", 15, message.getSeconds());
		assertEquals("ERROR: Message Type must be '26' ", 26, message.getMessagetype());
		System.out.println(" Message Test -->  OK.");

		payload = (MessageType26) message.getPayload();
		assertEquals("ERROR: Band Number must be '9' ", 9, payload.getBandnumber());
		assertEquals("ERROR: Band Number must be '0' ", 0, payload.getBlockid());

		// check de Gives and IGP
		String[] resultados = "15,63.75;15,63.75;15,63.75;15,63.75;13,2.0;12,2.0;11,2.0;10,1.75;9,1.625;8,1.625;9,1.625;10,1.625;9,1.25;9,0.75;9,0.625;".split(";");
		for (int i = 0; i < payload.getGridpoints().length; i++) {
			assertEquals("ERROR: Data must be '" + resultados[i] + "'", resultados[i], payload.getGridpoints()[i].getGIVEI() + "," + payload.getGridpoints()[i].getIGP_VerticalDelay());
		}
		System.out.println(" Payload Test -->  OK.");
		System.out.println();
		System.out.println(" End Test of Decoding Message Type 26.");
		System.out.println();

	}

	// Example of gridpoint test
	@Test
	public void ValuesTest() {

		System.out.println("+");
		System.out.println(" Init Test of GridPointMT26 Funcionalities.");
		System.out.println();
		payload = new MessageType26();
		MessageType26.GridPointMT26 point = payload.new GridPointMT26(1000, 8);
		assertEquals("ERROR: IGP must be '125'  value. ", 125f, point.getIGP_VerticalDelay(), 0.0f);
		assertEquals("ERROR: GIVEI must be '8'  value. ", 8, point.getGIVEI());
		assertEquals("ERROR: rms must be '-1'  IGP > 63.75 . ", -1, point.getRms());
		assertEquals("ERROR: vtec must be '-1'  IGP > 63.75 . ", -1, point.getVtec());
		System.out.println(" PointMT26 init value Test 		-->  OK.");
		System.out.println(" PointMT26 overvalue Test		-->  OK.");

		point = payload.new GridPointMT26(10, 8);
		assertEquals("ERROR: IGP must be '1.25'  value. ", 1.25f, point.getIGP_VerticalDelay(), 0.0f);
		assertEquals("ERROR: GIVEI must be '8'  value. ", 8, point.getGIVEI());
		//assertEquals("ERROR: rms must be '166'  value . ", 166, point.getRms());
		assertEquals("ERROR: rms must be '77'  value . ", 77, point.getVtec());
		System.out.println(" PointMT26 convertion equeation Test 	-->  OK.");

		System.out.println();
		System.out.println(" End Test of GridPointMT26 Funcionalities.");
		System.out.println();

	}

}
