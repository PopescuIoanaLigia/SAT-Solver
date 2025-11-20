import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Solver {
    static SAT problemaSat = null;

    // functia DPLL fara unit propagation
    static private Rezultat DPLL(Clauza atribuiri){ //atribuiri este init cu o lista goala
                                             //atribuiri nu poate sa fie null
        //if ϕ contine o clauză falsificată de τ then
        if(!problemaSat.NuContineClauzaFalsificata(atribuiri)) {
            //System.out.println(" Clauza: " + atribuiri + " contine clauze false");
            return new Rezultat(Satisfiabil.UNSAT, atribuiri);

        }
        //if τ contine câte un literal pentru fiecare variabilă din ϕ then
        if(problemaSat.getNrVariabile() == atribuiri.getNrLiterali()){
            //System.out.println(" Clauza: " + atribuiri + " contine un literal pt fiecare var");
            return new Rezultat(Satisfiabil.SAT, atribuiri);
        }

        //Alegem un literal care nu se găseste in τ
        int nrVariabila = -1;
        for(int i=1; i<=problemaSat.getNrVariabile(); i++){
            Literal literal = null;
            //caut o variabila care nu e in atribuiri
            for( Literal l: atribuiri.getLiteralList()) {
                if( l.getNrVariabila() == i ){
                    literal = l;
                    break;
                }
            }

            if(literal == null) {
                //System.out.println(" Literal nou: " + i + " nu se gaseste in atribuire");
                //System.out.println(atribuiri);
                nrVariabila = i;
                break;
            }
        }

        List<Literal> auxLiteralList1 = new ArrayList<>(atribuiri.getLiteralList());
        List<Literal> auxLiteralList2 = new ArrayList<>(atribuiri.getLiteralList());

        //adaug literalul in noul atribuiri (forma true)
        auxLiteralList1.add(new Literal(nrVariabila, true));
        Clauza auxAtribuiri = new Clauza(atribuiri.getNrLiterali() + 1,
                State.UNDECIDED,auxLiteralList1);
        Rezultat rezultat = DPLL(auxAtribuiri);
        if(rezultat.getSatisfiabil() == Satisfiabil.SAT) {
            return rezultat;
        }

        //adaug literalul in noul atribuiri (forma false)
        auxLiteralList2.add(new Literal(nrVariabila, false));
        auxAtribuiri = new Clauza(atribuiri.getNrLiterali() + 1,
                State.UNDECIDED,auxLiteralList2);
        rezultat = DPLL(auxAtribuiri);
        if(rezultat.getSatisfiabil() == Satisfiabil.SAT) {
            return rezultat;
        }
        return new Rezultat(Satisfiabil.UNSAT, atribuiri);
    }

    // functia DPLL cu unit propagation
    static private Rezultat DPLL_UnitPropag(Clauza atribuiri){ //atribuiri este init cu o lista goala
        //atribuiri nu poate sa fie null
        //if ϕ contine o clauză falsificată de τ then
        if(!problemaSat.NuContineClauzaFalsificata(atribuiri)) {
            //System.out.println(" Clauza: " + atribuiri + " contine clauze false");
            return new Rezultat(Satisfiabil.UNSAT, atribuiri);

        }

        //while ϕ contine o clauză C unitară în τ do
        RezClasaUnitara rezClasaUnitara = problemaSat.ReturneazaClauzaUnitara(atribuiri);
        while( rezClasaUnitara != null){
            atribuiri.getLiteralList().add(rezClasaUnitara.getLiteral());
            atribuiri.setNrLiterali(atribuiri.getNrLiterali() + 1);
            if(!problemaSat.NuContineClauzaFalsificata(atribuiri)) {
                //System.out.println(" Clauza: " + atribuiri + " contine clauze false");
                return new Rezultat(Satisfiabil.UNSAT, atribuiri);

            }
            rezClasaUnitara = problemaSat.ReturneazaClauzaUnitara(atribuiri);
        }

        //if τ contine câte un literal pentru fiecare variabilă din ϕ then
        if(problemaSat.getNrVariabile() == atribuiri.getNrLiterali()){
            //System.out.println(" Clauza: " + atribuiri + " contine un literal pt fiecare var");
            return new Rezultat(Satisfiabil.SAT, atribuiri);
        }

        //Alegem un literal care nu se găseste in τ
        int nrVariabila = -1;
        for(int i=1; i<=problemaSat.getNrVariabile(); i++){
            Literal literal = null;
            //caut o variabila care nu e in atribuiri
            for( Literal l: atribuiri.getLiteralList()) {
                if( l.getNrVariabila() == i ){
                    literal = l;
                    break;
                }
            }

            if(literal == null) {
                //System.out.println(" Literal nou: " + i + " nu se gaseste in atribuire");
                //System.out.println(atribuiri);
                nrVariabila = i;
                break;
            }
        }

        List<Literal> auxLiteralList1 = new ArrayList<>(atribuiri.getLiteralList());
        List<Literal> auxLiteralList2 = new ArrayList<>(atribuiri.getLiteralList());

        //adaug literalul in noul atribuiri (forma true)
        auxLiteralList1.add(new Literal(nrVariabila, true));
        Clauza auxAtribuiri = new Clauza(atribuiri.getNrLiterali() + 1,
                State.UNDECIDED,auxLiteralList1);
        Rezultat rezultat = DPLL_UnitPropag(auxAtribuiri);
        if(rezultat.getSatisfiabil() == Satisfiabil.SAT) {
            return rezultat;
        }

        //adaug literalul in noul atribuiri (forma false)
        auxLiteralList2.add(new Literal(nrVariabila, false));
        auxAtribuiri = new Clauza(atribuiri.getNrLiterali() + 1,
                State.UNDECIDED,auxLiteralList2);
        rezultat = DPLL_UnitPropag(auxAtribuiri);
        if(rezultat.getSatisfiabil() == Satisfiabil.SAT) {
            return rezultat;
        }
        return new Rezultat(Satisfiabil.UNSAT, atribuiri);
    }

    public static void main(String[] args) {
        // Verific nr fisiere specificate in apelare = 2
        if (args.length != 2) {
            System.err.println("Usage: java Solver <input_file> <output_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try { //citirea din fisier si initializarea obiectului SAT
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String firstLine = reader.readLine();
            while (firstLine.charAt(0) == 'c') {
                firstLine = reader.readLine();
            }
            String[] tokens = firstLine.split("\\s+");
            //System.out.println("First line: " + firstLine);
            if(!tokens[0].equals("p") || !tokens[1].equals("cnf"))
            {
                throw new ExceptieStartLineKey("p cnf");
            }

            // salvam numarul de variabile si clauze
            //System.out.println("Nr_var: " +tokens[2] + " Nr_clauze" + tokens[3]);
            int nrVariabileSAT = Integer.parseInt(tokens[2]);
            int nrclauze = Integer.parseInt(tokens[3]);

            List<Clauza> listaClauze = new ArrayList<Clauza>();

            for(int i=0; i < nrclauze; i++){
                String primaClauza = reader.readLine();
                primaClauza = primaClauza.trim(); //eliminam spatiile de la margini
                //System.out.println("Prima clauza:" + primaClauza);
                String[] variabile = primaClauza.split("\\s+");
                //System.out.println("Variabile:");
                List<Literal> literals = new ArrayList<Literal>();
                int nrVariabileClauza = 0;
                int j = 0;
                while(!variabile[j].equals("0")){
                    //System.out.println(" " + variabile[j]);

                    // salvez literalul drept int
                    int variabila = Integer.parseInt(variabile[j]);
                    boolean valoare;
                    if( variabila < 0){
                        valoare = false;
                    } else {
                        valoare = true;
                    }
                    // salvez numarul valorii fara minus
                    int nrVariabilaLiteral = Math.abs(variabila);

                    Literal literal = new Literal(nrVariabilaLiteral, valoare);
                    literals.add(literal);
                    nrVariabileClauza++;
                    j++;
                }
                Clauza clauza = new Clauza(nrVariabileClauza, literals);
                listaClauze.add(clauza);
            }
            reader.close();
            Solver.problemaSat = new SAT(nrVariabileSAT, nrclauze, listaClauze);
        }
        catch (FileNotFoundException e) {
            System.err.println("Fisierul nu a fost gasit: " + inputFile);
        }
        catch (IOException e) {
            System.err.println("Fisierul nu a io: " + inputFile);
            e.printStackTrace();
        }
        catch (ExceptieStartLineKey e) {
            System.out.println(e.getMessage());
        }

        // apeleaz functia DPLL
        //System.out.println("/////////////Apelez DPLL//////////////");
        Clauza atribuiri = new Clauza(0, new ArrayList<Literal>());
        Rezultat rezultat = Solver.DPLL_UnitPropag(atribuiri);
        String output = rezultat.AfisareRezultat();

        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(output);
            writer.close();
            System.out.println("Textul a fost scris in fisier.");
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fișierului: " + e.getMessage());
        }

        System.out.println(problemaSat);
    }
}
