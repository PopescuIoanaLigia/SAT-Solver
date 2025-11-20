public class Rezultat {
    private Satisfiabil satisfiabil;
    private Clauza clauza;

    // Constructor
    public Rezultat( Satisfiabil satisfiabil, Clauza clauza ) {
        this.satisfiabil = satisfiabil;
        this.clauza = clauza;
    }

    // Getteri
    public Satisfiabil getSatisfiabil() {
        return satisfiabil;
    }
    public Clauza getClauza() {
        return clauza;
    }

    public String AfisareRezultat() {
        String S = "s ";
        if(satisfiabil == Satisfiabil.SAT) {
            S += "SATISFIABLE\n";
            S += clauza.AfisareAtribuiri();
        } else {
            S += "UNSATISFIABLE";
        }
        return S;
    }
}
