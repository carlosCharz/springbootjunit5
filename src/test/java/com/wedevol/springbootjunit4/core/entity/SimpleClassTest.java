package com.wedevol.springbootjunit4.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SimpleClassTest {

    @Test
    @DisplayName("Line array split not empty test")
    public void lineArraySplitNotEmptyTest() {
        String line = "4567|arturo|perez|teleport engineering manager|Germany|Telecommunications|3|176";
        String[] lineParts = line.split("\\|");
        Assertions.assertFalse(lineParts.length == 0);
    }
    
    @Disabled
    public void testDisabled() {
        Assertions.assertEquals(26, 14 + 12);
    }

}
