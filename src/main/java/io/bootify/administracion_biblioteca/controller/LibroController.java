package io.bootify.administracion_biblioteca.controller;

import io.bootify.administracion_biblioteca.domain.Cliente;
import io.bootify.administracion_biblioteca.model.LibroDTO;
import io.bootify.administracion_biblioteca.repos.ClienteRepository;
import io.bootify.administracion_biblioteca.service.LibroService;
import io.bootify.administracion_biblioteca.util.CustomCollectors;
import io.bootify.administracion_biblioteca.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;
    private final ClienteRepository clienteRepository;

    public LibroController(final LibroService libroService,
            final ClienteRepository clienteRepository) {
        this.libroService = libroService;
        this.clienteRepository = clienteRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("clienteValues", clienteRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Cliente::getId, Cliente::getNombre)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("libroes", libroService.findAll());
        return "libro/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("libro") final LibroDTO libroDTO) {
        return "libro/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("libro") @Valid final LibroDTO libroDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("cliente") && libroDTO.getCliente() != null && libroService.clienteExists(libroDTO.getCliente())) {
            bindingResult.rejectValue("cliente", "Exists.libro.cliente");
        }
        if (bindingResult.hasErrors()) {
            return "libro/add";
        }
        libroService.create(libroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libro.create.success"));
        return "redirect:/libros";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("libro", libroService.get(id));
        return "libro/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("libro") @Valid final LibroDTO libroDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LibroDTO currentLibroDTO = libroService.get(id);
        if (!bindingResult.hasFieldErrors("cliente") && libroDTO.getCliente() != null &&
                !libroDTO.getCliente().equals(currentLibroDTO.getCliente()) &&
                libroService.clienteExists(libroDTO.getCliente())) {
            bindingResult.rejectValue("cliente", "Exists.libro.cliente");
        }
        if (bindingResult.hasErrors()) {
            return "libro/edit";
        }
        libroService.update(id, libroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libro.update.success"));
        return "redirect:/libros";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        libroService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("libro.delete.success"));
        return "redirect:/libros";
    }

}
