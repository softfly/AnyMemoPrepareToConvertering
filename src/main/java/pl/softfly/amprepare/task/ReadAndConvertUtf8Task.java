package pl.softfly.amprepare.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import pl.softfly.amprepare.AnyMemoConverterContext;

public class ReadAndConvertUtf8Task extends Task implements Runnable {

	protected final static Charset WINDDOWS_1025 = Charset.forName("windows-1250");

	protected final Reader in;

	protected final File tmpInFile;

	public ReadAndConvertUtf8Task(Reader in, Writer out, AnyMemoConverterContext context) throws FileNotFoundException {
		super(in, out, context);

		FileInputStream fis;
		if (context.getInFile().equals(context.getOutFile())) {
			tmpInFile = new File(context.getInFile().getAbsolutePath() + "-tmp");
			context.getInFile().renameTo(tmpInFile);
			fis = new FileInputStream(tmpInFile);
		} else {
			fis = new FileInputStream(context.getInFile());
			tmpInFile = null;
		}
		this.in = new InputStreamReader(fis, WINDDOWS_1025);
	}

	@Override
	public void run() {
		try {
			out.write('\ufeff');
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (tmpInFile != null) {
				tmpInFile.delete();
			}
		}
	}

	/*
	 * protected static Charset guessCharset(File in) throws IOException {
	 * return Charset.forName(new TikaEncodingDetector().guessEncoding(new
	 * FileInputStream(in))); }
	 */

}