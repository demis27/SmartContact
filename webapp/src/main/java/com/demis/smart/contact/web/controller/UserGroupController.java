package com.demis.smart.contact.web.controller;

import com.demis.smart.contact.Range;
import com.demis.smart.contact.Sort;
import com.demis.smart.contact.jpa.entity.UserGroup;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.demis.smart.contact.service.UserGroupService;
import com.demis.smart.contact.web.RestConfiguration;
import com.demis.smart.contact.web.controller.exception.RangeException;
import com.demis.smart.contact.web.converter.GenericConverterWeb;
import com.demis.smart.contact.web.converter.UserGroupConverterWeb;
import com.demis.smart.contact.web.dto.UserGroupDTOWeb;
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
public class UserGroupController extends GenericController<UserGroup, UserGroupDTOWeb> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    @Qualifier("userGroupService" )
    private UserGroupService userGroupService;

    @Autowired
    @Qualifier("userGroupConverterWeb" )
    private UserGroupConverterWeb userGroupConverterWeb;

    @Autowired
    @Qualifier("restConfiguration")
    private RestConfiguration configuration;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET, value = {"/userGroup", "/userGroup/"})
    @ResponseBody
    public List<UserGroupDTOWeb> getUserGroups(@RequestParam(value="sort", required = false) String sortParameters,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<UserGroupDTOWeb> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<UserGroup> models = userGroupService.findPart(range, sorts);
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
    @RequestMapping(value = {"/userGroup/{id}","/userGroup/{id}/"}, method = RequestMethod.GET)
    public Object getUserGroup(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroup UserGroup = userGroupService.findById(id);
        if (UserGroup != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroup.getUpdated().getTime());
            UserGroupDTOWeb dto = userGroupConverterWeb.convertModel(UserGroup, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroup/{id}", "/userGroup/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postUserGroup() {
    }

    @RequestMapping(value = {"/userGroup", "/userGroup/"}, method = RequestMethod.POST)
    @ResponseBody
    public Object postUserGroup(@RequestBody UserGroupDTOWeb UserGroupDTO, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroup UserGroup = userGroupService.create(userGroupConverterWeb.convertDTO(UserGroupDTO));
        if (UserGroup != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroup.getUpdated().getTime());
            UserGroupDTOWeb dto = userGroupConverterWeb.convertModel(UserGroup, request);
            return dto;
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroup", "/userGroup/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteUserGroups() {
    }

    @RequestMapping(value = {"/userGroup/{id}", "/userGroup/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteUserGroup(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        UserGroup UserGroup = userGroupService.findById(id);
        if (UserGroup != null) {
            try {
                userGroupService.delete(id);
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't delete the UserGroup: " + UserGroup, e);
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

    @RequestMapping(value = {"/userGroup", "/userGroup/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putUserGroups() {
    }

    @RequestMapping(value = {"/userGroup/{id}", "/userGroup/{id}/"}, method = RequestMethod.PUT)
    @ResponseBody
    public Object putUserGroup(@PathVariable("id") Long id, @RequestBody UserGroupDTOWeb dto, HttpServletResponse httpResponse, HttpServletRequest request) {
        UserGroup UserGroup = userGroupService.findById(id);
        if (UserGroup != null) {
            userGroupConverterWeb.updateModel(UserGroup, dto);
            try {
                UserGroup result = userGroupService.update(UserGroup);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                UserGroupDTOWeb resultDto = userGroupConverterWeb.convertModel(result, request);
                return resultDto;
            } catch (ModelNotFoundException e) {
                LOGGER.warn("Can't modify the UserGroup: " + UserGroup, e);
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

    @RequestMapping(value = {"/userGroup", "/userGroup/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsUserGroups(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    @RequestMapping(value = {"/userGroup/{id}", "/userGroup/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsResouce(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,POST,DELETE,OPTIONS");
    }

    // ------------------------------------------------------------------------
    // HEAD
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"/userGroup", "/userGroup/"}, method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void headUserGroups(){
    }

    @RequestMapping(value = {"/userGroup/{id}", "/userGroup/{id}/"}, method = RequestMethod.HEAD)
    public void headUserGroup(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse){
        UserGroup UserGroup = userGroupService.findById(id);
        if (UserGroup != null) {
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, UserGroup.getUpdated().getTime());
            httpResponse.setStatus(HttpStatus.OK.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }

    @Override
    protected GenericConverterWeb getConverter() {
        return userGroupConverterWeb;
    }


}
