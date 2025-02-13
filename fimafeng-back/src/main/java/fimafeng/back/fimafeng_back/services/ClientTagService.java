package fimafeng.back.fimafeng_back.services;

import fimafeng.back.fimafeng_back.models.ClientTag;
import fimafeng.back.fimafeng_back.repositories.ClientTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientTagService {
    @Autowired
    private ClientTagRepository clientTagRepository;

    public ClientTag save(ClientTag clientTag) {
        return clientTagRepository.save(clientTag);
    }


    public List<ClientTag> saveAll(List<ClientTag> clientTags) { return clientTagRepository.saveAll(clientTags); }


    public ClientTag findById(int idClientTag) {
        Optional<ClientTag> optionalClientTag = clientTagRepository.findById(idClientTag);
        return optionalClientTag.orElse(null);
    }

    public List<ClientTag> findAll() {
        return clientTagRepository.findAll();
    }

    public boolean update(ClientTag updatedClientTag) {
        if (updatedClientTag == null) throw new IllegalArgumentException("updatedClientTag is null");
        int id = updatedClientTag.getId();

        Optional<ClientTag> optionalClientTag = clientTagRepository.findById(id);
        if (optionalClientTag.isEmpty()) return false;

        ClientTag clientTag = optionalClientTag.get();
        clientTag.setRefClientId(updatedClientTag.getRefClientId());
        clientTag.setRefTagId(updatedClientTag.getRefTagId());

        clientTagRepository.saveAndFlush(clientTag);
        return true;
    }

    public boolean delete(int idClientTag) {
        Optional<ClientTag> optionalClientTag = clientTagRepository.findById(idClientTag);
        if (optionalClientTag.isPresent()) {
            clientTagRepository.deleteById(idClientTag);
            return true;
        }
        return false;
    }
}
