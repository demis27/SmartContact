package com.demis.smart.contact.web.converter;

import com.demis.smart.contact.jpa.entity.ApplicationUser;
import com.demis.smart.contact.web.dto.ApplicationUserDTOWeb;
import org.springframework.stereotype.Service;

@Service(value = "applicationUserConverterWeb")
public class ApplicationUserConverterWeb extends GenericConverterWeb<ApplicationUser, ApplicationUserDTOWeb> {

    public ApplicationUserConverterWeb() {
        super(ApplicationUser.class, ApplicationUserDTOWeb.class);
    }

    protected void updateModelFields(ApplicationUser ApplicationUser, ApplicationUserDTOWeb ApplicationUserDTO) {
    }

    protected void updateDTOFields(ApplicationUserDTOWeb ApplicationUserDTO, ApplicationUser ApplicationUser) {
    }
}