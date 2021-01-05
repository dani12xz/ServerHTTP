/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverhttp;

import java.util.ArrayList;

public class Studente {
    
    String nome;
    String cognome;
    int idStudente;
    

    public Studente(String nome, String cognome, int idStudente) {
        this.nome = nome;
        this.cognome = cognome;
        this.idStudente = idStudente;
    }


   
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getIdStudente() {
        return idStudente;
    }

    public void setIdStudente(int idStudente) {
        this.idStudente = idStudente;
    }

   

    

    

    
    
    
    
}
