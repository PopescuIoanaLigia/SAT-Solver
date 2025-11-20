import java.util.ArrayList;
import java.util.List;

public class Clauza {
    private int nrLiterali;
    private State stare;
    private List<Literal> literalList = new ArrayList<>();

    public Clauza(int nrLiterali, List<Literal> literalList) {
        this.nrLiterali = nrLiterali;
        this.stare = State.UNDECIDED;
        this.literalList = literalList;
    }
    public Clauza(int nrLiterali, State stare, List<Literal> literalList) {
        this.nrLiterali = nrLiterali;
        this.stare = stare;
        this.literalList = literalList;
    }

    //getteri si setteri
    public State getStare() {
        return stare;
    }
    public int getNrLiterali() {
        return nrLiterali;
    }
    public void setNrLiterali(int nrLiterali) {
        this.nrLiterali = nrLiterali;
    }
    public List<Literal> getLiteralList() {
        return literalList;
    }
    public void setLiteralList(List<Literal> literalList) {
        this.literalList = literalList;
    }

    Literal Find(Literal literal){ //functie pentru cautarea unui literal in clauza
        if(literalList.isEmpty()) return null;
        for(Literal l : literalList){
            if(l.getNrVariabila() == literal.getNrVariabila()){
                //System.out.println(" Am gasit literalul " + literal.getNrVariabila());
                return l;
            }
        }
        //System.out.println("Nu am gasit " + literal.getNrVariabila());
        return null;
    }

    boolean VerificaClauzaNefalsificata( Clauza atribuiri ){
        if( atribuiri == null ) return true; //nu avem nicio atribuire de verificat
        if(atribuiri.getNrLiterali() < nrLiterali){
            this.stare = State.UNDECIDED;
            //System.out.println("atribuiri mai putine decat nr variabile din clauza");
            return true; // atribuiri mai putine decat nr variabile din clauza
                         // deci nu are cum sa fie falsificata clauza
        }
        for(Literal literalulDinClauza : literalList){
            Literal literalulDinAtribuire =
                    atribuiri.Find( literalulDinClauza);
            if( literalulDinAtribuire != null ){
                if( literalulDinAtribuire.getValoare() ==
                        literalulDinClauza.getValoare() ){
                    //System.out.println("Doi literali egali: " + literalulDinClauza + " " + literalulDinAtribuire);
                    this.stare = State.TRUE;
                    return true; // daca am gasit doi literali egali, clauza e adevarata
                }
            } else {
                // am gasit un literalDinClauza care nu se afla in atribuiri
                // deci clauza este nedecisa inca, dar nu falsa
                //System.out.println("Doi literali diferiti: " + literalulDinClauza + " " + literalulDinAtribuire);
                this.stare = State.UNDECIDED;
                return true;
            }
        }
        //System.out.println("Niciun literal nu corespunde" );
        this.stare = State.FALSE;
        return false;
    }

    Literal VerificaClauzaUnitara(Clauza atribuiri){
        if( atribuiri == null && this.getNrLiterali() == 1 ) return getLiteralList().get(0);
        int nrLiteraliNeatribuiti = 0;
        Literal literalImplicat = null;
        for(Literal literalulDinClauza : literalList){
            Literal literalulDinAtribuire =
                    atribuiri.Find( literalulDinClauza);
            if( literalulDinAtribuire != null ){
                if( literalulDinAtribuire.getValoare() ==
                        literalulDinClauza.getValoare() ){
                    //System.out.println("Nu e unitara: Doi literali egali: " + literalulDinClauza + " " + literalulDinAtribuire);
                    return null; // daca am gasit doi literali egali, clauza e adevarata
                }
            } else {
                // am gasit un literalDinClauza care nu se afla in atribuiri
                // avem voie decat un singur literal neatribuit
                literalImplicat = literalulDinClauza;
                nrLiteraliNeatribuiti++;
                //System.out.println("Literalul neatribuit numarul " + nrLiteraliNeatribuiti + " este: "+ literalulDinClauza);
                if(nrLiteraliNeatribuiti == 2){
                    return null;
                }
            }
        }
        return literalImplicat;
    }

    @Override
    public String toString() {
        String S = "";
        S =" ( ";
        for(Literal l : literalList){
            S +=l.toString();
        }
        S = S.substring(0, S.length() - 2);
        S +=") ^";
        return S;
    }

    public String AfisareAtribuiri()
    {
        String S = "v ";
        for(Literal l : literalList){
            if( l.getValoare() == true)
                S = S + l.getNrVariabila() + " ";
            else {
                S = S + (-l.getNrVariabila()) + " ";
            }
        }
        S += "0";
        return S;
    }
}
