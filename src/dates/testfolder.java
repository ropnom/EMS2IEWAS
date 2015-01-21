package dates;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import Model.WriteCurrentData;

public class testfolder {

	public static void main(String[] args) {

		
		try {
			System.out.println(Paths.get("").toAbsolutePath().toString());
			WriteCurrentData write = new WriteCurrentData();
			write.setFilename("ejemplo.txt");

			List<String> lines = new ArrayList<String>();
			lines.add("Hola mundo");
			lines.add("Ejemploooo");
			write.Write(lines);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
