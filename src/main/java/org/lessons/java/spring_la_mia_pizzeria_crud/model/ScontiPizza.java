package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "scontiPizzas")
public class ScontiPizza {

    public ScontiPizza() {
    }

    public ScontiPizza(Integer id, String titolo, LocalDate scontoInizioData, LocalDate scontoFineData) {
        this.id = id;
        this.titolo = titolo;
        this.scontoInizioData = scontoInizioData;
        this.scontoFineData = scontoFineData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Va insirito un titolo per lo sconto!")
    private String titolo;

    @NotNull(message = "Deve avere una data d'inizio")
    private LocalDate scontoInizioData;

    @NotNull(message = "Deve avere una data di fine")
    private LocalDate scontoFineData;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public LocalDate getScontoInizioData() {
        return this.scontoInizioData;
    }

    public void setScontoInizioData(LocalDate scontoInizioData) {
        this.scontoInizioData = scontoInizioData;
    }

    public LocalDate getScontoFineData() {
        return this.scontoFineData;
    }

    public void setScontoFineData(LocalDate scontoFineData) {
        this.scontoFineData = scontoFineData;
    }

    @ManyToOne
    @JoinColumn(name = "pizza_id", nullable = false)
    private Pizza pizza;

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

}
