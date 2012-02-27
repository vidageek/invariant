package net.vidageek.invariant.internals;

final public class Failure {

	private final String fileName;
	private final Throwable cause;
	private final String invariantName;

	public Failure(final Throwable cause, final String invariantName, final String fileName) {
		this.cause = cause;
		this.invariantName = invariantName;
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "\nInvariant " + invariantName + " failed for file " + fileName + " with message [" + cause.getMessage()
				+ "]";
	}

}
