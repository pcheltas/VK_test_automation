package homework_3.runner;

import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.*;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Test suite launcher for executing JUnit 5 tests in the homework_3.tests package.
 * <p>
 * This launcher provides programmatic control over test execution and integrates with custom reporting
 * through {@link CustomTestListener}. It automatically discovers and runs all tests found in the
 * specified package.
 */
public class AllTestsLauncher {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("homework_3.tests")
                )
                .build();

        LauncherFactory.create()
                .execute(request, new CustomTestListener());
    }
}