package pl.softfly.amprepare.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import static java.nio.file.StandardCopyOption.*;
import pl.softfly.amprepare.AnyMemoConverterContext;
import pl.softfly.amprepare.AnyMemoConverterException;

public class ReadAndConvertUtf8Task extends Task implements Runnable {

	protected final static Charset WINDDOWS_1025 = Charset.forName("windows-1250");

	protected final Reader in;

	public ReadAndConvertUtf8Task(Reader in, Writer out, AnyMemoConverterContext context)
			throws IOException, AnyMemoConverterException {
		super(in, out, context);

		if (!context.getInFile().exists()) {
			throw new AnyMemoConverterException("Input file don't exist.");
		}

		FileInputStream fis;
		if (context.getInFile().equals(context.getOutFile())) {
			if (isFileLock(context.getInFile())) {
				throw new AnyMemoConverterException("Input file is locked.");
			}
			
			File tmpInFile = File.createTempFile(FilenameUtils.getName(context.getInFile().getName()), ".txt");
			Files.move(context.getInFile().toPath(), tmpInFile.toPath(), REPLACE_EXISTING);
			tmpInFile.deleteOnExit();
			fis = new FileInputStream(tmpInFile);
		} else {
			fis = new FileInputStream(context.getInFile());
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
		}
	}

	protected boolean isFileLock(File file) throws IOException {
		boolean isLock = true;
		RandomAccessFile rf = null;
		FileChannel fileChannel = null;
		FileLock lock = null;
		try {
			rf = new RandomAccessFile(file, "rw");
			fileChannel = rf.getChannel();
			lock = fileChannel.tryLock();
			if (lock != null) {
				isLock = false;
			}
		} catch (Exception ex) {

		} finally {
			if (lock != null) {
				lock.release();
			}

			if (fileChannel != null) {
				fileChannel.close();
			}

			if (rf != null) {
				rf.close();
			}
		}
		return isLock;
	}

}