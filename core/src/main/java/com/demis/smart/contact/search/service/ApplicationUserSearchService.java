package com.demis.smart.contact.search.service;

import com.demis.smart.contact.jpa.entity.ApplicationUser;
import com.demis.smart.contact.search.converter.ApplicationUserConverter;
import com.demis.smart.contact.search.dto.ApplicationUserESDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value = "applicationUserSearchService")
public class ApplicationUserSearchService extends AbstractSearchService<ApplicationUser, ApplicationUserESDTO> {

    public static final String USER_MAPPING = "user";

    @Autowired
    @Qualifier("applicationUserConverterSearch")
    private ApplicationUserConverter applicationUserConverter;

    @Override
    protected ApplicationUserConverter getConverter() {
        return applicationUserConverter;
    }

    @Override
    protected String getMapping() {
        return USER_MAPPING;
    }

    @Override
    protected Class<ApplicationUserESDTO> getDTOClass() {
        return ApplicationUserESDTO.class;
    }
}
