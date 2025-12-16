package com.vekrest.vekclient.service;

import com.vekrest.vekclient.entity.*;
import com.vekrest.vekclient.exception.NotFoundException;
import com.vekrest.vekclient.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ClientRepository.class);
    }

    @Test
    void registerShouldSaveNewClientWhenNotFound() {
        Client client = new Client("1", "John Doe", null, null, Status.APROVADO, false);
        when(repository.findById(client.id())).thenThrow(new NotFoundException());
        when(repository.save(any(Client.class))).thenReturn(client);

        Client result = repository.save(client);

        assertEquals(client, result);
        verify(repository).save(client);
    }

    @Test
    void registerShouldUpdateExistingClient() {
        Client existingClient = new Client("1", "John Doe", null, null, Status.APROVADO, false);
        Client newClient = new Client("1", "Jane Doe", null, null, Status.APROVADO, false);
        when(repository.findById(existingClient.id())).thenReturn(existingClient);
        when(repository.save(any(Client.class))).thenReturn(newClient);

        Client result = repository.save(newClient);

        assertEquals(newClient, result);
        verify(repository).save(newClient);
    }

    @Test
    void updateShouldModifyExistingClient() {
        Client existingClient = new Client("1", "John Doe", null, null, Status.APROVADO, false);
        Client updatedClient = new Client("1", "Jane Doe", null, null, Status.APROVADO, false);
        when(repository.findById(existingClient.id())).thenReturn(existingClient);
        when(repository.save(any(Client.class))).thenReturn(updatedClient);

        Client result = repository.save(updatedClient);

        assertEquals(updatedClient, result);
        verify(repository).save(updatedClient);
    }

    @Test
    void findByIdShouldReturnClientWhenFound() {
        Client client = new Client("1", "John Doe", null, null, Status.APROVADO, false);
        when(repository.findById(client.id())).thenReturn(client);

        Client result = repository.findById("1");

        assertEquals(client, result);
    }

    @Test
    void findByIdShouldThrowNotFoundExceptionWhenNotFound() {
        when(repository.findById("1")).thenThrow(new NotFoundException());

        assertThrows(NotFoundException.class, () -> repository.findById("1"));
    }

    @Test
    void deleteShouldRemoveClientAndReturnIt() {
        Client client = new Client("1", "John Doe", null, null, Status.APROVADO, false);
        when(repository.findById(client.id())).thenReturn(client);

        repository.delete("1");

        verify(repository).delete("1");
    }

    @Test
    void getAllShouldReturnPaginationOfClients() {
        Pagination<Client> pagination = new Pagination<>(
                List.of(
                        new Client(
                                "1",
                                "John Doe",
                                LocalDate.of(2025, 1, 1),
                                new Address("00000-000", State.SP),
                                Status.APROVADO,
                                false
                        )
                ), 0, 10, 1, 1
        );
        when(repository.getAll(1, 10)).thenReturn(pagination);

        Pagination<Client> result = repository.getAll(1, 10);

        assertEquals(pagination, result);
    }
}
