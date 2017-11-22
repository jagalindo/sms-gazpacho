package main.jose;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;

import parsing.Parser;
import parsing.SMSToolkit;
import parsing.Table;

public class GenerateFigures {

	//Defining mapping dimmensions
	static String[] varDims = { "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse"};
	static String[] resDims = { "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" };
	
	//Load abbrivation for journals and confenreces
	static Properties abbreviation = new Properties();
	static SMSToolkit toolkit = new SMSToolkit();

	//Input File paths
	static String fullBibPath="./data/nov16/processed/full.bib";
	static String filteredBibPath="./data/nov16/processed/filtered.bib";
	static String fullBibCitesPath="./data/nov16/processed/cited.bib";

	//Output File paths
	static String outputPath="./output/";
		
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//load abbreviation
		abbreviation.load(new FileReader("./data/abbreviation"));
		
		List<String> allDims = new ArrayList<String>();
		allDims.addAll(Arrays.asList(varDims));
		allDims.addAll(Arrays.asList(resDims));

		//------------------------------------------------------
		//-Research question 1: Where are the papers published--
		//------------------------------------------------------
		
//		generateJournalTables(fullBibPath, abbreviation, allDims);
//		generateConferencesTables(fullBibPath, abbreviation, allDims);
	
		//------------------------------------------------------
		//------Research question 2: Who are the authors--------
		//------------------------------------------------------

//		generateAuthorsTables(fullBibPath,allDims);
//		generateInstitutionsTables(fullBibPath,allDims);
	
		//------------------------------------------------------
		//---------------Research question 3: Why---------------
		//------------------------------------------------------
//		generateKeyTables(filteredBibPath,Arrays.asList(varDims),Arrays.asList(resDims));
		
		//------------------------------------------------------
		//---------------Research question 4: When--------------
		//------------------------------------------------------
		generateConferenceVsCites(fullBibCitesPath);
		
	}

	public static void generateJournalTables(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateVenueTable(DBPath, abbreviation, SMSDimmensions, true);

		Table basicTable=fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] {"Rank","#Papers","Name","Acronym"});
		
		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank","#Papers","Name","Acronym", "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse" });
		
		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank","#Papers","Name","Acronym", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		
		fullTable.printLatexTable(outputPath+"fullTableJournal.tex");
		basicTable.printLatexTable(outputPath+"basicTableJournal.tex");
		varTable.printLatexTable(outputPath+"varTableJournal.tex");
		resTable.printLatexTable(outputPath+"resTableJournal.tex");
		
		fullTable.printCSVTable(outputPath+"fullTableJournal.csv");
		basicTable.printCSVTable(outputPath+"basicTableJournal.csv");
		varTable.printCSVTable(outputPath+"varTableJournal.csv");
		resTable.printCSVTable(outputPath+"resTableJournal.csv");

	}
	
	public static void generateConferencesTables(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateVenueTable(DBPath, abbreviation, SMSDimmensions, false);

		Table basicTable=fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] {"Rank","#Papers","Name","Acronym"});
		
		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank","#Papers","Name","Acronym", "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse" });
		
		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank","#Papers","Name","Acronym", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		
		fullTable.printLatexTable(outputPath+"fullTableConference.tex");
		basicTable.printLatexTable(outputPath+"basicTableConference.tex");
		varTable.printLatexTable(outputPath+"varTableConference.tex");
		resTable.printLatexTable(outputPath+"resTableConference.tex");
		
		fullTable.printCSVTable(outputPath+"fullTableConference.csv");
		basicTable.printCSVTable(outputPath+"basicTableConference.csv");
		varTable.printCSVTable(outputPath+"varTableConference.csv");
		resTable.printCSVTable(outputPath+"resTableConference.csv");

	}

	public static void generateAuthorsTables(String DBPath,List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateAuthorTable(DBPath, SMSDimmensions);
		
		Table basicTable=fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] {"Rank","#Papers","Author"});
		
		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank","#Papers","Author", "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse" });
		
		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank","#Papers","Author", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		
		fullTable.printLatexTable(outputPath+"fullTableAuthor.tex");
		basicTable.printLatexTable(outputPath+"basicTableAuthor.tex");
		varTable.printLatexTable(outputPath+"varTableAuthor.tex");
		resTable.printLatexTable(outputPath+"resTableAuthor.tex");
		
		fullTable.printCSVTable(outputPath+"fullTableAuthor.csv");
		basicTable.printCSVTable(outputPath+"basicTableAuthor.csv");
		varTable.printCSVTable(outputPath+"varTableAuthor.csv");
		resTable.printCSVTable(outputPath+"resTableAuthor.csv");
	
	}
	
	
	public static void generateInstitutionsTables(String DBPath,List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateInstitutionTable(DBPath, SMSDimmensions);
		
		Table basicTable=fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] {"Rank","#Papers","Institution"});
		
		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank","#Papers","Institution", "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse" });
		
		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank","#Papers","Institution", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		
		fullTable.printLatexTable(outputPath+"fullTableInstitution.tex");
		basicTable.printLatexTable(outputPath+"basicTableInstitution.tex");
		varTable.printLatexTable(outputPath+"varTableInstitution.tex");
		resTable.printLatexTable(outputPath+"resTableInstitution.tex");
		
		fullTable.printCSVTable(outputPath+"fullTableInstitution.csv");
		basicTable.printCSVTable(outputPath+"basicTableInstitution.csv");
		varTable.printCSVTable(outputPath+"varTableInstitution.csv");
		resTable.printCSVTable(outputPath+"resTableInstitution.csv");
	
	}
	
	public static void generateKeyTables(String DBPath,List<String> variability,List<String> research) {
		Table fullVarTable = toolkit.generateKeyTable(DBPath, variability);
		fullVarTable.printLatexTable(outputPath+"tableVariability.tex");
		fullVarTable.printCSVTable(outputPath+"tableVariability.csv");
		
		Table fullResTable = toolkit.generateKeyTable(DBPath, research);
		fullResTable.printLatexTable(outputPath+"tableResearch.tex");
		fullResTable.printCSVTable(outputPath+"tableResearch.csv");

	}
	
	public static void generateConferenceVsCites(String DBPath) {
		Parser p  = new Parser();
		BibTeXDatabase db = p.readDatabase(DBPath);

		String[] selectedConferences = { "SPLC", "ICSE", "VAMOS", "ASE" };

		Collection<BibTeXEntry> all = db.getEntries().values();
		
		Collection<BibTeXEntry> filtered = new LinkedList<BibTeXEntry>();
		filtered.addAll(p.filterJournals(db.getEntries().values()));
		System.out.println("We found "+filtered.size()+" elements");
		
		for (String conf : selectedConferences) {
			filtered.addAll(p.filterByConference(db.getEntries().values(), conf));
		}
		
		//all.removeAll(filtered);

		System.out.println("Filtered nº cites: "+countCites(filtered));
		System.out.println("Total nº cites: "+countCites(all));
		
		
	}
	
	private static int countCites(Collection<BibTeXEntry> btw) {
		int res=0;
		for(BibTeXEntry e:btw) {
			
			res+=Integer.parseInt(e.getField(new Key("cites")).toUserString());
		}
		return res;
	}

	
}
