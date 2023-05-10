package com.webgroupEproject.myproject23526.Services;

import com.webgroupEproject.myproject23526.Model.RecServices;
import com.webgroupEproject.myproject23526.Model.UserClient;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientService {
    void saveClientOrder(UserClient serclientoder);
    List<UserClient> getALLClient();

    UserClient getClientById(long id);

    void DeleteOrder(long id);
}
