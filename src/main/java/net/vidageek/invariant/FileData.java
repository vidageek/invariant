package net.vidageek.invariant;

import java.io.File;

final public class FileData {

	private final File file;

	public FileData(final File file) {
		this.file = file;
	}

	public String getName() {
		String path = file.getAbsolutePath();
		return path.substring(path.lastIndexOf(File.separator) + 1);
	}

}
