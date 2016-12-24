package main;

import java.util.HashSet;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class listFirstAuthors {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/done.bib");

		Set<String> authorsSet = new HashSet<String>();
		for (BibTeXEntry e : db.getEntries().values()) {
			Value authorsV = e.getField(org.jbibtex.BibTeXEntry.KEY_AUTHOR);
			String authors = authorsV.toUserString();
			int indexOf = authors.indexOf(" and");
			if (indexOf > 0) {
				String substring = authors.substring(0, indexOf);
				System.out.println(substring.replaceAll(" ", "+"));
				
			}else{
				System.out.println(authors.replaceAll(" ", "+"));
			}
		}
	}

}
