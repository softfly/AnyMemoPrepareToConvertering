package pl.softfly.amprepare.task;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import pl.softfly.amprepare.AnyMemoConverterContext;

public abstract class Task implements Runnable {

	protected final Reader in;

	protected final Writer out;

	protected final AnyMemoConverterContext context;

	public Task(Reader in, Writer out, AnyMemoConverterContext context) {
		this.in = in;
		this.out = out;
		this.context = context;
	}

	public void run() {
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
