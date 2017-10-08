package main.jose;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;
import parsing.SMSToolkit;
import parsing.Table;

public class GenerateFigures {

	//Defining mapping dimmensions
	static String[] dims = { "k-vis", "k-mmodel", "k-configuration", "k-testing", "k-modeling", "k-reverse",
			"k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" };
	static List<String> dimsAsList = Arrays.asList(dims);
	
	//Load abbrivation for journals and confenreces
	static Properties abbreviation = new Properties();
	static SMSToolkit toolkit = new SMSToolkit();

	static String fullBibPath="./data/nov16/processed/full.bib";
	static String filteredBibPath="./data/nov16/processed/filtered.bib";
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		abbreviation.load(new FileReader("./data/abbreviation"));
		
		//------------------------------------------------------
		//-Research question 1: Where are the papers published--
		//------------------------------------------------------
		
		////Main tables 
		//generateJournalTable(fullBibPath, abbreviation, dimsAsList);
		//generateConferenceTable(fullBibPath, abbreviation, dimsAsList);
		////Secondary 
		//generateResearchJournalTable(fullBibPath, abbreviation, dimsAsList);
		//generateResearchConferenceTable(fullBibPath, abbreviation, dimsAsList);
		//generateVariabilityJournalTable(fullBibPath, abbreviation, dimsAsList);
		//generateVariabilityConferenceTable(fullBibPath, abbreviation, dimsAsList);

		//------------------------------------------------------
		//------Research question 2: Who are the authors--------
		//------------------------------------------------------
		listFirstAuthors(fullBibPath);
		
	}

	public static void generateJournalTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, true);
		generateTable.reduceToRows(10);
		System.out.println(generateTable.printLatexTable());
		
	}
	
	public static void generateConferenceTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, false);
		generateTable.reduceToRows(10);
		System.out.println(generateTable.printLatexTable());
		
	}
	
	public static void generateResearchJournalTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, true);
		generateTable.reduceToRows(10);
		generateTable.reduceToColumns(new String[] { "Rank", "Name", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		System.out.println(generateTable.printLatexTable());
		
	}
	
	public static void generateResearchConferenceTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, false);
		generateTable.reduceToRows(10);
		generateTable.reduceToColumns(new String[] { "Rank", "Name", "k-experience", "k-philosophical", "k-opinion", "k-solution", "k-evaluation", "k-validation" });
		System.out.println(generateTable.printLatexTable());
		
	}
	public static void generateVariabilityJournalTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, true);
		generateTable.reduceToRows(10);
		generateTable.reduceToColumns(new String[] { "Rank", "Name", "k-vis", "k-mmodel", "k-configuration",
				"k-testing", "k-modeling", "k-reverse" });
		System.out.println(generateTable.printLatexTable());
		
	}
	
	public static void generateVariabilityConferenceTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table generateTable = toolkit.generateTable(DBPath, abbreviation, SMSDimmensions, false);
		generateTable.reduceToRows(10);
		generateTable.reduceToColumns(new String[] { "Rank", "Name", "k-vis", "k-mmodel", "k-configuration",
				"k-testing", "k-modeling", "k-reverse" });
		System.out.println(generateTable.printLatexTable());
		
	}

	public static void listFirstAuthors(String DBPath) {
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase(DBPath);
		SMSToolkit toolkit = new SMSToolkit();
		
		Map<String, Collection<BibTeXEntry>> groupByAuthor = p.groupByAuthor(db.getEntries().values(), true);
		Collection<Entry<String,Collection<BibTeXEntry>>> orderedByValueSize = p.orderedByValueSize(groupByAuthor);
		
		
		
		Table authorTable= new Table(new String[] {"#papers","author"});
		for(Entry<String, Collection<BibTeXEntry>> author:orderedByValueSize) {
			//System.out.println(toolkit.getAffiliation(author.getKey().replaceAll(" ", "+")));
			authorTable.addRow(new Object[] {author.getValue().size(),author.getKey()});
		}
		//authorTable.reduceToRows(10);
		System.out.println(authorTable.printLatexTable());
		
	}
}
