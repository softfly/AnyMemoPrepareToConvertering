package pl.softfly.amprepare.task;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import junitx.framework.FileAssert;
import pl.softfly.amprepare.AnyMemoConverter;
import pl.softfly.amprepare.TestCnsts;

public class AnyMemoConverterTaskTest {

	@Rule
	public TestName testName = new TestName();

	protected void test() throws Exception {
		File outDir = new File(TestCnsts.OUT_DIR);
		outDir.mkdir();

		String methodName = testName.getMethodName();
		File inFile = new File(TestCnsts.TESTFILE_DIR + methodName + ".txt");
		// For security
		File inFile2 = new File(TestCnsts.OUT_DIR + methodName + ".txt");
		Files.copy(inFile.toPath(), inFile2.toPath(), StandardCopyOption.REPLACE_EXISTING);
		File outFile = new File(TestCnsts.OUT_DIR + methodName + "-out.txt");

		AnyMemoConverter.main(inFile2.getAbsolutePath(), outFile.getAbsolutePath());
		File checkFile = new File(TestCnsts.TESTFILE_DIR + methodName + "-out.txt");
		FileAssert.assertEquals(checkFile, outFile);
	}

	@Test(timeout = 1000)
	public void testConvertUtf8Task() throws Exception {
		test();
	}

	@Test(timeout = 1000)
	public void testConvertLineFeedTask() throws Exception {
		test();
	}

	@Test(timeout = 1000)
	public void testRemoveLastWhiteSpaceTask() throws Exception {
		test();
	}

	@Test(timeout = 1000)
	public void testHtmlSpecialCharsTask() throws Exception {
		test();
	}

	@Test(timeout = 1000)
	public void testLong() throws Exception {
		test();
	}

}
