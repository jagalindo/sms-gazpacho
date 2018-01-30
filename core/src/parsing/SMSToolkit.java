package parsing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;

public class SMSToolkit {
	// public String
	// phantomPath="C:\\Users\\malawito\\Documents\\Workspaces\\SMS\\sms-gazpacho\\core\\lib\\phantomjs\\";

	Parser p = new Parser();

	public Table generateHeatMapCSV(String DBPath, String[] dimmension_1, String[] dimmension_2) {
		BibTeXDatabase db = p.readDatabase(DBPath);
		Table table = new Table(new String[] { "faceta", "facetb", "papers" });

		for (String rf : dimmension_1) {
			Collection<BibTeXEntry> rfc = p.filterByReview(db.getEntries().values(), rf);
			for (String vf : dimmension_2) {
				Collection<BibTeXEntry> vfc = p.filterByReview(rfc, vf);
				table.addRow(new Object[] { rf, vf, vfc.size() });
			}
		}
		return table;
	}

	public Table generateKeyTable(String DBPath, List<String> SMSDimmensions) {
		BibTeXDatabase db = p.readDatabase(DBPath);

		Table table = new Table(new String[] { "Facet", "Citations", "#Papers" });
		for (String dimmension : SMSDimmensions) {
			Collection<BibTeXEntry> papers = p.filterByReview(db.getEntries().values(), dimmension);
			String citations = "";
			for (BibTeXEntry e : papers) {
				citations += "\\citeS{" + e.getKey().getValue() + "}";
			}
			table.addRow(new Object[] { dimmension, citations, papers.size() });
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
		Collection<Entry<String, Collection<BibTeXEntry>>> orderedByValueSize = p
				.orderedByValueSize(groupByInstitution);

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

	public Table generateTrendsEvolution(String DBPath, String[] facets) {
		Table table = new Table(new String[] { "year", "type", "papers" });
		BibTeXDatabase db = p.readDatabase(DBPath);

		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db.getEntries().values(), "year");
		for (Entry<String, Set<BibTeXEntry>> entry : years_papers.entrySet()) {

			for (String var_facet : facets) {
				Collection<BibTeXEntry> vfc = p.filterByReview(entry.getValue(), var_facet);
				if (vfc.size() > 0) {
					table.addRow(
							new Object[] { entry.getKey(), var_facet, vfc.size() / (double) entry.getValue().size() });
				}
			}

		}
		return table;
	}

	public Table generateBarPlot(String DBPath) {
		Table table = new Table(new String[] { "year", "papers", "type" });
		BibTeXDatabase db = p.readDatabase(DBPath);

		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db.getEntries().values(), "year");
		
		for (Entry<String, Set<BibTeXEntry>> entry : years_papers.entrySet()) {

			double  journal = 0;
			double  conferences = 0;
			double  total = 0;

			for (BibTeXEntry bte : entry.getValue()) {
				if (bte.getType().getValue().equals("Article")) {
					journal++;
				} else if (bte.getType().getValue().equals("InProceedings")) {
					conferences++;
				}
			}
			total=(journal+conferences);
			table.addRow(new Object[] { entry.getKey(), truncate(journal / total), new String("Journal") });
			table.addRow(new Object[] { entry.getKey(), truncate(conferences / total), new String("Conference") });
		}
		return table;
	}

	private static double truncate(double d) {
		return BigDecimal.valueOf(d).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public boolean hasKey(BibTeXEntry e, String key) {
		org.jbibtex.Value value = e.getField(new Key("review"));

		if (value.toUserString().contains(key)) {
			return true;
		}else {
			return false;
		}
	}
	
	public Table generateDetailTable(String DBPath, List<String> SMSDimmensions) {
		List<String> firstRow= new LinkedList(SMSDimmensions);
		firstRow.add(0,"paper");
		
		String[] array= new String[firstRow.size()-1];
		for(int i=1;i<firstRow.size();i++) {
			array[i-1]=firstRow.get(i);
		}
		
		Table table = new Table(firstRow.toArray(new String[firstRow.size()]));
		BibTeXDatabase db = p.readDatabase(DBPath);

		for (BibTeXEntry bte : db.getEntries().values()) {
			List<Object> rowElements = new LinkedList<Object>();
			rowElements.add("\\citeS{"+bte.getKey()+"}");
			
			for(String key:array) {
				if(hasKey(bte, key)) {
					rowElements.add("X");
				}else {
					rowElements.add(" ");
				}
			}
			table.addRow(rowElements.toArray(new Object[0]));
		}
		return table;
	}

}
