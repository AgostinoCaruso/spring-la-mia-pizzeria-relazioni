package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pizzas")
public class pizza {

    public pizza(Integer id, String nome, String immagine, float prezzo) {
        this.id = id;
        this.nome = nome;
        this.immagine = immagine;
        this.prezzo = prezzo;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @NotBlank(message = "Il nome non può essere vuoto")
    private String nome;
    @NotBlank
    private String immagine;
    @NotNull
    private float prezzo;
    
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
    
    public String getImmagine() {
        return this.immagine;
    }
    
    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
    
    public float getPrezzo() {
        return this.prezzo;
    }
    
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
}

