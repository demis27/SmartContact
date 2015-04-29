package com.demis.smart.contact.search.converter;

import com.demis.smart.contact.jpa.entity.ApplicationUser;
import com.demis.smart.contact.search.dto.ApplicationUserESDTO;
import org.springframework.stereotype.Service;

@Service(value = "applicationUserConverterSearch")
public class ApplicationUserConverter extends GenericConverter<ApplicationUser, ApplicationUserESDTO> {

    public ApplicationUserConverter() {
        super(ApplicationUser.class, ApplicationUserESDTO.class);
    }

    @Override
    protected void updateModelFields(ApplicationUser model, ApplicationUserESDTO dto) {
        model.setEmail(dto.getEmail());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setLogin(dto.getLogin());
    }

    @Override
    protected void updateDTOFields(ApplicationUserESDTO dto, ApplicationUser model) {
        dto.setEmail(model.getEmail());
        dto.setFirstName(model.getFirstName());
        dto.setLastName(model.getLastName());
        dto.setLogin(model.getLogin());
    }
}
