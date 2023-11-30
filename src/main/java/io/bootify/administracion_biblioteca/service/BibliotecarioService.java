package io.bootify.administracion_biblioteca.service;

import io.bootify.administracion_biblioteca.domain.Bibliotecario;
import io.bootify.administracion_biblioteca.model.BibliotecarioDTO;
import io.bootify.administracion_biblioteca.repos.BibliotecarioRepository;
import io.bootify.administracion_biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BibliotecarioService {

    private final BibliotecarioRepository bibliotecarioRepository;

    public BibliotecarioService(final BibliotecarioRepository bibliotecarioRepository) {
        this.bibliotecarioRepository = bibliotecarioRepository;
    }

    public List<BibliotecarioDTO> findAll() {
        final List<Bibliotecario> bibliotecarios = bibliotecarioRepository.findAll(Sort.by("id"));
        return bibliotecarios.stream()
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .toList();
    }

    public BibliotecarioDTO get(final Long id) {
        return bibliotecarioRepository.findById(id)
                .map(bibliotecario -> mapToDTO(bibliotecario, new BibliotecarioDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = new Bibliotecario();
        mapToEntity(bibliotecarioDTO, bibliotecario);
        return bibliotecarioRepository.save(bibliotecario).getId();
    }

    public void update(final Long id, final BibliotecarioDTO bibliotecarioDTO) {
        final Bibliotecario bibliotecario = bibliotecarioRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(bibliotecarioDTO, bibliotecario);
        bibliotecarioRepository.save(bibliotecario);
    }

    public void delete(final Long id) {
        bibliotecarioRepository.deleteById(id);
    }

    private BibliotecarioDTO mapToDTO(final Bibliotecario bibliotecario,
            final BibliotecarioDTO bibliotecarioDTO) {
        bibliotecarioDTO.setId(bibliotecario.getId());
        bibliotecarioDTO.setNombre(bibliotecario.getNombre());
        bibliotecarioDTO.setUsuario(bibliotecario.getUsuario());
        bibliotecarioDTO.setContrasenya(bibliotecario.getContrasenya());
        return bibliotecarioDTO;
    }

    private Bibliotecario mapToEntity(final BibliotecarioDTO bibliotecarioDTO,
            final Bibliotecario bibliotecario) {
        bibliotecario.setNombre(bibliotecarioDTO.getNombre());
        bibliotecario.setUsuario(bibliotecarioDTO.getUsuario());
        bibliotecario.setContrasenya(bibliotecarioDTO.getContrasenya());
        return bibliotecario;
    }

    public boolean usuarioExists(final String usuario) {
        return bibliotecarioRepository.existsByUsuarioIgnoreCase(usuario);
    }

}
