package pl.softfly.amprepare.task;

import java.io.Reader;
import java.io.Writer;

import pl.softfly.amprepare.AnyMemoConverterContext;

/**
 * Convert \n to <br/>
 */
public class ConvertLineFeedTask extends Task implements Runnable {

	protected final static char[] BR = "<br/>".toCharArray();

	public ConvertLineFeedTask(Reader in, Writer out, AnyMemoConverterContext context) {
		super(in, out, context);
	}

	@Override
	public void run() {
		boolean multipleRow = false;
		try {
			int i;
			while ((i = in.read()) != -1) {
				char c = (char) i;
				if (multipleRow && c == '\n') {
					out.write(BR);
				} else if (multipleRow && c == '"') {
					multipleRow = false;
					out.write(c);
				} else if (c == '"') {
					multipleRow = true;
					out.write(c);
				} else {
					out.write(c);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.run();
		}
	}

}