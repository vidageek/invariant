package net.vidageek.invariant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.runner.RunWith;

@RunWith(InvariantRunner.class)
final public class InvariantUseDefinition {

	private static final Set<String> files = new HashSet<String>();

	@Invariant
	public void invariantDefinitionShouldWork(final FileData data) {
	}

	@Invariant(affects = ".*\\.cfx")
	public void invariantFindsAllMatchingFiles(final FileData data) {
		assertTrue("", data.getName().endsWith(".cfx"));
		files.add(data.getName());
	}

	@Invariant(affects = ".*a\\.cfx")
	public void checkPreviousInvariant(final FileData data) {
		assertEquals(3, files.size());
		assertTrue(files.contains("a.cfx"));
		assertTrue(files.contains("b.cfx"));
		assertTrue(files.contains("c.cfx"));
	}

}
