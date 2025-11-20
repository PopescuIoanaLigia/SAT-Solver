import java.util.Objects;

public class Literal {
    private int nrVariabila;
    private boolean valoare; // negat=0 sau ne-negat=1

    public Literal(int nrVariabila, boolean valoare) {
        this.nrVariabila = nrVariabila;
        this.valoare = valoare;
    }
    public int getNrVariabila() {
        return nrVariabila;
    }
    public boolean getValoare() {
        return valoare;
    }

    @Override
    public String toString() {
        String S = "";
        if(valoare) {
            S = String.valueOf(nrVariabila);
        } else {
            S = String.valueOf(-nrVariabila);
        }
        S += " v ";
        return S;
    }
}
