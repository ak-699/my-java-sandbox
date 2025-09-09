package com.slf4j.demo.lombok_demo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClass {
    public void testLombokAnnotation() {
        log.info("This is a log generated with lombok slf4j annotation");
    }

}
