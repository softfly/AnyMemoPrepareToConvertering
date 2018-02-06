package pl.softfly.amprepare.executor.task;

import pl.softfly.amprepare.AnyMemoConverterContext;
import pl.softfly.amprepare.task.Task;

public abstract class ExecutorTask {

	public abstract void excecute(Class<Task>[] tasks, AnyMemoConverterContext context) throws Exception;

	public static ExecutorTask getSingleInstance() {
		return SingleExecutorTask.getInstance();
	}

	public static ExecutorTask getConcurrentInstance() {
		return ConcurrentExecutorTask.getInstance();
	}

}
