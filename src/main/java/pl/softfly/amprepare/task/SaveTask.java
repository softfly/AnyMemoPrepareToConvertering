package pl.softfly.amprepare.task;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import pl.softfly.amprepare.AnyMemoConverterContext;

public class SaveTask extends Task implements Runnable {

	protected final static Charset UTF_8 = Charset.forName("utf-8");

	public SaveTask(Reader in, Writer out, AnyMemoConverterContext context) throws FileNotFoundException {
		super(in, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(context.getOutFile()), UTF_8)),
				context);
	}

	@Override
	public void run() {
		try {
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			super.run();
		}
	}

}
