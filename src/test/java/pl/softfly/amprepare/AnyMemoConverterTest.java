package pl.softfly.amprepare;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import junitx.framework.FileAssert;

public class AnyMemoConverterTest {

	@Rule
	public TestName testName = new TestName();

	@Test(timeout = 1000)
	public void testWithInFile() throws Exception {
		File outDir = new File(TestCnsts.OUT_DIR);
		outDir.mkdir();

		String methodName = testName.getMethodName();

		File inFile = new File(TestCnsts.TESTFILE_DIR + methodName + ".txt");
		File outFile = new File(TestCnsts.OUT_DIR + methodName + "-out.txt");
		Files.copy(inFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

		AnyMemoConverter.main(outFile.getAbsolutePath());

		File checkFile = new File(TestCnsts.TESTFILE_DIR + methodName + "-out.txt");
		FileAssert.assertEquals(checkFile, outFile);
	}

}