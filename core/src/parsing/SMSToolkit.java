package parsing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import com.moodysalem.phantomjs.wrapper.CommandLineArgument;
import com.moodysalem.phantomjs.wrapper.PhantomJS;
import com.moodysalem.phantomjs.wrapper.beans.PhantomJSExecutionResponse;
import com.moodysalem.phantomjs.wrapper.beans.PhantomJSOptions;

public class SMSToolkit {
	// public String
	// phantomPath="C:\\Users\\malawito\\Documents\\Workspaces\\SMS\\sms-gazpacho\\core\\lib\\phantomjs\\";
	public String phantomPath = "C:\\Users\\jagal\\Documents\\Repositories\\sms-gazpacho\\core\\lib\\phantomjs\\";

	public String getAffiliation(String author) {

		try {
			PhantomJSExecutionResponse response = PhantomJS.exec(new FileInputStream(phantomPath + "getaffiliation.js"),
			PhantomJSOptions.DEFAULT, new CommandLineArgument(author));
			return response.getStdOut().replaceAll("Verified email at ", "").replaceAll("\r\n", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Those here are to remain TODO find a good name
	public Table generateTable(String DBPath, Properties abbreviation, List<String> SMSDimmensions, boolean journals) {

		List<String> dimmensions = new LinkedList<String>();
		dimmensions.add("Rank");
		dimmensions.add("#Papers");
		dimmensions.add("Name");
		dimmensions.add("Acronym");

		dimmensions.addAll(SMSDimmensions);

		Parser p = new Parser();
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
