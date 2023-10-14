package main;

import java.util.Random;

public class Student{
    private int indeks;
    private long vremeDolaska;  //random vreme dolaska, 0<x<=1s
    private long trajanjeOdbrane;   //random trajanje odbrane, 0.5s<x<=1s
    private int ocena;      //random vrednost 5-10
    private int odabir;

    public Student(int indeks){
        this.indeks = indeks;
        this.vremeDolaska = new Random().nextInt(1000);
        this.trajanjeOdbrane = new Random().nextInt(500) + 500;
        this.odabir = new Random().nextInt(256) % 2;
    }
    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public long getVremeDolaska() {
        return vremeDolaska;
    }

    public void setVremeDolaska(long vremeDolaska) {
        this.vremeDolaska = vremeDolaska;
    }

    public long getTrajanjeOdbrane() {
        return trajanjeOdbrane;
    }

    public void setTrajanjeOdbrane(long trajanjeOdbrane) {
        this.trajanjeOdbrane = trajanjeOdbrane;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public int getOdabir() {
        return odabir;
    }

    public void setOdabir(int odabir) {
        this.odabir = odabir;
    }

    public boolean daLiStudentKasni(long pocetakSvihOdbrana, long trenutnoVreme){
        long vremeOdbraneOdPocetka = pocetakSvihOdbrana + vremeDolaska;
        if (vremeOdbraneOdPocetka - trenutnoVreme > 0) {
           // System.out.println("Ceka se student "+ indeks + ", " + (vremeOdbraneOdPocetka - trenutnoVreme) +  "ms");
            return true;
        }
        return false;
    }
    public long sacekajStudenta(long pocetakSvihOdbrana, long trenutnoVreme){
        long vremeCekanja = (vremeDolaska + pocetakSvihOdbrana) - trenutnoVreme;
        //System.out.println(vremeCekanja);
        if (vremeCekanja > 0)
            return vremeCekanja;
        return 0;
    }
}
