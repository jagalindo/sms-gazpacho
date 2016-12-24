package main;

import java.util.Collection;
import java.util.LinkedList;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;

public class FilterDatabaseByConf {

	public static void main(String[] args) {
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/done.bib");
		System.out.println("Initial size :"+db.getEntries().size());
		String[] selectedConferences = { "SPLC", "ICSE", "VAMOS", "ISSTA", "ICSR", "ASE", "ICSE" };
		Collection<BibTeXEntry> filtered = new LinkedList<BibTeXEntry>();
		filtered.addAll(p.filterJournals(db.getEntries().values()));

		for (String conf : selectedConferences) {
			filtered.addAll(p.filterByConference(db.getEntries().values(), conf));
		}

		BibTeXDatabase db2 = new BibTeXDatabase();
		for (BibTeXEntry e : filtered) {
			db2.addObject(e);
		}
		p.writeDatabase(db2,"./output_data/filtered.bib");
		System.out.println("End size :"+db2.getEntries().size());
	}

}
