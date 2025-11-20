class ExceptieStartLineKey extends Exception {
    public ExceptieStartLineKey(String required) {
        super("Linia nu incepe cu cheia necesara: " + required);
    }
}

