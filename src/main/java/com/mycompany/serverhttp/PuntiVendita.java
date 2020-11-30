
package com.mycompany.serverhttp;

import java.util.ArrayList;

public class PuntiVendita {
    int size;
    ArrayList<PuntiVendita>listaRisultati;
    int idPuntoVendita;
    String denominazione;
    String indirizzo;
    int cap;
    String comune;
    String codProvincia;
    String urlSito;
    String telefonoPrincipale;
    String telefonoSecondario;
    String email;
    String latitudine;
    String longitudine;
    String flagFisicoOnline;
    int idEsercente;
    String ragioneSociale;

    public PuntiVendita(int size, ArrayList<PuntiVendita> listaRisultati, int idPuntoVendita, String denominazione, String indirizzo, int cap, String comune, String codProvincia, String urlSito, String telefonoPrincipale, String telefonoSecondario, String email, String latitudine, String longitudine, String flagFisicoOnline, int idEsercente, String ragioneSociale) {
        this.size = size;
        this.listaRisultati = listaRisultati;
        this.idPuntoVendita = idPuntoVendita;
        this.denominazione = denominazione;
        this.indirizzo = indirizzo;
        this.cap = cap;
        this.comune = comune;
        this.codProvincia = codProvincia;
        this.urlSito = urlSito;
        this.telefonoPrincipale = telefonoPrincipale;
        this.telefonoSecondario = telefonoSecondario;
        this.email = email;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.flagFisicoOnline = flagFisicoOnline;
        this.idEsercente = idEsercente;
        this.ragioneSociale = ragioneSociale;
    }

    public PuntiVendita() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ArrayList<PuntiVendita> getListaRisultati() {
        return listaRisultati;
    }

    public void setListaRisultati(ArrayList<PuntiVendita> listaRisultati) {
        this.listaRisultati = listaRisultati;
    }

    public int getIdPuntoVendita() {
        return idPuntoVendita;
    }

    public void setIdPuntoVendita(int idPuntoVendita) {
        this.idPuntoVendita = idPuntoVendita;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    public String getUrlSito() {
        return urlSito;
    }

    public void setUrlSito(String urlSito) {
        this.urlSito = urlSito;
    }

    public String getTelefonoPrincipale() {
        return telefonoPrincipale;
    }

    public void setTelefonoPrincipale(String telefonoPrincipale) {
        this.telefonoPrincipale = telefonoPrincipale;
    }

    public String getTelefonoSecondario() {
        return telefonoSecondario;
    }

    public void setTelefonoSecondario(String telefonoSecondario) {
        this.telefonoSecondario = telefonoSecondario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }

    public String getFlagFisicoOnline() {
        return flagFisicoOnline;
    }

    public void setFlagFisicoOnline(String flagFisicoOnline) {
        this.flagFisicoOnline = flagFisicoOnline;
    }

    public int getIdEsercente() {
        return idEsercente;
    }

    public void setIdEsercente(int idEsercente) {
        this.idEsercente = idEsercente;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    @Override
    public String toString() {
        return "PuntoVendita{" + "size=" + size + ", listaRisultati=" + listaRisultati + ", idPuntoVendita=" + idPuntoVendita + ", denominazione=" + denominazione + ", indirizzo=" + indirizzo + ", cap=" + cap + ", comune=" + comune + ", codProvincia=" + codProvincia + ", urlSito=" + urlSito + ", telefonoPrincipale=" + telefonoPrincipale + ", telefonoSecondario=" + telefonoSecondario + ", email=" + email + ", latitudine=" + latitudine + ", longitudine=" + longitudine + ", flagFisicoOnline=" + flagFisicoOnline + ", idEsercente=" + idEsercente + ", ragioneSociale=" + ragioneSociale + '}';
    }

    
    
    

    
    
    
    
    
    
}
