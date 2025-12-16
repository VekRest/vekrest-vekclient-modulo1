package com.vekrest.vekclient.vekclient.repository;

import com.vekrest.vekclient.entity.Client;
import com.vekrest.vekclient.entity.Pagination;
import com.vekrest.vekclient.exception.InternalServerException;
import com.vekrest.vekclient.exception.NotFoundException;
import com.vekrest.vekclient.repository.ClientRepository;
import com.vekrest.vekclient.vekclient.repository.adapter.ClientRepositoryAdapter;
import com.vekrest.vekclient.vekclient.repository.client.ClientRepositoryWithMongo;
import com.vekrest.vekclient.vekclient.repository.orm.ClientOrm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ClientRepositoryImpl.class);

    private final ClientRepositoryWithMongo repository;

    public ClientRepositoryImpl(ClientRepositoryWithMongo repository) {
        this.repository = repository;
    }

    @Override
    public Pagination<Client> getAll(final int pageNumber, final int pageSize) {
        try {
            LOG.info("Recuperando todos os clientes na data/hora: {}", LocalDateTime.now());

            Page<ClientOrm> pageClientOrm =  repository.findAllByStatusAtivo(Pageable.ofSize(pageSize).withPage(pageNumber));

            return ClientRepositoryAdapter.cast(pageClientOrm);
        } catch (Exception ex) {
            LOG.error("Erro ao recuperar clientes: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    @Override
    public Client save(Client client) {
        try {
            LOG.info("Salvando cliente na data/hora: {}", LocalDateTime.now());

            ClientOrm orm = ClientRepositoryAdapter.cast(client);

            return ClientRepositoryAdapter.cast(repository.save(orm));
        } catch (Exception ex) {
            LOG.error("Erro ao salvar cliente: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    @Override
    public Client update(Client client) {
        try {
            LOG.info("Atualizando cliente na data/hora: {}", LocalDateTime.now());

            Client oldClient = this.findById(client.id());

            ClientOrm orm = ClientRepositoryAdapter.cast(
                    new Client(
                            client.id(),
                            client.name() == null ? oldClient.name() : client.name(),
                            client.birth() == null ? oldClient.birth() : client.birth(),
                            client.address() == null ? oldClient.address() : client.address(),
                            client.status() == null ? oldClient.status() : client.status(),
                            oldClient.isDeleted()
                    )
            );

            return ClientRepositoryAdapter.cast(repository.save(orm));
        } catch (NotFoundException ex) {
            LOG.error("Cliente nao encontrado para atualizar: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw ex;
        } catch (Exception ex) {
            LOG.error("Erro ao atualizar cliente: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    @Override
    public Client findById(final String id) {
        try {
            LOG.info("Procurando cliente por id: {} na data/hora: {}", id, LocalDateTime.now());

            Optional<ClientOrm> optional = repository.findById(id);

            if (optional.isEmpty()) {
                throw new NotFoundException("Cliente nao encontrado");
            }

            return ClientRepositoryAdapter.cast(repository.save(optional.get()));
        } catch (NotFoundException ex) {
            LOG.error("Cliente nao encontrado por id: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw ex;
        } catch (Exception ex) {
            LOG.error("Erro ao procurar cliente por id: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }

    @Override
    public void delete(final String id) {
        try {
            LOG.info("Deletando lógicamente o cliente por id: {} na data/hora: {}", id, LocalDateTime.now());

            Client client = this.findById(id);
            this.save(
                    new Client(
                            client.id(),
                            client.name(),
                            client.birth(),
                            client.address(),
                            client.status(),
                            true
                    )
            );
        } catch (NotFoundException ex) {
            LOG.error("Cliente nao encontrado para deletar: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw ex;
        } catch (Exception ex) {
            LOG.error("Erro ao deletar cliente: {} o erro aconteceu na data/hora: {}", ex.getMessage(), LocalDateTime.now());
            throw new InternalServerException(ex);
        }
    }
}
