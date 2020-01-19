/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.michaelkoenig.labor06;

import java.util.Objects;

/**
 *
 * @author 20160451
 */
public class Schueler {

    private String vorname;
    private String nachname;
    private char geschlecht;
    private int katalognummer;
    private String klasse;

    public Schueler(String vorname, String nachname, char geschlecht, int katalognummer, String klasse) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.geschlecht = geschlecht;
        this.katalognummer = katalognummer;
        this.klasse = klasse;
    }

    public static Schueler fromCSVString(String s) {
        String[] split = s.split(",");
        if (split.length != 5) {
            throw new IllegalArgumentException("Invalid string");
        }
        return new Schueler(split[1], split[0], split[2].charAt(0), Integer.parseInt(split[3]), split[4]);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    public int getKatalognummer() {
        return katalognummer;
    }

    public void setKatalognummer(int katalognummer) {
        this.katalognummer = katalognummer;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.katalognummer;
        hash = 53 * hash + Objects.hashCode(this.klasse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Schueler other = (Schueler) obj;
        if (this.katalognummer != other.katalognummer) {
            return false;
        }
        if (!Objects.equals(this.klasse, other.klasse)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Schueler{" + "vorname=" + vorname + ", nachname=" + nachname + ", geschlecht=" + geschlecht + ", katalognummer=" + katalognummer + ", klasse=" + klasse + '}';
    }

}
