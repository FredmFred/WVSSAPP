package wvssappclub.de.wvssapp;

/**
 * Created by Fred on 15.02.2017.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Vertretung {
    private GregorianCalendar tag;
    private String klasse;
    private String stunde;
    private String lehrer;
    private String vertretungsLehrer;
    private String art;
    private String fach;
    private String raum;
    private String text;

    public Vertretung() {
    }

    public String getVertretungsLehrer() {
        return vertretungsLehrer;
    }

    public void setVertretungsLehrer(String vertretungsLehrer) {
        this.vertretungsLehrer = vertretungsLehrer;
    }

    public GregorianCalendar getTag() {
        return tag;
    }

    public void setTag(GregorianCalendar tag) {
        this.tag = tag;
    }

    public String getKlasse() {
        return klasse;
    }

    public void setKlasse(String klasse) {
        this.klasse = klasse;
    }

    public String getStunde() {
        return stunde;
    }

    public void setStunde(String stunde) {
        this.stunde = stunde;
    }

    public String getLehrer() {
        return lehrer;
    }

    public void setLehrer(String lehrer) {
        this.lehrer = lehrer;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getFach() {
        return fach;
    }

    public void setFach(String fach) {
        this.fach = fach;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private void replaceNull(){
        this.text = (this.text == null) ? "-" : this.text;
        this.raum = (this.raum == null) ? "-" : this.raum;
        this.art = (this.art == null) ? "-" : this.art;
        this.fach = (this.fach == null) ? "-" : this.fach;
        this.lehrer = (this.lehrer == null) ? "-" : this.lehrer;
        this.vertretungsLehrer = (this.vertretungsLehrer == null) ? "" : this.vertretungsLehrer;
    }

    @Override
    public String toString() {
        this.replaceNull();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
        String ret = "Tag: " + df.format(this.tag.getTime()) + "\n";
        ret += "Klasse: " + this.klasse + "\n";
        ret += "Sunde: " + this.stunde + "\n";
        ret += "Lehrer: " + this.lehrer + "\n";
        ret += "Vertretungslehrer: " + this.vertretungsLehrer + "\n";
        ret += "Art: " + this.art + "\n";
        ret += "Fach: " + this.fach + "\n";
        ret += "Raum: " + this.raum + "\n";
        return ret + "Text: " + this.text + "\n";
    }
}
