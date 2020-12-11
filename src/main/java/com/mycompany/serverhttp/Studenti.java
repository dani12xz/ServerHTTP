
package com.mycompany.serverhttp;

import java.util.ArrayList;

public class Studenti {
    ArrayList<Studente>listaStudenti=new ArrayList<>();

    public Studenti(ArrayList<Studente> listaStudenti) {
        this.listaStudenti = listaStudenti;
    }

    public ArrayList<Studente> getListaStudenti() {
        return listaStudenti;
    }

    public void setListaStudenti(ArrayList<Studente> listaStudenti) {
        this.listaStudenti = listaStudenti;
    }

    public Studenti() {
    }
    
    
    void add(Studente studente) {
        listaStudenti.add(studente);
    }
}
