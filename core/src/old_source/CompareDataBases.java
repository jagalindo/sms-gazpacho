package old_source;

import java.util.Collection;
import java.util.LinkedList;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;

public class CompareDataBases {

	public static void main(String[] args) {

		Parser p = new Parser();

		BibTeXDatabase full = p.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\all.bib");
		BibTeXDatabase filtered = p.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\selectedConfsAndJournals.bib");


		System.out.println("Full " + full.getEntries().size());
		System.out.println("Filtered " + filtered.getEntries().size());

		System.out.println("Full - journals " + p.filterJournals(full.getEntries().values()).size());
		System.out.println("Filtered - journals " + p.filterJournals(filtered.getEntries().values()).size());
		
		System.out.println("Full - conferences " + p.filterConferences(full.getEntries().values()).size());
		System.out.println("Filtered - conferences " + p.filterConferences(filtered.getEntries().values()).size());
	
		
		
		
		String[] selectedConferences = { "SPLC", "ICSE", "VAMOS", "ASE" };
		Collection<BibTeXEntry> selectedFull = new LinkedList<BibTeXEntry>();
		
		for (String conf : selectedConferences) {
			selectedFull.addAll(p.filterByConference(full.getEntries().values(), conf));
		}
		System.out.println("Selected conferences from full :"+selectedFull.size());
		
		
		Collection<BibTeXEntry> result = new LinkedList<BibTeXEntry>();
		result.addAll(filtered.getEntries().values());
		for(BibTeXEntry e:selectedFull) {
			if(!p.contains(filtered, e)) {
				result.add(e);
			}
		}
		
		BibTeXDatabase resultDB = new BibTeXDatabase();
		for (BibTeXEntry e : result) {
			resultDB.addObject(e);
		}
		p.writeDatabase(resultDB,"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\selectedConfsAndJournals.2.bib");
	
	}

	

}
