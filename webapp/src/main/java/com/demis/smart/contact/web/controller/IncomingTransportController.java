package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingTransport;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.IncomingTransportService;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import com.demis.smart.contact.web.converter.IncomingTransportConverterWeb;
import com.demis.smart.contact.web.dto.IncomingTransportDTOWeb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(RestConfiguration.REST_BASE_URL)
public class IncomingTransportController extends GenericController<IncomingTransport, IncomingTransportDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingTransportController.class);

    @Autowired
    @Qualifier("incomingTransportService" )
    private IncomingTransportService incomingTransportService;

    @Autowired
    @Qualifier("incomingTransportConverterWeb" )
    private IncomingTransportConverterWeb incomingTransportConverterWeb;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/incomingTransport", "/incomingTransport/"})
    @ResponseBody
    public List<IncomingTransportDTOWeb> getIncomingTransports(@RequestParam(value="sort", required = false) String sortParameters,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<IncomingTransportDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<IncomingTransport> models = incomingTransportService.findPart(range, sorts);
        if (models.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            response.setHeader(HttpHeaders.CONTENT_RANGE.toString(), "resources " + range.getStart() + "-" + Math.min(range.getEnd(), models.size()) + "/*");
            response.setStatus(HttpStatus.OK.value());
            dtos = getConverter().convertModels(models, request);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/incomingTransport/{id}","/incomingTransport/{id}/"}, method = RequestMethod.GET)
    public Object getIncomingTransport(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingTransport IncomingTransport = incomingTransportService.findById(id);
        if (IncomingTransport != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingTransport.getUpdated().getTime());
            IncomingTransportDTOWeb dto = incomingTransportConverterWeb.convertModel(IncomingTransport, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingTransport/{id}", "/incomingTransport/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postIncomingTransport() {
    }

    @RequestMapping(value = {"/incomingTransport", "/incomingTransport/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postIncomingTransport(@RequestBody IncomingTransportDTOWeb IncomingTransportDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingTransport IncomingTransport = incomingTransportService.create(incomingTransportConverterWeb.convertDTO(IncomingTransportDTO));
        if (IncomingTransport != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingTransport.getUpdated().getTime());
            IncomingTransportDTOWeb dto = incomingTransportConverterWeb.convertModel(IncomingTransport, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingTransport", "/incomingTransport/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteIncomingTransports() {
    }

    @RequestMapping(value = {"/incomingTransport/{id}", "/incomingTransport/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteIncomingTransport(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        IncomingTransport IncomingTransport = incomingTransportService.findById(id);
        if (IncomingTransport != null) {
            try {
                incomingTransportService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the IncomingTransport: " + IncomingTransport, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // PUT
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingTransport", "/incomingTransport/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putIncomingTransports() {
    }

    @RequestMapping(value = {"/incomingTransport/{id}", "/incomingTransport/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putIncomingTransport(@PathVariable("id") Long id, @RequestBody IncomingTransportDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingTransport IncomingTransport = incomingTransportService.findById(id);
        if (IncomingTransport != null) {
            incomingTransportConverterWeb.updateModel(IncomingTransport, dto);
            try {
                IncomingTransport result = incomingTransportService.update(IncomingTransport);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                IncomingTransportDTOWeb resultDto = incomingTransportConverterWeb.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the IncomingTransport: " + IncomingTransport, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // OPTIONS
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingTransport", "/incomingTransport/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsIncomingTransports(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/incomingTransport/{id}", "/incomingTransport/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingTransport", "/incomingTransport/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headIncomingTransports(){
    }

    @RequestMapping(value = {"/incomingTransport/{id}", "/incomingTransport/{id}/"}, method = RequestMethod.HEAD)
    public void headIncomingTransport(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        IncomingTransport IncomingTransport = incomingTransportService.findById(id);
        if (IncomingTransport != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingTransport.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return incomingTransportConverterWeb;
    }


}
