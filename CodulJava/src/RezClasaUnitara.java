public class RezClasaUnitara {
    private Clauza clauza;
    private Literal literal;
    public RezClasaUnitara(Clauza clauza, Literal literal) {
        this.clauza = clauza;
        this.literal = literal;
    }
    public Clauza getClauza() {
        return clauza;
    }
    public Literal getLiteral() {
        return literal;
    }
}
