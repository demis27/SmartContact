package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.RequestedRangeUnsatisfiableException;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.converter.RangeConverter;
import com.demis.smart.contact.web.controller.converter.SortConverter;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

public abstract class GenericController<M, DTO> {

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericController.class);

    protected abstract GenericConverterWeb getConverter();

    public Range getRange(String requestRange) throws RangeException {
        Range range = null;

        if (requestRange != null) {
            try {
                range = RangeConverter.parse(requestRange);
            } catch (RequestedRangeUnsatisfiableException e) {
                String reason = "Wrong format for the range parameter. The format is: \"resources: page=[page-number];size=[page-size]\" and the parameter value is: " + requestRange;
                throw new RangeException(reason);
            }
        }
        else {
            range = new Range(0, configuration.getDefaultPageSize());
        }
        return range;
    }

    public List<Sort> getSorts(String sortParameters) {
        List<Sort> sorts;
        if (sortParameters != null && sortParameters.length() > 0) {
            sorts = SortConverter.parse(sortParameters);
        }
        else {
            sorts = Collections.emptyList();
        }
        return sorts;
    }

    @ExceptionHandler(RangeException.class)
    public Object handleRangeException(HttpServletRequest request, HttpServletResponse httpResponse, RangeException ex) {
        LOGGER.warn(ex.getReason());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ex;
    }
}
