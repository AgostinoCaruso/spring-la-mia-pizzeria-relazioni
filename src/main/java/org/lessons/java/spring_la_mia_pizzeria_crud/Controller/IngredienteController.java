package org.lessons.java.spring_la_mia_pizzeria_crud.Controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingrediente;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredienteRepository;
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

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    private final PizzaRepository pizzaRepository;
    
    @Autowired
    private IngredienteRepository ingredienteRepository;

    IngredienteController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "ingredienti/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model){

        model.addAttribute("ingrediente", ingredienteRepository.findById(id).get());
        return "ingredienti/show";
    }

    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("edit", false);
        return "ingredienti/create-or-edit";
    }
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "ingredienti/create-or-edit";
        }

        ingredienteRepository.save(formIngrediente);
        return "redirect:/ingredienti";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        model.addAttribute("ingrediente", ingredienteRepository.findById(id).get());
        model.addAttribute("edit", true);

        return "ingredienti/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            return "ingredienti/create-or-edit";
        }

        ingredienteRepository.save(formIngrediente);

        return "redirect:/ingredienti";
    }
    
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){

        Ingrediente ingredienteDaCancellare = ingredienteRepository.findById(id).get();

        for(Pizza pizzaCollegata : ingredienteDaCancellare.getPizze()){
            pizzaCollegata.getIngredienti().remove(ingredienteDaCancellare);
        }

        ingredienteRepository.delete(ingredienteDaCancellare);
        return "redirect:/ingredienti";
    }
}
