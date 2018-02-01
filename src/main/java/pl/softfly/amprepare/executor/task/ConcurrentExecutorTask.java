package pl.softfly.amprepare.executor.task;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;

import pl.softfly.amprepare.AnyMemoConverterContext;
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

	public void excecute(Class[] tasks, AnyMemoConverterContext context) throws InterruptedException {
		Thread[] threads = new Thread[tasks.length];

		Reader pi1 = null;
		for (int i = 0; i < tasks.length; i++) {
			Class<Task> c = tasks[i];
			try {
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
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
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
