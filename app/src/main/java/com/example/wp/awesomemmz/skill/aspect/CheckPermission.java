package com.example.wp.awesomemmz.skill.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
    //声明参数 permission (注解内仅仅只有一个名字为 value 的属性时，应用这个注解时可以直接接属性值填写到括号内)
    String[] value();
}