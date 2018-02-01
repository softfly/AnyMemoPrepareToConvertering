package pl.softfly.amprepare.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import pl.softfly.amprepare.AnyMemoConverterContext;

public class HtmlSpecialCharsTask extends Task implements Runnable {

	protected final static Charset UTF_8 = Charset.forName("utf-8");

	protected final BufferedReader in;

	protected final BufferedWriter out;

	protected final static HashMap<String, String> MAP = new HashMap<>();
	static {
		MAP.put("<(?![bB]>|/[bB]>)", "&lt;");
		MAP.put("(?<!<[bB]|</[bB])>", "&gt;");
		MAP.put("\\\\", "&bsol;");
	}

	public HtmlSpecialCharsTask(Reader in, Writer out, AnyMemoConverterContext context) {
		super(new BufferedReader(in), new BufferedWriter(out), context);
		this.in = (BufferedReader) super.in;
		this.out = (BufferedWriter) super.out;
	}

	@Override
	public void run() {
		try {
			String l;
			while ((l = in.readLine()) != null) {
				for (Map.Entry<String, String> entry : MAP.entrySet()) {
					l = l.replaceAll(entry.getKey(), entry.getValue());
				}
				out.write(l);
				out.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.run();
		}
	}

}