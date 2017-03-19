package com.yuhuayuan.annotations;

import java.lang.annotation.*;

/**
 * Created by cl on 2017/3/16.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface IdGenerator {
    String name() default "";

    long offset() default 0L;
}
