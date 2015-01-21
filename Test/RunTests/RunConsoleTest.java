package RunTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Decoding.MT18;
import Decoding.MT26;

public class RunConsoleTest {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(MT26.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }
	    result = JUnitCore.runClasses(MT18.class);
	    for (Failure failure : result.getFailures()) {
	      System.out.println(failure.toString());
	    }
	}

}
