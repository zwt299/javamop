package com.runtimeverification.rvmonitor.examples.c;

import com.runtimeverification.rvmonitor.examples.TestHelper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Base class for C examples JUnit tests.
 * @author TraianSF
 */
@RunWith(Parameterized.class)
public class CExamplesIT {
    TestHelper helper;


    public CExamplesIT(String specPath) {
        helper = new TestHelper(specPath);
    }

    /**
     * Builds the tests, then runs them.
     * Matches precomputed expected output files against the output generated by the tests.
     * @throws Exception
     */
    @Test
    public void testTest() throws Exception {
        helper.testCommand(null, "make", "clean");
        helper.testCommand(null, "make");
        helper.testCommand("tests/test", "make", "-s", "test");
        helper.testCommand(null, "make", "clean");
        helper.deleteFiles(false,
                "tests/test.actual.out",
                "tests/test.actual.err"
        );

    }

    // The method bellow creates the set of parameter instances to be used as seeds by
    // the test constructor.  Junit will run the testsuite once for each parameter instance.
    // This is documented in the Junit Parameterized tests page:
    // http://junit.sourceforge.net/javadoc/org/junit/runners/Parameterized.html
    @Parameterized.Parameters(name="{0}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<Object[]>();
        for (File rvmFile : FileUtils.listFiles(new File("../examples/c"), new String[]{"rvm"}, true)) {
            String specPath = rvmFile.getPath();
            data.add(new Object[] {specPath});
        }
        return data;
    }
}
