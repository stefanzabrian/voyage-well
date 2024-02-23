package com.dev.voyagewell.service.user.client;

import com.dev.voyagewell.model.user.Client;
import com.dev.voyagewell.configuration.utils.exception.ResourceNotFoundException;

public interface ClientService {
    void save(Client client);
    void delete(Client client) throws ResourceNotFoundException;
}
