package no.oslomet.cs.algdat.Oblig3;


import java.util.*;

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
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null){
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else {
                break;
            }
        }

        if(p == null){
            return false;
        }

        if(antall == 1) rot = null; // første tilfelle (bare rot)

       else if(p.høyre == null && p.venstre == null){ // andre tilfelle ingen barn
            if(p.forelder.høyre == p){
                p.forelder.høyre = null;
            } else {
                p.forelder.venstre = null;
            }
        }

       else if(p.venstre != null && p.høyre == null){ // tredje tilfelle
            p.venstre.forelder = p.forelder;
            if(p.forelder == null){
                rot = p.venstre;
            }
            else if(p.forelder.venstre == p){
                p.forelder.venstre = p.venstre;
            } else{
                p.forelder.høyre = p.venstre;
            }


       } else if(p.venstre == null && p.høyre != null) {
            p.høyre.forelder = p.forelder;
           if(p.forelder == null){
               rot = p.høyre;
           }
            else if(p.forelder.høyre == p){
                p.forelder.høyre = p.høyre;
            } else{
                p.forelder.venstre = p.høyre;
            }


        } else {

           Node<T> n = førstePostorden(p.høyre);
           p.verdi = n.verdi;

           if(n.forelder.venstre == n){
               n.forelder.venstre = null;
           } else{
               n.forelder.høyre = null;
           }
       }


        antall--;   // det er nå én node mindre i treet
        endringer++;
        return true;
    }

    public int fjernAlle(T verdi) {

        int antallFjernet=0;

        Node<T> p = rot;
        while (p != null) { // fortsetter når den er i treet
            int cmp = comp.compare(verdi, p.verdi); // sammenligner verdier
            if (cmp < 0) p = p.venstre; // hvis verdien er mindre så går den til venstre
            else if (cmp > 0) p = p.høyre; // hvis verdien er større så går den til høyre
            else { // hvis verdiene er like så økes antall forekomst
                antallFjernet++;
                p = p.høyre; // for å se om det er flere forekomster etter denne noden så blir den første forekomsten p
                fjern(verdi);
            }
        }
        return antallFjernet;

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
        if(antall == 0)return;
        Node<T> p = førstePostorden(rot);
        while (p != null) {
            fjern(p.verdi);
            p = nestePostorden(p);
        }
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


        if(p == null || p.forelder == null){
            return null;
        }
        else if(p.forelder.høyre == null){
            p = p.forelder;
        }
        else if(p.forelder.høyre == p){
            p = p.forelder;
        }

        else {
            p = p.forelder.høyre;
            p = førstePostorden(p);
        }

        return p;

    }

    // fikk hjelp ved å se på Programkode 5.1.15 b)
    public void postorden(Oppgave<? super T> oppgave) {
        if (rot == null) return;

        Node<T> p = førstePostorden(rot);
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

        postordenRecursive(p.venstre, oppgave); // vil skrive ut venstre barn til det er
        postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi); //skriver først rotnoden så venstre barn så høyre barn rekursivt
    }

    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList(); // oppretter arraylist

        Node<T> p = rot; // gjorde rotnoden til variabel

        ArrayDeque<Node<T>> kø = new ArrayDeque<>(); // lagde en kø
        kø.addFirst(p); //adda inn p i køen
        liste.add(p.verdi); // adder inn rotnoden sin verdi i listen

        while(!kø.isEmpty()) { // så lenge køen ikke er tom så vil den gå
            Node<T> cur = kø.removeFirst(); // tar ut fra køen

            if (cur.venstre != null) { // hvis det er et venstre barn så addes den i køen
                kø.addLast(cur.venstre);
            }
            if (cur.høyre != null) { // hvis det er et høyre barn så addes den i køen
                kø.addLast(cur.høyre);
            }
            if(!kø.isEmpty()){ // hvis det er verdier inni køen så addes de i listen
                liste.add(kø.getFirst().verdi);
            }
        }
        return liste;
    }

    static <K>SBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {

        SBinTre<K> noder = new SBinTre<>(c); // lager nytt tre
        for (int i =0; i < data.size();i++) { // går gjennom or løkken
            noder.leggInn(data.get(i)); // legger inn verdiene til treet
        }
        return noder; // returnerer treet
    }

} // ObligSBinTre
