package pl.softfly.amprepare.executor.task;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import pl.softfly.amprepare.AnyMemoConverterContext;
import pl.softfly.amprepare.AnyMemoConverterException;
import pl.softfly.amprepare.task.Task;

public class ConcurrentExecutorTask extends ExecutorTask {

	protected static ExecutorTask INSTANCE;

	private ConcurrentExecutorTask() {
	}

	protected static ExecutorTask getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConcurrentExecutorTask();
		}
		return INSTANCE;
	}

	@Override
	public void excecute(Class<Task>[] tasks, AnyMemoConverterContext context)
			throws InterruptedException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, IOException, AnyMemoConverterException {
		Thread[] threads = new Thread[tasks.length];

		Reader pi1 = null;
		for (int i = 0; i < tasks.length; i++) {
			try {
				Class<Task> c = tasks[i];

				PipedReader pi2 = new PipedReader();
				PipedWriter po2;
				if (i == tasks.length - 1) {
					po2 = null;
				} else {
					po2 = new PipedWriter(pi2);
				}

				Task task = c.getDeclaredConstructor(Reader.class, Writer.class, AnyMemoConverterContext.class)
						.newInstance(pi1, po2, context);
				threads[i] = new Thread(task);
				pi1 = pi2;
			} catch (InvocationTargetException e) {
				Throwable t = e.getTargetException();
				if (t instanceof AnyMemoConverterException) {
					throw (AnyMemoConverterException) t;
				} else {
					throw e;
				}
			}
		}
		for (int i = 0; i < tasks.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < tasks.length; i++) {
			threads[i].join();
		}
	}

}
