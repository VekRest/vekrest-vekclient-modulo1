package com.vekrest.repository;

import com.vekrest.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientRepository {
    Page<Client> getAll(Pageable pageable);

    Client save(Client client);

    Client findById(String id);

    void delete(String id);
}
