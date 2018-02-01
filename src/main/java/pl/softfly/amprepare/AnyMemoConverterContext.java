package pl.softfly.amprepare;

import java.io.File;

public class AnyMemoConverterContext {

	protected File inFile;

	protected File outFile;

	public File getInFile() {
		return inFile;
	}

	public void setInFile(File inFile) {
		this.inFile = inFile;
	}

	public File getOutFile() {
		return outFile;
	}

	public void setOutFile(File outFile) {
		this.outFile = outFile;
	}

}
