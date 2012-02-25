package net.vidageek.invariant;

import java.util.List;

import net.vidageek.invariant.internals.InvariantStatement;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class InvariantRunner extends BlockJUnit4ClassRunner {

	private static final Logger log = LoggerFactory.getLogger(InvariantRunner.class);

	public InvariantRunner(final Class<?> type) throws InitializationError {
		super(type);
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		final List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Invariant.class);
		if (log.isDebugEnabled()) {
			log.debug("Found the following invariants: " + asString(methods));
		}
		return methods;
	}

	@Override
	protected void validateTestMethods(final List<Throwable> errors) {
		for (FrameworkMethod method : computeTestMethods()) {
			method.validatePublicVoid(false, errors);
		}
	}

	@Override
	protected Statement methodBlock(final FrameworkMethod method) {
		return new InvariantStatement(method);
	}

	private String asString(final List<FrameworkMethod> methods) {
		String result = "[";
		for (FrameworkMethod method : methods) {
			result += getTestClass().getJavaClass().getSimpleName() + "." + method.getName() + ", ";
		}
		return result.substring(0, result.length() - 2) + "]";
	}

}
