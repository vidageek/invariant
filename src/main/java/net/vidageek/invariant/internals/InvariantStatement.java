package net.vidageek.invariant.internals;

import java.io.File;
import java.lang.reflect.Method;

import net.vidageek.invariant.FileData;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.ReflectionProviderException;

import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class InvariantStatement extends Statement {

	private final Class<?> type;
	private final Method method;
	private static final Logger log = LoggerFactory.getLogger(InvariantStatement.class);

	public InvariantStatement(final Method method, final Class<?> type) {
		this.method = method;
		this.type = type;
	}

	@Override
	public void evaluate() throws Throwable {
		evaluateAt(new File("."));
	}

	private void evaluateAt(final File file) throws Throwable {
		try {
			log.debug("Invoking invariant " + type.getSimpleName() + "." + method.getName() + " at : "
					+ file.getAbsolutePath());
			Mirror mirror = new Mirror();
			Object invariantObject = mirror.on(type).invoke().constructor().withoutArgs();
			mirror.on(invariantObject).invoke().method(method).withArgs(new FileData(file));
		} catch (ReflectionProviderException e) {
			Throwable cause = e.getCause();
			if (AssertionError.class.isAssignableFrom(cause.getClass())) {
				throw cause;
			}
		}
	}
}
