limpiar:
	rm -rf bin
compilar:limpiar
	mkdir bin
	find src -name "*.java" | xargs javac -cp bin -d bin
jar:compilar
	jar cvfm catalogo.jar manifest.txt -C bin .
javadoc:compilar
	rm -rf html
	mkdir html
	find . -type f -name "*.java" | xargs javadoc -d html -encoding utf-8 -charset utf-8
