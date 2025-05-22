package org.lessons.java.spring_la_mia_pizzeria_crud.Controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.ScontiPizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.ScontiPizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/scontiPizza")
public class ScontiPizzaController {

    @Autowired
    private ScontiPizzaRepository repository;

    // Crea
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("scontiPizza") ScontiPizza formScontiPizza,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", false);
            return "scontiPizza/create-or-edit";
        }

        repository.save(formScontiPizza);
        return "redirect:/pizzas/" + formScontiPizza.getPizza().getId();
    }

    // Modifica: mostra form
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        model.addAttribute("scontiPizza", repository.findById(id).get());
        model.addAttribute("edit", true);
        return "scontiPizza/create-or-edit";
    }

    // Modifica: aggiorna e salva
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("scontiPizza") ScontiPizza formScontiPizza,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return "scontiPizza/create-or-edit";
        }

        repository.save(formScontiPizza);
        return "redirect:/pizzas/" + formScontiPizza.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        repository.deleteById(id);
        return "redirect:/pizzas";
    }
}
