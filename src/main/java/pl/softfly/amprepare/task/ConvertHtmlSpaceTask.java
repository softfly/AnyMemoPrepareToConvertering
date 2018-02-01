package pl.softfly.amprepare.task;

import java.io.Reader;
import java.io.Writer;

import pl.softfly.amprepare.AnyMemoConverterContext;

public class ConvertHtmlSpaceTask extends Task implements Runnable {

	protected final static char[] NBSP = "&nbsp;".toCharArray();

	public ConvertHtmlSpaceTask(Reader in, Writer out, AnyMemoConverterContext context) {
		super(in, out, context);
	}

	@Override
	public void run() {
		boolean multipleRow = false;
		boolean startRow = false;
		try {
			int i;
			while ((i = in.read()) != -1) {
				char c = (char) i;
				if (multipleRow) {
					if (c == '\n') {
						startRow = true;
						out.write(c);
					} else if (startRow && c == ' ') {
						out.write(NBSP);
					} else {
						startRow = false;
						out.write(c);
					}
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