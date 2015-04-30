package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.IncomingEmail;
import com.demis.smart.contact.service.IncomingEmailService;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import com.demis.smart.contact.web.converter.IncomingEmailConverterWeb;
import com.demis.smart.contact.web.dto.IncomingEmailDTOWeb;
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
public class IncomingEmailController extends GenericController<IncomingEmail, IncomingEmailDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomingEmailController.class);

    @Autowired
    @Qualifier("incomingEmailService" )
    private IncomingEmailService incomingEmailService;

    @Autowired
    @Qualifier("incomingEmailConverterWeb" )
    private IncomingEmailConverterWeb incomingEmailConverterWeb;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/incomingEmail", "/incomingEmail/"})
    @ResponseBody
    public List<IncomingEmailDTOWeb> getIncomingEmails(@RequestParam(value="sort", required = false) String sortParameters,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<IncomingEmailDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<IncomingEmail> models = incomingEmailService.findPart(range, sorts);
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
    @RequestMapping(value = {"/incomingEmail/{id}","/incomingEmail/{id}/"}, method = RequestMethod.GET)
    public Object getIncomingEmail(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingEmail IncomingEmail = incomingEmailService.findById(id);
        if (IncomingEmail != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingEmail.getUpdated().getTime());
            IncomingEmailDTOWeb dto = incomingEmailConverterWeb.convertModel(IncomingEmail, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingEmail/{id}", "/incomingEmail/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postIncomingEmail() {
    }

    @RequestMapping(value = {"/incomingEmail", "/incomingEmail/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postIncomingEmail(@RequestBody IncomingEmailDTOWeb IncomingEmailDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingEmail IncomingEmail = incomingEmailService.create(incomingEmailConverterWeb.convertDTO(IncomingEmailDTO));
        if (IncomingEmail != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingEmail.getUpdated().getTime());
            IncomingEmailDTOWeb dto = incomingEmailConverterWeb.convertModel(IncomingEmail, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingEmail", "/incomingEmail/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteIncomingEmails() {
    }

    @RequestMapping(value = {"/incomingEmail/{id}", "/incomingEmail/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteIncomingEmail(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        IncomingEmail IncomingEmail = incomingEmailService.findById(id);
        if (IncomingEmail != null) {
            try {
                incomingEmailService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the IncomingEmail: " + IncomingEmail, e);
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

    @RequestMapping(value = {"/incomingEmail", "/incomingEmail/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putIncomingEmails() {
    }

    @RequestMapping(value = {"/incomingEmail/{id}", "/incomingEmail/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putIncomingEmail(@PathVariable("id") Long id, @RequestBody IncomingEmailDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        IncomingEmail IncomingEmail = incomingEmailService.findById(id);
        if (IncomingEmail != null) {
            incomingEmailConverterWeb.updateModel(IncomingEmail, dto);
            try {
                IncomingEmail result = incomingEmailService.update(IncomingEmail);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                IncomingEmailDTOWeb resultDto = incomingEmailConverterWeb.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the IncomingEmail: " + IncomingEmail, e);
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

    @RequestMapping(value = {"/incomingEmail", "/incomingEmail/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsIncomingEmails(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/incomingEmail/{id}", "/incomingEmail/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/incomingEmail", "/incomingEmail/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headIncomingEmails(){
    }

    @RequestMapping(value = {"/incomingEmail/{id}", "/incomingEmail/{id}/"}, method = RequestMethod.HEAD)
    public void headIncomingEmail(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        IncomingEmail IncomingEmail = incomingEmailService.findById(id);
        if (IncomingEmail != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingEmail.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return incomingEmailConverterWeb;
    }


}
