package main.marga;

import org.jbibtex.BibTeXDatabase;

import parsing.Configurator;
import parsing.Parser;

public class ReviewDatabase {

	public static void main(String[] args) {
		Parser p = new Parser();
		Configurator c = new Configurator();
		
		BibTeXDatabase database=p.readDatabase("./out_new.bib");
		c.configure(database);
		
		p.writeDatabase(database, "./out_new_reviewed.bib");


	}

}
