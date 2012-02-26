package net.vidageek.invariant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

final public class FileData {

	private final File file;

	public FileData(final File file) {
		this.file = file;
	}

	public String getName() {
		String path = file.getAbsolutePath();
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}

	public String getContent() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
