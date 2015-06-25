import java.io.File;
import java.util.Scanner;

/*
 * Main entry point to the migrator.
 */



public class Main {

	public static launchThread task;

	public static void main(String[] args) {
		System.out.println("Starting Migrator");
		Scanner s = new Scanner(System.in);

		// Create the file directory
		if (false) {
			System.out
					.println("You have to specify the path with your VAADIN project");
		} else {
			File projectFolder = new File(s.nextLine());
			// File projectFolder = new
			// File("/Users/pavel/Documents/VaadinTest/");
			int conversionType = 0;
			try {
				task = new launchThread(projectFolder, conversionType);
			} catch (Exception e) {
			}
		}

	}

}
