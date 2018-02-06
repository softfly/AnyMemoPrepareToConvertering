package pl.softfly.amprepare;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import pl.softfly.amprepare.executor.task.ExecutorTask;
import pl.softfly.amprepare.task.ConvertHtmlSpaceTask;
import pl.softfly.amprepare.task.ConvertLineFeedTask;
import pl.softfly.amprepare.task.HtmlSpecialCharsTask;
import pl.softfly.amprepare.task.ReadAndConvertUtf8Task;
import pl.softfly.amprepare.task.RemoveLastWhiteSpaceTask;
import pl.softfly.amprepare.task.SaveTask;

public class AnyMemoConverter {

	@SuppressWarnings("rawtypes")
	protected final static Class[] TASKS = { //
			ReadAndConvertUtf8Task.class, //
			HtmlSpecialCharsTask.class, //
			ConvertHtmlSpaceTask.class, //
			ConvertLineFeedTask.class, //
			RemoveLastWhiteSpaceTask.class, //
			SaveTask.class };

	@SuppressWarnings("unchecked")
	public static void main(String... args) throws Exception {
		try {
			AnyMemoConverterContext context = parseInputArguments(args);
			if (context == null) {
				displayHelp();
			}

			ExecutorTask executorTask = ExecutorTask.getConcurrentInstance();
			executorTask.excecute(TASKS, context);
		} catch (AnyMemoConverterException e) {
			System.out.println(e.getMessage());
		}
	}

	protected static AnyMemoConverterContext parseInputArguments(String... args) {
		AnyMemoConverterContext context = new AnyMemoConverterContext();
		String outputDir = null;
		String outputNameFile = null;

		int i = 0;
		while (i < args.length) {
			if ("-d".equals(args[i])) {
				outputDir = args[++i];
			} else if (context.getInFile() == null) {
				context.setInFile(new File(args[i]));
			} else if (context.getOutFile() == null) {
				outputNameFile = args[i];
				context.setOutFile(new File(outputNameFile));
			}
			i++;
		}

		if (context.getInFile() == null) {
			return null;
		}

		if (context.getOutFile() == null) {
			if (outputDir != null && !outputDir.isEmpty()) {
				if (outputNameFile == null || outputNameFile.isEmpty()) {
					outputNameFile = FilenameUtils.getName(context.getInFile().getName());
				}
				context.setOutFile(new File(outputDir + "/" + outputNameFile));
			} else {
				context.setOutFile(context.getInFile());
			}
		}
		return context;
	}

	protected static void displayHelp() {
		System.out.println("Usage: java [-options] [inputFile] [outputFile]");
		System.out.println("  -d output dir");
	}

}
