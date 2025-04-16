package homework_3.annotations;

import homework_3.services.BotFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks test methods or test classes as requiring bot instance for execution.
 * <p>
 * When applied to a test method or class, triggers automatic bot initialization
 * through {@link BotFactory} before test execution. The bot instance will be
 * available via the protected {@code bot} field in the base test class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BotRequired {}
