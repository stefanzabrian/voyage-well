package com.dev.voyagewell.service.user.client;

import com.dev.voyagewell.model.user.Client;
import com.dev.voyagewell.repository.user.ClientRepository;
import com.dev.voyagewell.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void delete(Client client) throws ResourceNotFoundException {
        Client clientToBeDeleted = clientRepository.findById(client.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Client don't exists"));
        clientRepository.delete(clientToBeDeleted);
    }
}
