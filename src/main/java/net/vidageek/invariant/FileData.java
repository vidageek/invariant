package net.vidageek.invariant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringBuilder buffer = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public File getFile() {
		return file;
	}
}
