package com.example.gestion_biblioteca.controller;

import com.example.gestion_biblioteca.model.BibliotecarioDTO;
import com.example.gestion_biblioteca.service.BibliotecarioService;
import com.example.gestion_biblioteca.util.WebUtils;
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
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioController(final BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bibliotecarios", bibliotecarioService.findAll());
        return "bibliotecario/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bibliotecario") final BibliotecarioDTO bibliotecarioDTO) {
        return "bibliotecario/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("bibliotecario") @Valid final BibliotecarioDTO bibliotecarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bibliotecario/add";
        }
        bibliotecarioService.create(bibliotecarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecario.create.success"));
        return "redirect:/bibliotecarios";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("bibliotecario", bibliotecarioService.get(id));
        return "bibliotecario/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("bibliotecario") @Valid final BibliotecarioDTO bibliotecarioDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "bibliotecario/edit";
        }
        bibliotecarioService.update(id, bibliotecarioDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecario.update.success"));
        return "redirect:/bibliotecarios";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = bibliotecarioService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            bibliotecarioService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bibliotecario.delete.success"));
        }
        return "redirect:/bibliotecarios";
    }

}
