package parsing;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

public class SMSToolkit {
	// public String
	// phantomPath="C:\\Users\\malawito\\Documents\\Workspaces\\SMS\\sms-gazpacho\\core\\lib\\phantomjs\\";

	Parser p = new Parser();

	public Table generateKeyTable(String DBPath, List<String> SMSDimmensions) {
		BibTeXDatabase db = p.readDatabase(DBPath);
		
		Table table= new Table(new String[] {"Facet", "Citations", "#Papers"});
		for(String dimmension: SMSDimmensions) {
			Collection<BibTeXEntry> papers = p.filterByReview(db.getEntries().values(), dimmension);
			String citations="";
			for(BibTeXEntry e:papers) {
				citations+="\\citeS{"+e.getKey().getValue()+"}";
			}
			table.addRow(new Object[] {dimmension, citations,papers.size()});
		}
		return table;
	}

	public Table generateInstitutionTable(String DBPath, List<String> SMSDimmensions) {
		List<String> dimmensions = new LinkedList<String>();
		dimmensions.add("Rank");
		dimmensions.add("#Papers");
		dimmensions.add("Institution");
		dimmensions.addAll(SMSDimmensions);
		
		BibTeXDatabase db = p.readDatabase(DBPath);
		Map<String, Collection<BibTeXEntry>> groupByInstitution = p.groupByInstitution(db.getEntries().values());
		Collection<Entry<String, Collection<BibTeXEntry>>> orderedByValueSize = p.orderedByValueSize(groupByInstitution);

		Table institutionTable = new Table(dimmensions.toArray(new String[0]));
		int rank = 1;

		for (Entry<String, Collection<BibTeXEntry>> institution : orderedByValueSize) {
			List<Object> rowElements = new LinkedList<Object>();
			rowElements.add(rank);

			rowElements.add(institution.getValue().size());
			rowElements.add(institution.getKey());
			for (String smsdimmension : SMSDimmensions) {
				rowElements.add(p.filterByReview(institution.getValue(), smsdimmension).size());
			}
			institutionTable.addRow(rowElements.toArray(new Object[0]));

			rank++;
		}
		return institutionTable;
	}

	public Table generateAuthorTable(String DBPath, List<String> SMSDimmensions) {
		BibTeXDatabase db = p.readDatabase(DBPath);

		Map<String, Collection<BibTeXEntry>> groupByAuthor = p.groupByAuthor(db.getEntries().values(), true);
		groupByAuthor = p.getLongestKey(groupByAuthor);
		Collection<Entry<String, Collection<BibTeXEntry>>> orderedByValueSize = p.orderedByValueSize(groupByAuthor);

		List<String> dimmensions = new LinkedList<String>();
		dimmensions.add("Rank");
		dimmensions.add("#Papers");
		dimmensions.add("Author");
		dimmensions.addAll(SMSDimmensions);

		Table authorTable = new Table(dimmensions.toArray(new String[0]));
		int rank = 1;

		for (Entry<String, Collection<BibTeXEntry>> author : orderedByValueSize) {
			List<Object> rowElements = new LinkedList<Object>();
			rowElements.add(rank);

			rowElements.add(author.getValue().size());
			rowElements.add(author.getKey());
			for (String smsdimmension : SMSDimmensions) {
				rowElements.add(p.filterByReview(author.getValue(), smsdimmension).size());
			}
			authorTable.addRow(rowElements.toArray(new Object[0]));

			rank++;
		}
		return authorTable;
	}

	// Those here are to remain TODO find a good name
	public Table generateVenueTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions,
			boolean journals) {

		List<String> dimmensions = new LinkedList<String>();
		dimmensions.add("Rank");
		dimmensions.add("#Papers");
		dimmensions.add("Name");
		dimmensions.add("Acronym");

		dimmensions.addAll(SMSDimmensions);

		BibTeXDatabase db = p.readDatabase(DBPath);

		Table resultTable = new Table(dimmensions.toArray(new String[0]));

		Collection<BibTeXEntry> entries;
		if (journals) {
			// Get the Journals
			entries = p.filterJournals(db.getEntries().values());
		} else {
			entries = p.filterConferences(db.getEntries().values());

		}
		// Group of journals
		Map<String, Collection<BibTeXEntry>> groupedContributionsByVenue = p.groupContributionsByVenue(entries);

		// get the journnals ordered
		Collection<Entry<String, Collection<BibTeXEntry>>> listOfEntries = p
				.orderedByValueSize(groupedContributionsByVenue);

		// Create the ranking integer
		int rank = 1;
		for (Entry<String, Collection<BibTeXEntry>> journalPapers : listOfEntries) {

			List<Object> rowElements = new LinkedList<Object>();
			rowElements.add(rank);
			rowElements.add(journalPapers.getValue().size());
			rowElements.add(abbreviation.getProperty(journalPapers.getKey()));
			rowElements.add(journalPapers.getKey());

			for (String smsdimmension : SMSDimmensions) {
				rowElements.add(p.filterByReview(journalPapers.getValue(), smsdimmension).size());
			}
			resultTable.addRow(rowElements.toArray(new Object[0]));

			rank++;
		}

		return resultTable;

	}
}
