package old_source;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;

public class UpdateDatabase {

	public static void main(String[] args) {
		Parser p = new Parser();
		
		//Load the 200 entries out from each query
		BibTeXDatabase initial_scholar_citing=p.readDatabase("./data/nov16/initial/scholar-citing-all-771.bib");
		BibTeXDatabase initial_scholar_string=p.readDatabase("./data/nov16/initial/scholar-string-all-12300.bib");
		BibTeXDatabase initial_scopus_citing=p.readDatabase("./data/nov16/initial/scopus-citing-all-455.bib");
		BibTeXDatabase initial_scopus_string=p.readDatabase("./data/nov16/initial/scopus-string-all-351.bib");

		//Remove duplicates from initial databases (based on title)
		BibTeXDatabase merged=p.mergeIntelligently(initial_scholar_citing,initial_scholar_string,initial_scopus_citing,initial_scopus_string);
	
		//Printout some information
		System.out.println("The merged database contains "+p.countNumberOfPapers(merged)+" papers.");

		//Load the related papers done manually and remove non existent papers in merged
		BibTeXDatabase related=p.readDatabase("./data/nov16/related_manual.bib");
		p.remove(merged,related);
		
		//Printout some information
		System.out.println("The database without unrelated contains "+p.countNumberOfPapers(merged)+" papers.");
		
		
		//Load the database from June'16 to reuse the classification		
		BibTeXDatabase june16=p.readDatabase("./data/june16/june16.bib");
		p.importEntries(june16, merged);
		
		//Get some scores
		Map<String, Set<BibTeXEntry>> countByKey = p.countByKey(merged.getEntries().values(), "year");
		for(Entry<String,Set<BibTeXEntry>> years:countByKey.entrySet()){
			System.out.println(years.getKey()+" "+years.getValue().size());
		}
		
		//Write down the database
		p.writeDatabase(merged, "./done.bib");
	}

}
