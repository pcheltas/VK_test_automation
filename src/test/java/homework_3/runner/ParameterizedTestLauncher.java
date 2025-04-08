package homework_3.runner;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Flexible test launcher that supports both package-wide and class-specific test execution.
 * <p>
 * Provides programmatic control over JUnit 5 test execution with dynamic test discovery capabilities.
 * Can run either all tests in the {@code homework_3.tests} package or specific test classes
 * specified as command-line arguments. Integration with custom reporting via {@link CustomTestListener}
 */
public class ParameterizedTestLauncher {
    public static void main(String[] args) {
        LauncherDiscoveryRequestBuilder requestBuilder = LauncherDiscoveryRequestBuilder.request();

        if (args.length == 0) {
            requestBuilder.selectors(selectPackage("homework_3.tests"));
        } else {
            for (String className : args) {
                try {
                    Class<?> testClass = Class.forName("homework_3.tests." + className);
                    requestBuilder.selectors(selectClass(testClass));
                } catch (ClassNotFoundException e) {
                    System.err.println("Test class not found: " + className);
                }
            }
        }

        LauncherDiscoveryRequest request = requestBuilder.build();
        Launcher launcher = LauncherFactory.create();
        launcher.execute(request, new CustomTestListener());
    }
}