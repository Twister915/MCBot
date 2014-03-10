package org.projectbot.inter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EngineMeta {
    String name();
    String implementor() default "GaiusCodez";
    String engineAuthor();
    boolean defaultEngine() default false;
}
