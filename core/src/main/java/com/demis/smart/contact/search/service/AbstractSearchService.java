package com.demis.smart.contact.search.service;

import com.demis.smart.contact.dto.DTO;
import com.demis.smart.contact.jpa.entity.Model;
import com.demis.smart.contact.search.SearchConfig;
import com.demis.smart.contact.search.converter.GenericConverter;
import com.demis.smart.contact.service.ModelNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public abstract class AbstractSearchService<M extends Model, D extends DTO> {

    private static Logger LOGGER = LoggerFactory.getLogger(AbstractSearchService.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private Client client;

    @Autowired
    private SearchConfig configuration;

    public M create(M created) {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), created.getId().toString())
                    .setSource(mapper.writeValueAsString(getConverter().convertModel(created)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in JSon conversion for model: " + created, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return created;

    }

    public void delete(Long id) throws ModelNotFoundException {
        client.prepareDelete(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
    }

    public M update(M updated) throws ModelNotFoundException {
        try {
            client.prepareIndex(configuration.getIndexName(), getMapping(), updated.getId().toString())
                    .setSource(mapper.writeValueAsString(getConverter().convertModel(updated)))
                    .execute()
                    .actionGet();
        } catch (JsonProcessingException e) {
            LOGGER.error("Error in JSon conversion for model: " + updated, e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updated;

    }

    public M getById(Long id) throws IOException {
        GetResponse response = client.prepareGet(configuration.getIndexName(), getMapping(), id.toString())
                .execute()
                .actionGet();
        LOGGER.debug("Response as String: " + response.getSourceAsString());
        if (response.getSource() == null) {
            return null;
        }
        else {
            D dto = mapper.readValue(response.getSourceAsString(), getDTOClass());
            return getConverter().convertDTO(dto);
        }
    }

    protected abstract Class<D> getDTOClass();

    protected abstract GenericConverter<M, D> getConverter();

    protected abstract String getMapping();
}
