Codul implementat in Java constituie un SAT-solver care utilizeaza DPLL si unit-propagation. Au fost realizate doua functii separate principale( DPLL si DPLL_UnitPropag).

Pentru debugging, exista afisari de la tastatura comentate pe percursul codului. ATENTIE! - checkerul va esua cu TLL daca sunt decomentate.

In continuare se for explica clasele utilizate, impreuna cu functiile si functionalitatile lor:

///////////CustomExceptions.java//////////////

	Contine ExceptieStartLineKey o exceptie cu modificator de acces efault care extinde Exceptions din documentul CustomExceptions.java
	Afiseaza mesajul "Linia nu incepe cu cheia necesara: " + required. Este utilizzata pentru input gresit al fisierului din care se citeste.


///////////Literal.Java//////////////

	Contine clasa publica Literal care reprezinta literalii din problema sat
Variabile de instanta:
	private int nrVariabila -- numarul care reprezinta variabila literalului (1 , 2, 10 etc.)
	private boolean valoare -- valoarea pe care o ia literalul (true pentru variabila ne-negata, false pentru variabila negata)
	
Metode:
	public getNrVariabila() -- getter pentru nrVariabila
	public getValoare() -- getter pentru valoare
	@Override
	public String toString() -- metoda debugging pentru afisarea problemei SAT (apelata de calsa Clauza)


///////////Enums.Java//////////////

	Contine definitia a doua enum-uri pentru Clasele Clauza, respectiv SAT:
	enum State -- FALSE, TRUE, UNDECIDED ( stabileste daca clauza este falsificata, nedecisa, sau adevarata)
	enum Satisfiabil --  SAT, UNSAT ( stabileste daca problema sat este satisfiabila sau nu)


///////////Clauza.Java//////////////

	Reprezinta o clauza din problema SAT.
	Este folosita si pentru reprezentarea atribuirii (in cazul asta, variabila stare este redundanta)

Variabile de instanta:
	
	private int nrLiterali -- numarul de literali din clauza/ atribuire
	private State stare -- starea clauzei (FALSE, TRUE, UNDECIDED)
	private List<Literal> literalList -- lista de literali din clauza

Metode:

	Constructori:
		public Clauza(int nrLiterali, List<Literal> literalList)
		public Clauza(int nrLiterali, State stare, List<Literal> literalList)
	Getteri:
		public State getStare()
		public int getNrLiterali()
		public void setNrLiterali(int nrLiterali)
		public List<Literal> getLiteralList()
		public void setLiteralList(List<Literal> literalList)
	Alte Metode:
	Literal Find(Literal literal) -- functi care primeste un literal si il cauta (dupa 		nrVariabila, ignorand )in lista clauzei/atribuirii pe care sete aplicata 		functia. se intoarce literalul in cazul prezentei sau null in cazul absentei.
		
	boolean VerificaClauzaNefalsificata( Clauza atribuiri ) -- functie care 			primeste atribuirile si verifica daca acestea falsifica sau nu clauza. Intoarce 		true pentru clauza nefalsificata si false pentru clauza falsificata.
		--in cazul in care atribuiri este null, clauza ramane UNDECIDED
		--in cazul in care atribuir are un nr. de literali < nr. de literali din 		clauza, clauza ramane UNDECIDED
		--altfel se parcurge lista de literali din clauza si se verifica existenta lor 		in atribuiri prin metoda Find. Daca literalul este gasit si valoarile 			corespund, inseamna ca clauza este TRUE si functia returneaza true. Daca 		literalul nu este gasit atunci clauza este UNDECIDED si se returneaza true. 		La final functia returneaza false, toti literalii sunt falsificati.
	
	Literal VerificaClauzaUnitara(Clauza atribuiri) -- functie care primeste atribuirile si 		verifica daca clauza este unitara. Intoarce literalul implicat de clauza 		unitara sau null in cazul unei clauze neunitare. 
		--in cazul in care atribuiri este null, clauza este unitara daca nrLiterali 		este 1
		--altfel se parcurge lista de literali din clauza si se verifica existenta lor 		in atribuiri prin metoda Find. Daca literalul este gasit si valoarile 			corespund, inseamna ca clauza este TRUE si functia returneaza null pentru ca nu 		este unitara. Daca literalul este gasit si valoarile nu corespund, creste 		nrLiteraliNeatribuiti. Daca nrLiteraliNeatribuiti ajunge la 2, se returneaza 		null. Functia returneaza literalImplicat.
	
	public String AfisareAtribuiri() -- metoda pentru afisarea in fisierul output al rezultatului in formatul dorit

	Metode Debugging:
		public String toString()


