package KOWI2003.LaserMod.config;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation for fields in the Config class to describe a custom display name in the file
 * @author KOWI2003
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface ConfigName {

	String value();
	
}
