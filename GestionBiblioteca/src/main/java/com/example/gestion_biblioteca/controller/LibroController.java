package com.example.gestion_biblioteca.controller;

import com.example.gestion_biblioteca.domain.Prestamo;
import com.example.gestion_biblioteca.model.LibroDTO;
import com.example.gestion_biblioteca.repos.PrestamoRepository;
import com.example.gestion_biblioteca.service.LibroService;
import com.example.gestion_biblioteca.util.CustomCollectors;
import com.example.gestion_biblioteca.util.WebUtils;
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
    private final PrestamoRepository prestamoRepository;

    public LibroController(final LibroService libroService,
            final PrestamoRepository prestamoRepository) {
        this.libroService = libroService;
        this.prestamoRepository = prestamoRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("prestamoValues", prestamoRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Prestamo::getId, Prestamo::getId)));
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
        if (!bindingResult.hasFieldErrors("isbn") && libroDTO.getIsbn() == null) {
            bindingResult.rejectValue("isbn", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("isbn") && libroService.isbnExists(libroDTO.getIsbn())) {
            bindingResult.rejectValue("isbn", "Exists.libro.isbn");
        }
        if (bindingResult.hasErrors()) {
            return "libro/add";
        }
        libroService.create(libroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libro.create.success"));
        return "redirect:/libros";
    }

    @GetMapping("/edit/{isbn}")
    public String edit(@PathVariable(name = "isbn") final String isbn, final Model model) {
        model.addAttribute("libro", libroService.get(isbn));
        return "libro/edit";
    }

    @PostMapping("/edit/{isbn}")
    public String edit(@PathVariable(name = "isbn") final String isbn,
            @ModelAttribute("libro") @Valid final LibroDTO libroDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "libro/edit";
        }
        libroService.update(isbn, libroDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("libro.update.success"));
        return "redirect:/libros";
    }

    @PostMapping("/delete/{isbn}")
    public String delete(@PathVariable(name = "isbn") final String isbn,
            final RedirectAttributes redirectAttributes) {
        libroService.delete(isbn);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("libro.delete.success"));
        return "redirect:/libros";
    }

}
