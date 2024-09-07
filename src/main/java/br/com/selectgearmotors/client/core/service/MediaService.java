package br.com.selectgearmotors.client.core.service;

import br.com.selectgearmotors.client.application.api.exception.ResourceFoundException;
import br.com.selectgearmotors.client.core.domain.Media;
import br.com.selectgearmotors.client.core.ports.in.client.*;
import br.com.selectgearmotors.client.core.ports.in.media.*;
import br.com.selectgearmotors.client.core.ports.out.MediaRepositoryPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MediaService implements CreateMediaPort, UpdateMediaPort, FindByIdMediaPort, FindMediasPort, DeleteMediaPort {

    private final MediaRepositoryPort clientRepository;

    @Override
    public Media save(Media client) throws ResourceFoundException {
        return clientRepository.save(client);
    }

    @Override
    public Media update(Long id, Media client) {
        Media resultById = findById(id);
        if (resultById != null) {
            resultById.update(id, client);

            return clientRepository.save(resultById);
        }

        return null;
    }

    @Override
    public Media findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Media> findAll() {
       return clientRepository.findAll();
    }

    @Override
    public boolean remove(Long id) {
        try {
            Media clientById = findById(id);
            if (clientById == null) {
                throw new ResourceFoundException("Media not found");
            }

            clientRepository.remove(id);
            return Boolean.TRUE;
        } catch (ResourceFoundException e) {
            log.error("Erro ao remover produto: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }
}
