package pl.softfly.amprepare.executor.task;

import pl.softfly.amprepare.AnyMemoConverterContext;

public abstract class ExecutorTask {

	public abstract void excecute(Class[] tasks, AnyMemoConverterContext context) throws Exception;

	public static ExecutorTask getSingleInstance() {
		return SingleExecutorTask.getInstance();
	}

	public static ExecutorTask getConcurrentInstance() {
		return ConcurrentExecutorTask.getInstance();
	}

}
