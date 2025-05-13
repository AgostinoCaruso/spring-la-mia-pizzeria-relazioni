package org.lessons.java.spring_la_mia_pizzeria_crud.Controller;

import java.util.List;

import javax.naming.Binding;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    @GetMapping
    public String index(@RequestParam(name = "ricerca", required = false) String ricerca, Model model) {
        List<Pizza> pizzas;
        if (ricerca != null && !ricerca.isEmpty()) {
            pizzas = repository.findByNomeContainingIgnoreCase(ricerca);
        } else {
            pizzas = repository.findAll();
        }
        model.addAttribute("ricerca", ricerca);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    @GetMapping("/searchByTitle")
    public String searchByTitle(@RequestParam("nome") String nome, Model model) {
        List<Pizza> pizzas = repository.findByNomeContaining(nome);
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "pizzas/create";
        }
        repository.save(formPizza);
        return "redirect:/pizzas";
    }
}
