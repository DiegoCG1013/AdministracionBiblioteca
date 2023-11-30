package io.bootify.administracion_biblioteca.service;

import io.bootify.administracion_biblioteca.domain.Cliente;
import io.bootify.administracion_biblioteca.domain.Libro;
import io.bootify.administracion_biblioteca.model.ClienteDTO;
import io.bootify.administracion_biblioteca.repos.ClienteRepository;
import io.bootify.administracion_biblioteca.repos.LibroRepository;
import io.bootify.administracion_biblioteca.util.NotFoundException;
import io.bootify.administracion_biblioteca.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final LibroRepository libroRepository;

    public ClienteService(final ClienteRepository clienteRepository,
            final LibroRepository libroRepository) {
        this.clienteRepository = clienteRepository;
        this.libroRepository = libroRepository;
    }

    public List<ClienteDTO> findAll() {
        final List<Cliente> clientes = clienteRepository.findAll(Sort.by("id"));
        return clientes.stream()
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .toList();
    }

    public ClienteDTO get(final Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> mapToDTO(cliente, new ClienteDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ClienteDTO clienteDTO) {
        final Cliente cliente = new Cliente();
        mapToEntity(clienteDTO, cliente);
        return clienteRepository.save(cliente).getId();
    }

    public void update(final Long id, final ClienteDTO clienteDTO) {
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(clienteDTO, cliente);
        clienteRepository.save(cliente);
    }

    public void delete(final Long id) {
        clienteRepository.deleteById(id);
    }

    private ClienteDTO mapToDTO(final Cliente cliente, final ClienteDTO clienteDTO) {
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setUsuario(cliente.getUsuario());
        clienteDTO.setContrasenya(cliente.getContrasenya());
        return clienteDTO;
    }

    private Cliente mapToEntity(final ClienteDTO clienteDTO, final Cliente cliente) {
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setUsuario(clienteDTO.getUsuario());
        cliente.setContrasenya(clienteDTO.getContrasenya());
        return cliente;
    }

    public boolean usuarioExists(final String usuario) {
        return clienteRepository.existsByUsuarioIgnoreCase(usuario);
    }

    public String getReferencedWarning(final Long id) {
        final Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Libro clienteLibro = libroRepository.findFirstByCliente(cliente);
        if (clienteLibro != null) {
            return WebUtils.getMessage("cliente.libro.cliente.referenced", clienteLibro.getId());
        }
        return null;
    }

}
