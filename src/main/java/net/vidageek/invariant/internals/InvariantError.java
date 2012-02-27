package net.vidageek.invariant.internals;

import java.util.List;

final public class InvariantError extends AssertionError {

	public InvariantError(final List<Failure> failures) {
		super(failures.toString());
	}

	private static final long serialVersionUID = 1L;

}
