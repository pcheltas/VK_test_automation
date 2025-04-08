package homework_3.runner;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.*;

/**
 * Custom test execution listener for JUnit Platform that provides real-time test progress reporting.
 * <p>
 * Implements basic test lifecycle tracking by printing test start/stop events to standard output.
 * This listener demonstrates how to integrate with JUnit's test execution lifecycle and can be
 * extended for more sophisticated reporting needs.
 *
 * <ul>
 *   <li>Tracks test execution start/stop events</li>
 *   <li>Reports test status (SUCCESSFUL, FAILED, ABORTED)</li>
 *   <li>Provides immediate feedback during test execution</li>
 * </ul>
 */
public class CustomTestListener implements TestExecutionListener {

    /**
     * Called when a test execution starts.
     * <p>
     * Prints the test display name to standard output to indicate test commencement.
     *
     * @param testIdentifier the descriptor of the starting test
     */
    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        System.out.println("Starting: " + testIdentifier.getDisplayName());
    }

    /**
     * Called when a test execution finishes.
     * <p>
     * Reports both the test name and its final execution status (SUCCESSFUL, FAILED, or ABORTED).
     *
     * @param testIdentifier the descriptor of the finished test
     * @param testExecutionResult the result of the test execution
     */
    @Override
    public void executionFinished(TestIdentifier testIdentifier,
                                  TestExecutionResult testExecutionResult) {
        System.out.println("Finished: " + testIdentifier.getDisplayName() +
                " - " + testExecutionResult.getStatus());
    }
}
