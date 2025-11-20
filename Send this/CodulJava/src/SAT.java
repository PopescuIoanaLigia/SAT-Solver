import java.util.ArrayList;
import java.util.List;

public class SAT {
    private int nrVariabile;
    private int nrClauze;
    private List<Clauza> listaClauze = new ArrayList<>();

    public SAT(int nrvariabile, int nrclauze, List<Clauza> listaClauze) {
        this.nrVariabile = nrvariabile;
        this.nrClauze = nrclauze;
        this.listaClauze = listaClauze;
    }

    public int getNrVariabile() {
        return nrVariabile;
    }
    public int getNrClauze() {
        return nrClauze;
    }

    public void setListaClauze(List<Clauza> listaClauze) {
        this.listaClauze = listaClauze;
    }

    boolean NuContineClauzaFalsificata (Clauza atribuiri) {
        for (Clauza clauza : listaClauze) {
            //System.out.println();
            //System.out.println("Falsificare Verific pentru clauza: " + clauza + " si Atribuirile: " + atribuiri);
            if( !clauza.VerificaClauzaNefalsificata(atribuiri)) {
                return false;
            }
        }
        return true;
    }

    RezClasaUnitara ReturneazaClauzaUnitara (Clauza atribuiri) {
        for (Clauza clauza : listaClauze) {
            //System.out.println();
            //System.out.println("Unitara Verific pentru clauza: " + clauza + " si Atribuirile: " + atribuiri);
            Literal literalImplicat =clauza.VerificaClauzaUnitara(atribuiri);
            if( literalImplicat != null) {
                return new RezClasaUnitara(clauza, literalImplicat);
            }
        }
        return null;
    }

    //pentru debug, se poate apela System.out.println(sat) si se afiseaza
    //problema SAT in format normal
    /*@Override
    public String toString() {
        String S = "";
        S = "SAT{nrVariabile='" + nrVariabile + "', nrClauze=" + nrClauze + "}" + "\n";
        for (Clauza c : listaClauze) {
            S += c.toString();
        }
        S = S.substring(0, S.length() - 1);
        return S;
    }*/
}
