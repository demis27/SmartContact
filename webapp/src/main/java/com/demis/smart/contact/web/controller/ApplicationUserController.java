package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.ApplicationUser;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.ApplicationUserService;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import com.demis.smart.contact.web.converter.ApplicationUserConverterWeb;
import com.demis.smart.contact.web.dto.ApplicationUserDTOWeb;
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
public class ApplicationUserController extends GenericController<ApplicationUser, ApplicationUserDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUserController.class);

    @Autowired
    @Qualifier("applicationUserService" )
    private ApplicationUserService applicationUserService;

    @Autowired
    @Qualifier("applicationUserConverterWeb" )
    private ApplicationUserConverterWeb applicationUserConverterWeb;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/applicationUser", "/applicationUser/"})
    @ResponseBody
    public List<ApplicationUserDTOWeb> getApplicationUsers(@RequestParam(value="sort", required = false) String sortParameters,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<ApplicationUserDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<ApplicationUser> models = applicationUserService.findPart(range, sorts);
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
    @RequestMapping(value = {"/applicationUser/{id}","/applicationUser/{id}/"}, method = RequestMethod.GET)
    public Object getApplicationUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        ApplicationUser ApplicationUser = applicationUserService.findById(id);
        if (ApplicationUser != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, ApplicationUser.getUpdated().getTime());
            ApplicationUserDTOWeb dto = applicationUserConverterWeb.convertModel(ApplicationUser, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/applicationUser/{id}", "/applicationUser/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postApplicationUser() {
    }

    @RequestMapping(value = {"/applicationUser", "/applicationUser/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postApplicationUser(@RequestBody ApplicationUserDTOWeb ApplicationUserDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        ApplicationUser ApplicationUser = applicationUserService.create(applicationUserConverterWeb.convertDTO(ApplicationUserDTO));
        if (ApplicationUser != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, ApplicationUser.getUpdated().getTime());
            ApplicationUserDTOWeb dto = applicationUserConverterWeb.convertModel(ApplicationUser, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/applicationUser", "/applicationUser/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteApplicationUsers() {
    }

    @RequestMapping(value = {"/applicationUser/{id}", "/applicationUser/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteApplicationUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        ApplicationUser ApplicationUser = applicationUserService.findById(id);
        if (ApplicationUser != null) {
            try {
                applicationUserService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the ApplicationUser: " + ApplicationUser, e);
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

    @RequestMapping(value = {"/applicationUser", "/applicationUser/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putApplicationUsers() {
    }

    @RequestMapping(value = {"/applicationUser/{id}", "/applicationUser/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putApplicationUser(@PathVariable("id") Long id, @RequestBody ApplicationUserDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        ApplicationUser ApplicationUser = applicationUserService.findById(id);
        if (ApplicationUser != null) {
            applicationUserConverterWeb.updateModel(ApplicationUser, dto);
            try {
                ApplicationUser result = applicationUserService.update(ApplicationUser);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                ApplicationUserDTOWeb resultDto = applicationUserConverterWeb.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the ApplicationUser: " + ApplicationUser, e);
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

    @RequestMapping(value = {"/applicationUser", "/applicationUser/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsApplicationUsers(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/applicationUser/{id}", "/applicationUser/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/applicationUser", "/applicationUser/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headApplicationUsers(){
    }

    @RequestMapping(value = {"/applicationUser/{id}", "/applicationUser/{id}/"}, method = RequestMethod.HEAD)
    public void headApplicationUser(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        ApplicationUser ApplicationUser = applicationUserService.findById(id);
        if (ApplicationUser != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, ApplicationUser.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return applicationUserConverterWeb;
    }


}
