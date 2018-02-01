package pl.softfly.amprepare.task;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import pl.softfly.amprepare.AnyMemoConverterContext;

public class RemoveLastWhiteSpaceTask extends Task implements Runnable {

	protected final static int LINE_FEED = '\n';

	protected final static int FORM_FEED = '\f';

	protected final static int CARRIAGE_RETURN = '\r';

	protected final static int TABULATION = '\u0009';

	protected final static int[] CONTROLS = { LINE_FEED, FORM_FEED, CARRIAGE_RETURN, TABULATION };
	static {
		Arrays.sort(CONTROLS);
	}

	public RemoveLastWhiteSpaceTask(Reader in, Writer out, AnyMemoConverterContext context) {
		super(in, out, context);
	}

	@Override
	public void run() {
		try {
			Queue<Integer> cb = new ArrayDeque<Integer>();
			int i;
			while ((i = in.read()) != -1) {
				if (Arrays.binarySearch(CONTROLS, i) > -1) {
					cb.add(i);
				} else if (cb.size() > 0) {
					Integer a;
					while ((a = cb.poll()) != null) {
						out.write(a);
					}
					out.write(i);
				} else {
					out.write(i);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			super.run();
		}
	}

}