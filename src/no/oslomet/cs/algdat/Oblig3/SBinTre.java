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

        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!"); // gir mld om verdi er null

        Node<T> p = rot; // forelder er rotnoden
        Node<T> q = null;
        int cmp = 0; // hjelpe variabel for å sammenligne senere

        while (p != null) {
            q = p;
            cmp = comp.compare(verdi,p.verdi); // sammenligner verdiene
            if(cmp < 0){ // hvis forelder verdi er større enn verdi skal den til venstre ellers går den til høyre
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }

        // forelder er nå null, dvs. ute av treet, q er den siste vi passerte

        Node<T> node = new Node(verdi, null);                   // oppretter en ny node

        if (q == null){
            rot = node;
        }
        else if (cmp < 0){
            q.venstre = node;
            q.venstre.forelder = q; // forelder til q
        }
        else{
            q.høyre = node;                        // høyre barn til q
            q.høyre.forelder = q; // forelder til q
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
    // fikk hjelp ved å se Programkode 5.1.7 g)
    private static <T> Node<T> førstePostorden(Node<T> p) {

        Objects.requireNonNull(p, "verdien er null");

        while (true) {
            if (p.venstre != null){
                p = p.venstre;
            }
            else if (p.høyre != null){
                p = p.høyre;
            }
            else return p;
        }
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        Objects.requireNonNull(p, "verdien er null");
        // p er den første noden i postorden

        if (p.høyre != null){  // p har høyre barn
            return førstePostorden(p.høyre);

        } else{  // må gå oppover i treet

            while (p.forelder != null && p.forelder.høyre == p){
                p = p.forelder;
            }

        }

        return p.forelder;

    }

    // fikk hjelp ved å se på Programkode 5.1.15 b)
    public void postorden(Oppgave<? super T> oppgave) {
        if (rot == null) return;    // hvis treet er tomt

        Node<T> p = førstePostorden(rot);  // den aller første i postorden

        while (p != null) {
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return;    // hvis noden er null

        Node<T> første = førstePostorden(p);  // den aller første i postorden

        if(p != null) {
            oppgave.utførOppgave(første.verdi);
            første = nestePostorden(p);
            postordenRecursive(første, oppgave);
        }
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
