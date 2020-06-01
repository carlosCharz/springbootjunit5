package com.wedevol.springbootjunit4.core.entity;

import static org.junit.Assert.assertEquals;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SimpleClassTest {

    @Test
    public void lineArraySplitNotEmptyTest() {
        String line = "4567|arturo|perez|teleport engineering manager|Germany|Telecommunications|3|176";
        String[] lineParts = line.split("\\|");
        Assert.assertFalse(lineParts.length == 0);
    }
    
    @Ignore
    public void testDisabled() {
        assertEquals(26, 14 + 12);
    }

}
