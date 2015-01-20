package Decoding;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Model.TypeMessage.MessageType26;
import Model.message.Message;

public class MT26 {

	@Test
	public void DecodingTest() {
		// assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
		System.out.println(" Init Test of Decoding Message Type 26.");
		Message message = new Message("120 14 10 31 23 00 15 26 C66A43FDFFEFFF7FFBC21A10C08583A81B20D806C836815206902C8038C26880", 1);
		assertEquals("ERROR: PRN must be '120' ", 120, message.getPrn());
		assertEquals("ERROR: Year must be '14' ", 14, message.getYear());
		assertEquals("ERROR: Month must be '10' ", 10, message.getMonth());
		assertEquals("ERROR: Day must be '31' ", 31, message.getDay());
		assertEquals("ERROR: Minute must be '00' ", 0, message.getMinute());
		assertEquals("ERROR: Second must be '15' ", 15, message.getSeconds());
		assertEquals("ERROR: Message Type must be '26' ", 26, message.getMessagetype());
		MessageType26 payload = (MessageType26) message.getPayload();
		assertEquals("ERROR: Band Number must be '9' ", 9, payload.getBandnumber());
		assertEquals("ERROR: Band Number must be '0' ", 0, payload.getBlockid());

		// check de Gives and IGP
		String resultados = "15,63.75;15,63.75;15,63.75;15,63.75;13,2.0;12,2.0;11,2.0;10,1.75;9,1.625;8,1.625;9,1.625;10,1.625;9,1.25;9,0.75;9,0.625;";
		for (int i = 0; i < payload.getGridpoints().length; i++) {
			System.out.print(payload.getGridpoints()[i].getGIVEI() + "," + payload.getGridpoints()[i].getIGP_VerticalDelay() + ";");
		}
	}

	@Test
	public void ValuesTest() {
		// assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
		System.out.println(" Init Test of GridPoint Funcionalities.");

	}

}
