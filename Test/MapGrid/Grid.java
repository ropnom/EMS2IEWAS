package MapGrid;

import static org.junit.Assert.*;

import org.junit.Test;

import Model.map.Gridpoint;

public class Grid {

	// Example of gridpoint init test
	@Test
	public void ValuesTest() {
		// assertEquals("10 x 0 must be 0", 0, tester.multiply(10, 0));
		System.out.println(" Init Test of GridPoint Funcionalities.");
		Gridpoint point = new Gridpoint(42, 5);
		assertEquals("ERROR: lat must be '42'  value. ", 42, point.getLat());
		assertEquals("ERROR: lan must be '5'  value. ", 5, point.getLon());
		assertEquals("ERROR: vtec must be '9999' init value. ", 9999, point.getVtec());
		assertEquals("ERROR: rms must be '9999' init value. ", 9999, point.getRms());
		assertEquals("ERROR: timestamp must be 'null' init value. ", null, point.getTimestamp());

	}

}
