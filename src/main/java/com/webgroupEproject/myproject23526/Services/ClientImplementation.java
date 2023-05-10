package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.UserClient;
import com.webgroupEproject.myproject23526.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientImplementation implements ClientService{
   @Autowired
    private ClientRepository clientRepository;

    @Override
    public void saveClientOrder(UserClient userClient) {
        this.clientRepository.save(userClient);
    }

    @Override
    public List<UserClient> getALLClient() {
        return clientRepository.findAll();
    }

    @Override
    public UserClient getClientById(long id) {
        Optional<UserClient> optional = clientRepository.findById(id);
        UserClient userClient = null;

        if (optional.isPresent()) {
            userClient = optional.get();
        } else {
            throw new RuntimeException("Non such Client!!! with id " + id);
        }
    return userClient;
    }

    @Override
    public void DeleteOrder(long id) {
        this.clientRepository.deleteById(id);
    }
}
