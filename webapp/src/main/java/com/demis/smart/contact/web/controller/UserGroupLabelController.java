package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroupLabel;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.UserGroupLabelService;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import com.demis.smart.contact.web.converter.UserGroupLabelConverterWeb;
import com.demis.smart.contact.web.dto.UserGroupLabelDTOWeb;
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
public class UserGroupLabelController extends GenericController<UserGroupLabel, UserGroupLabelDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupLabelController.class);

    @Autowired
    @Qualifier("userGroupLabelService" )
    private UserGroupLabelService userGroupLabelService;

    @Autowired
    @Qualifier("userGroupLabelConverterWeb" )
    private UserGroupLabelConverterWeb userGroupLabelConverterWeb;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/userGroupLabel", "/userGroupLabel/"})
    @ResponseBody
    public List<UserGroupLabelDTOWeb> getUserGroupLabels(@RequestParam(value="sort", required = false) String sortParameters,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<UserGroupLabelDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<UserGroupLabel> models = userGroupLabelService.findPart(range, sorts);
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
    @RequestMapping(value = {"/userGroupLabel/{id}","/userGroupLabel/{id}/"}, method = RequestMethod.GET)
    public Object getUserGroupLabel(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroupLabel UserGroupLabel = userGroupLabelService.findById(id);
        if (UserGroupLabel != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroupLabel.getUpdated().getTime());
            UserGroupLabelDTOWeb dto = userGroupLabelConverterWeb.convertModel(UserGroupLabel, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroupLabel/{id}", "/userGroupLabel/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postUserGroupLabel() {
    }

    @RequestMapping(value = {"/userGroupLabel", "/userGroupLabel/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postUserGroupLabel(@RequestBody UserGroupLabelDTOWeb UserGroupLabelDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroupLabel UserGroupLabel = userGroupLabelService.create(userGroupLabelConverterWeb.convertDTO(UserGroupLabelDTO));
        if (UserGroupLabel != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroupLabel.getUpdated().getTime());
            UserGroupLabelDTOWeb dto = userGroupLabelConverterWeb.convertModel(UserGroupLabel, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroupLabel", "/userGroupLabel/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteUserGroupLabels() {
    }

    @RequestMapping(value = {"/userGroupLabel/{id}", "/userGroupLabel/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteUserGroupLabel(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        UserGroupLabel UserGroupLabel = userGroupLabelService.findById(id);
        if (UserGroupLabel != null) {
            try {
                userGroupLabelService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the UserGroupLabel: " + UserGroupLabel, e);
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

    @RequestMapping(value = {"/userGroupLabel", "/userGroupLabel/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putUserGroupLabels() {
    }

    @RequestMapping(value = {"/userGroupLabel/{id}", "/userGroupLabel/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putUserGroupLabel(@PathVariable("id") Long id, @RequestBody UserGroupLabelDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroupLabel UserGroupLabel = userGroupLabelService.findById(id);
        if (UserGroupLabel != null) {
            userGroupLabelConverterWeb.updateModel(UserGroupLabel, dto);
            try {
                UserGroupLabel result = userGroupLabelService.update(UserGroupLabel);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                UserGroupLabelDTOWeb resultDto = userGroupLabelConverterWeb.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the UserGroupLabel: " + UserGroupLabel, e);
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

    @RequestMapping(value = {"/userGroupLabel", "/userGroupLabel/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsUserGroupLabels(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/userGroupLabel/{id}", "/userGroupLabel/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroupLabel", "/userGroupLabel/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headUserGroupLabels(){
    }

    @RequestMapping(value = {"/userGroupLabel/{id}", "/userGroupLabel/{id}/"}, method = RequestMethod.HEAD)
    public void headUserGroupLabel(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        UserGroupLabel UserGroupLabel = userGroupLabelService.findById(id);
        if (UserGroupLabel != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroupLabel.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
    
    @Override
    protected GenericConverterWeb getConverter() {
        return userGroupLabelConverterWeb;
    }


}
