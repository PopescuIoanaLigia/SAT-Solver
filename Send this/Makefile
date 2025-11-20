# Target build: compilează toate fișierele .java din CodulJava/src
build:
	javac -d CodulJava CodulJava/src/*.java

# Target run: execută clasa principală Solver cu fișierele de intrare și ieșire
run: build
	java -cp CodulJava Solver $(INPUT) $(OUTPUT)

# Target clean: șterge fișierele compilate
clean:
	rm -f CodulJava/*.class
