package com.demis.smart.contact.web.controller.converter;

import com.demis.smart.contact.RequestedRangeUnsatisfiableException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RangeConverterTest {

    @Test
    public void rangeParsing() throws RequestedRangeUnsatisfiableException {
        Assert.assertNotNull(RangeConverter.parse("resources: page=0; size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources:page=0; size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources:page=0;size=10"));
        Assert.assertNotNull(RangeConverter.parse("resources : page = 0 ; size = 10 "));
    }
}
