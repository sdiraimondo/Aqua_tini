SRC=src
DEST=/Library/Tomcat/webapps/Ens/WEB-INF/classes

CLASSPATH=.:/Developer/Java/J2EE/jakarta-servletapi-4/lib/servlet.jar

ALL: $(DEST)/BonjourLeMonde.class $(DEST)/DireBonjour.class $(DEST)/Compteur.class

$(DEST)/BonjourLeMonde.class: $(SRC)/BonjourLeMonde.java
	javac -classpath $(CLASSPATH) $(SRC)/BonjourLeMonde.java -d $(DEST)

$(DEST)/DireBonjour.class: $(SRC)/DireBonjour.java
	javac -classpath $(CLASSPATH) $(SRC)/DireBonjour.java -d $(DEST)

$(DEST)/Compteur.class: $(SRC)/Compteur.java
	javac -classpath $(CLASSPATH) $(SRC)/Compteur.java -d $(DEST)

CLEAN:
	rm $(DEST)/BonjourLeMonde.class
	rm $(DEST)/DireBonjour.class
	rm $(DEST)/Compteur.class
