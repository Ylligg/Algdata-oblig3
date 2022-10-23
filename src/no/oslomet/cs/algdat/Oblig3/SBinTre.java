package no.oslomet.cs.algdat.Oblig3;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class SBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public SBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> forelder = rot; // forelder er rotnoden
        Node<T> q = null;
        int cmp = 0; // hjelpe variabel for å sammenligne senere

        while (forelder != null) {
            q = forelder;
            cmp = comp.compare(verdi,forelder.verdi); // sammenligner verdiene
            if(cmp < 0){ // hvis forelder verdi er større enn verdi skal den til venstre ellers går den til høyre
                forelder = forelder.venstre;
            } else {
                forelder = forelder.høyre;
            }
        }

        // forelder er nå null, dvs. ute av treet, q er den siste vi passerte

        Node<T> barn = new Node(verdi, null);                   // oppretter en ny node

        if (q == null){
            rot = barn;
        }
        else if (cmp < 0){
            q.venstre = barn;
        }
        else{
            q.høyre = barn;                        // høyre barn til q
        }

        antall++;                                // én verdi mer i treet
        return true;                             // vellykket innlegging
    }


    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        int forekomst=0;

        Node<T> p = rot;
            while (p != null) { // fortsetter når den er i treet
                int cmp = comp.compare(verdi, p.verdi); // sammenligner verdier
                if (cmp < 0) p = p.venstre; // hvis verdien er mindre så går den til venstre
                else if (cmp > 0) p = p.høyre; // hvis verdien er større så går den til høyre
                else { // hvis verdiene er like så økes antall forekomst
                    forekomst++;
                    p = p.høyre; // for å se om det er flere forekomster etter denne noden så blir den første forekomsten p
                }
            }
        return forekomst;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {

            Objects.requireNonNull(p, "verdien er null");

            while(p.venstre != null){ // går ned helt til siste barnet
                p = p.venstre;
                førstePostorden(p); // kaller på seg selv til siste node
            }
                return p; // dette blir 1ern

    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        Objects.requireNonNull(p, "verdien er null");

        // det er siste noden som er p

        while(p.forelder != null) {
            p = p.forelder;
            nestePostorden(p);
        }
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
