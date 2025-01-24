package fimafeng.back.proto_back.services;

import fimafeng.back.proto_back.models.Client;
import fimafeng.back.proto_back.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(int idClient) {
        Optional<Client> optionalClient = clientRepository.findById(idClient);
        return optionalClient.orElse(null);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public boolean update(Client updatedClient) {
        if (updatedClient == null) throw new IllegalArgumentException("client is null");
        int id = updatedClient.getId();

        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) return false;

        Client client = optionalClient.get();
        client.setFirstName(updatedClient.getFirstName());
        client.setLastName(updatedClient.getLastName());
        client.setEmail(updatedClient.getEmail());
        client.setDistrict(updatedClient.getDistrict());

        clientRepository.saveAndFlush(client);
        return true;
    }

    public boolean delete(int idClient) {
        Optional<Client> optionalClient = clientRepository.findById(idClient);
        if (optionalClient.isPresent()) {
            clientRepository.deleteById(idClient);
            return true;
        }
        return false;
    }

}
