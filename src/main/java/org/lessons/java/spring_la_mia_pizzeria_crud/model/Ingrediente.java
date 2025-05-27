package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name ="Ingredienti")
public class Ingrediente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Un ingrediente deve avere un nome")
    private String nome;


    private Integer pizzeCollegate;

    public Ingrediente() {

    }

    public Integer getPizzeCollegate() {
        return this.pizzeCollegate;
    }

    public void setPizzeCollegate(Integer pizzeCollegate) {
        this.pizzeCollegate = pizzeCollegate;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    @ManyToMany(mappedBy = "ingredienti")
    private List<Pizza> pizze;


    public List<Pizza> getPizze() {
        return this.pizze;
    }

    public void setPizze(List<Pizza> pizze) {
        this.pizze = pizze;
    }

}

