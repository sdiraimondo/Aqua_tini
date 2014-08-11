# Makefile - AQUA2006

# Fichier cible .tini à produire
CIBLE=bin/Aquarium

# Répertoire contenant les sources
SRC=src

# Répertoire contenant les classes produites
CLASS=./class

# Chemin des classes
CLASSPATH=$(CLASS)

# Chemin des javadocs
JAVADOCDIR=docs

# Chemin de l'API tini
TINI=/usr/local/tini1.17

# Chemin des utilitaires tini 
TINI_JARPATH=$(TINI)/bin

# Chemin de l'API Taylec
TAYLEC_LIB=$(TINI)/Taylec/lib
TAYLEC_TINI=$(TAYLEC_LIB)/com/taylec/tini

# Chemin des archives .jar
JARPATH=$(TINI_JARPATH)/tini.jar:$(TINI_JARPATH)/modules.jar:$(TINI_JARPATH)/tiniclasses.jar:$(TAYLEC_LIB)/TaylecTINI.jar

# Commandes
JAVAC=javac -target 1.1 -O
JAVADOC=javadoc -author -version -public -nodeprecatedlist -quiet
JAVA=java

# Compilation
all: $(CIBLE).tini

$(CIBLE).tini:
	$(JAVAC) -classpath $(JARPATH) $(SRC)/*.java -d $(CLASS)
	$(JAVA) -classpath $(TINI_JARPATH)/tini.jar:$(TAYLEC_LIB):$(TINI)/Taylec:$(TINI)/Taylec/lib BuildDependency -add I2CAll -x $(TINI_JARPATH)/owapi_dep.txt -p $(TINI_JARPATH)/modules.jar -f $(CLASS) -f $(TAYLEC_LIB)/TaylecTINI.jar -o $(CIBLE).tini -d $(TINI_JARPATH)/tini.db


# Javadoc
docs :
	$(JAVADOC) $(SRC)/*.java -doctitle AQUA2006 -d $(JAVADOCDIR) -classpath $(JARPATH)


# Nettoyage
clean:
	rm -f $(CIBLE).tini $(CLASS)/*.class $(SRC)/*.java~
	
cleanall:
	rm -f $(CIBLE).tini $(CLASS)/*.class $(SRC)/*.java~ Makefile~
	rm -Rf $(JAVADOCDIR)

cleandocs:
	rm -Rf $(JAVADOCDIR)


