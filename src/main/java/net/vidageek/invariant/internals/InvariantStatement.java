package net.vidageek.invariant.internals;

import java.io.File;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import net.vidageek.invariant.FileData;
import net.vidageek.invariant.Invariant;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.exception.MirrorException;

import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final public class InvariantStatement extends Statement {

	private final Class<?> type;
	private final Method method;
	private static final Logger log = LoggerFactory.getLogger(InvariantStatement.class);
	private final Pattern toApply;

	public InvariantStatement(final Method method, final Class<?> type) {
		this.method = method;
		this.type = type;
		toApply = Pattern.compile(method.getAnnotation(Invariant.class).affects());
	}

	@Override
	public void evaluate() throws Throwable {
		evaluate(new File(".").getCanonicalFile());
	}

	private void evaluate(final File file) throws Throwable {
		if (file.isHidden()) {
			log.debug("Rejecting " + file.getAbsolutePath() + " because it is hidden.");
			return;
		}
		if (file.isFile()) {
			if (toApply.matcher(file.getAbsolutePath()).matches()) {
				evaluateAt(file);
			}
		} else if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				evaluate(child);
			}
		}
	}

	private void evaluateAt(final File file) throws Throwable {
		try {
			log.debug("Invoking invariant " + type.getSimpleName() + "." + method.getName() + " at : "
					+ file.getAbsolutePath());
			Mirror mirror = new Mirror();
			Object invariantObject = mirror.on(type).invoke().constructor().withoutArgs();
			mirror.on(invariantObject).invoke().method(method).withArgs(new FileData(file));
		} catch (MirrorException e) {
			Throwable cause = e.getCause();
			if (AssertionError.class.isAssignableFrom(cause.getClass())) {
				throw cause;
			}
		}
	}
}
