package io.bootify.administracion_biblioteca.service;

import io.bootify.administracion_biblioteca.domain.Cliente;
import io.bootify.administracion_biblioteca.domain.Libro;
import io.bootify.administracion_biblioteca.model.LibroDTO;
import io.bootify.administracion_biblioteca.repos.ClienteRepository;
import io.bootify.administracion_biblioteca.repos.LibroRepository;
import io.bootify.administracion_biblioteca.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final ClienteRepository clienteRepository;

    public LibroService(final LibroRepository libroRepository,
            final ClienteRepository clienteRepository) {
        this.libroRepository = libroRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("id"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final Long id) {
        return libroRepository.findById(id)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        return libroRepository.save(libro).getId();
    }

    public void update(final Long id, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final Long id) {
        libroRepository.deleteById(id);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setId(libro.getId());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor());
        libroDTO.setDisponible(libro.getDisponible());
        libroDTO.setCliente(libro.getCliente() == null ? null : libro.getCliente().getId());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setDisponible(libroDTO.getDisponible());
        final Cliente cliente = libroDTO.getCliente() == null ? null : clienteRepository.findById(libroDTO.getCliente())
                .orElseThrow(() -> new NotFoundException("cliente not found"));
        libro.setCliente(cliente);
        return libro;
    }

    public boolean clienteExists(final Long id) {
        return libroRepository.existsByClienteId(id);
    }

}
