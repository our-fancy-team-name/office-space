package com.ourfancyteamname.officespace.annotations;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Test
@Tag("unit")
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(MockitoExtension.class)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UnitTest {
}
