package org.lessons.java.spring_la_mia_pizzeria_crud.Controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.ScontiPizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
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
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    private final IngredienteRepository ingredienteRepository;

    @Autowired
    private PizzaRepository repository;

    @Autowired
    private ScontiPizzaRepository scontiPizzaRepository;

    PizzaController(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

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
        model.addAttribute("edit", false);
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "pizzas/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "pizzas/create-or-edit";
        }
        repository.save(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        model.addAttribute("edit", true);
        model.addAttribute("ingredienti", ingredienteRepository.findAll());

        return "pizzas/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            model.addAttribute("ingredienti", ingredienteRepository.findAll());

            return "pizzas/create-or-edit";
        }
        repository.save(formPizza);
        return "redirect:/pizzas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Pizza pizza = repository.findById(id).get();
        for (ScontiPizza scontiDaEliminare : pizza.getScontiPizzas()) {
            scontiPizzaRepository.delete(scontiDaEliminare);
        }

        repository.delete(pizza);
        return "redirect:/pizzas";
    }

    // SCONTI PIZZA

    @GetMapping("/{id}/scontiPizza")
    public String scontiPizza(@PathVariable Integer id, Model model) {
        ScontiPizza scontiPizza = new ScontiPizza();
        scontiPizza.setPizza(repository.findById(id).get());
        model.addAttribute("scontiPizza", scontiPizza);

        return "scontiPizza/create-or-edit";
    }

}