///////////SAT.Java//////////////

	Reprezinta problema SAT
	
Variabile de instanta:

	private int nrVariabile -- numarul de variabile din problema
	private int nrClauze -- numarul de clauze din problema
	private List<Clauza> listaClauze -- lisata de clauze din problema

Metode:

	Constructor:
	public SAT(int nrvariabile, int nrclauze, List<Clauza> listaClauze)

	Getteri si setter:
	public int getNrVariabile()
	public int getNrClauze()
	public void setListaClauze(List<Clauza> listaClauze)

	Alte Metode:
	boolean NuContineClauzaFalsificata (Clauza atribuiri) -- parcurge lista de clauze si 	verifica daca contine clauze falsificate. returneaza true pentru lipsa clauzelor 	falsificate si false altfel.

	RezClasaUnitara ReturneazaClauzaUnitara (Clauza atribuiri) -- parcurge lista de clauze 	si returneaza clauza unitara si literalul implicat prin intermediul clasei 	RezClasaUnitara, sau null in caz contrar existentei lor.

	Debugging:
	public String toString()


///////////RezClasaUnitara.Java//////////////

	O clasa simpla care contine doar: private Literal literal si private Clasa clasa, folosita pentru a le putea intoarce pe ambele in metoda ReturneazaClauzaUnitara


///////////Rezultat.Java//////////////

	O clasa folosita pentru return value din DPLL_UnitPropag si DPLL din clasa Solver.
	
Variabile de instanta:

	private Satisfiabil satisfiabil -- statusul problemei SAT (SAT, UNSAT)
	private Clauza clauza -- atribuirea care reprezinta rezultatul (pentru SAT), sau null 	(pentru UNSAT)

Metode:
	public String AfisareRezultat() -- apeleaza AfisareAtribuiri din Clauza si construieste 	string-ul pentru output-ul din fisierul output.


///////////Solver.Java//////////////


	Clasa in care se afla main-ul. Singura variabila este static SAT problemaSat.
	Aici sunt definite functiile DPLL si DPLL_UnitPropag

Variabile de instanta:
	static SAT problemaSat -- problema sat de rezolvat

Metode:
	static private Rezultat DPLL(Clauza atribuiri) -- este functia care rezolva problema 	fara unit propagation. Nu este apelata deloc, ea a fost folosita pentru a facilita 
	construirea functiei DPLL_UnitPropag. Nu va fi explicata aici, dar este functionala.
	
static private Rezultat DPLL_UnitPropag(Clauza atribuiri) -- functia care rezolva problema SAT.

	-- daca problemaSat contine o clauză falsificată de atribuiri atunci: return new Rezultat(Satisfiabil.UNSAT, atribuiri) 
	--cat timp problemaSat contine o clauză unitară se apeleaza ReturneazaClauzaUnitara(atribuiri) si se adauga in lista de atribuiri literalul gasit. Daca exista clauze falsificate: return new Rezultat(Satisfiabil.UNSAT, atribuiri);
	--daca atribuirea contine cate un literal pentru fiecare variabila: return new Rezultat(Satisfiabil.SAT, atribuiri)
	--Alegem un literal care nu se găseste in atribuire
	--Adaugam literalul, perand, in forma true si in forma false, aplicand DPLL_UnitPropag pentru fiecare noua atribuire.
	--functia returneaza UNSAT.



