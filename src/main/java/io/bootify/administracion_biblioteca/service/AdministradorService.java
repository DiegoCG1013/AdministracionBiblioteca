package io.bootify.administracion_biblioteca.service;

import io.bootify.administracion_biblioteca.domain.Administrador;
import io.bootify.administracion_biblioteca.model.AdministradorDTO;
import io.bootify.administracion_biblioteca.repos.AdministradorRepository;
import io.bootify.administracion_biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorService(final AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

    public List<AdministradorDTO> findAll() {
        final List<Administrador> administradors = administradorRepository.findAll(Sort.by("id"));
        return administradors.stream()
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .toList();
    }

    public AdministradorDTO get(final Long id) {
        return administradorRepository.findById(id)
                .map(administrador -> mapToDTO(administrador, new AdministradorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AdministradorDTO administradorDTO) {
        final Administrador administrador = new Administrador();
        mapToEntity(administradorDTO, administrador);
        return administradorRepository.save(administrador).getId();
    }

    public void update(final Long id, final AdministradorDTO administradorDTO) {
        final Administrador administrador = administradorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(administradorDTO, administrador);
        administradorRepository.save(administrador);
    }

    public void delete(final Long id) {
        administradorRepository.deleteById(id);
    }

    private AdministradorDTO mapToDTO(final Administrador administrador,
            final AdministradorDTO administradorDTO) {
        administradorDTO.setId(administrador.getId());
        administradorDTO.setNombre(administrador.getNombre());
        administradorDTO.setUsuario(administrador.getUsuario());
        administradorDTO.setContrasenya(administrador.getContrasenya());
        return administradorDTO;
    }

    private Administrador mapToEntity(final AdministradorDTO administradorDTO,
            final Administrador administrador) {
        administrador.setNombre(administradorDTO.getNombre());
        administrador.setUsuario(administradorDTO.getUsuario());
        administrador.setContrasenya(administradorDTO.getContrasenya());
        return administrador;
    }

    public boolean usuarioExists(final String usuario) {
        return administradorRepository.existsByUsuarioIgnoreCase(usuario);
    }

}
