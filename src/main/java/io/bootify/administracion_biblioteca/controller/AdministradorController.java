package io.bootify.administracion_biblioteca.controller;

import io.bootify.administracion_biblioteca.model.AdministradorDTO;
import io.bootify.administracion_biblioteca.service.AdministradorService;
import io.bootify.administracion_biblioteca.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/administradors")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(final AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("administradors", administradorService.findAll());
        return "administrador/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("administrador") final AdministradorDTO administradorDTO) {
        return "administrador/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("administrador") @Valid final AdministradorDTO administradorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("usuario") && administradorService.usuarioExists(administradorDTO.getUsuario())) {
            bindingResult.rejectValue("usuario", "Exists.administrador.usuario");
        }
        if (bindingResult.hasErrors()) {
            return "administrador/add";
        }
        administradorService.create(administradorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("administrador.create.success"));
        return "redirect:/administradors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("administrador", administradorService.get(id));
        return "administrador/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("administrador") @Valid final AdministradorDTO administradorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final AdministradorDTO currentAdministradorDTO = administradorService.get(id);
        if (!bindingResult.hasFieldErrors("usuario") &&
                !administradorDTO.getUsuario().equalsIgnoreCase(currentAdministradorDTO.getUsuario()) &&
                administradorService.usuarioExists(administradorDTO.getUsuario())) {
            bindingResult.rejectValue("usuario", "Exists.administrador.usuario");
        }
        if (bindingResult.hasErrors()) {
            return "administrador/edit";
        }
        administradorService.update(id, administradorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("administrador.update.success"));
        return "redirect:/administradors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        administradorService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("administrador.delete.success"));
        return "redirect:/administradors";
    }

}
