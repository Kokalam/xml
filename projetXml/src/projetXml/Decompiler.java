package projetXml;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Decompiler {

	public static void main(String[] args) {
		final File file = new File(args[0]);
		final String nameToFind = args[1].replace('_', ' ');
		RandomAccessFile access = null;
		try {
			access = new RandomAccessFile(file, "r");
			String firstLine = access.readLine();
			String[] offsets = firstLine.split(",", 0);
			boolean found = false;
			for (String offset : offsets) {
				if (offset.charAt(0) == nameToFind.charAt(0)) {
					access.skipBytes(Integer.valueOf(offset.split(":")[1]));
					found = true;
					break;
				}
			}
			if (!found) {
				System.out.println(nameToFind + " does not exist in this document");
				return;
			}
			String name = "";
			found = false;
			while (!found && (name = access.readLine()) != null) {
				if (name.startsWith(nameToFind))
					found = true;
				if (!name.startsWith(nameToFind.charAt(0) + ""))
					break;
			}
			if (!found) {
				System.out.println(nameToFind + " does not exist in this document");
				return;
			}
			String[] coAutors;
			coAutors = (coAutors = name.split(":")).length > 1 ? coAutors[1].split(",") : new String[0];
			System.out.println(nameToFind + " has " + coAutors.length + " coAuthors");
			for (String coAuthor : coAutors) {
				System.out.println(coAuthor);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				access.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
