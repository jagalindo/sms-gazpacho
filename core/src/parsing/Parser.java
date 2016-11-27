package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.TokenMgrException;

public class Parser {

	public static BibTeXDatabase readDatabase(String file) {
		java.io.Reader reader = null;
		org.jbibtex.BibTeXParser bibtexParser;
		try {
			reader = new FileReader(new File(file));
			bibtexParser = new org.jbibtex.BibTeXParser();
			return bibtexParser.parse(reader);
		} catch (FileNotFoundException e) {
			System.err.println("The file you u.sed does not exist");
			e.printStackTrace();
		} catch (TokenMgrException | ParseException e) {
			System.err.println("There was a problem parsing this file.");
			e.printStackTrace();
		}
		return null;

	}

	public static int countNumberOfPapers(BibTeXDatabase db) {
		return db.getEntries().size();
	}

	public static BibTeXDatabase importEntries(BibTeXDatabase from, BibTeXDatabase to) {

		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> fromMap = from.getEntries();
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> toMap = to.getEntries();

		Collection<BibTeXEntry> toAdd = new LinkedList<BibTeXEntry>();
		Collection<BibTeXEntry> toRemove = new LinkedList<BibTeXEntry>();

		Collection<org.jbibtex.BibTeXEntry> entriesFrom = fromMap.values();
		for (org.jbibtex.BibTeXEntry entryFrom : entriesFrom) {
			org.jbibtex.Value reviewFrom = entryFrom.getField(new Key("review"));
			org.jbibtex.Value titleFrom = entryFrom.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
			if (reviewFrom != null) {
				Collection<org.jbibtex.BibTeXEntry> entriesTo = toMap.values();
				for (org.jbibtex.BibTeXEntry entryTo : entriesTo) {
					org.jbibtex.Value titleTo = entryTo.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
					if (titleTo.toUserString().equalsIgnoreCase(titleFrom.toUserString())) {
						toRemove.add(entryTo);
						toAdd.add(entryFrom);
					}
				}
			}
		}

		for (BibTeXEntry b : toRemove) {
			to.removeObject(b);

		}

		for (BibTeXEntry b : toAdd) {
			to.addObject(b);

		}

		return to;
	}

	public BibTeXDatabase addInformation(BibTeXDatabase db, String information) {
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = db.getEntries();
		for (org.jbibtex.BibTeXEntry entry : entryMap.values()) {
			entry.addField(new Key("information"), new StringValue(information, org.jbibtex.StringValue.Style.QUOTED));
		}

		return db;
	}

	public int countNumberOfPapers(BibTeXDatabase database, String... conditions) {
		int count = 0;
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = database.getEntries();

		Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
		for (org.jbibtex.BibTeXEntry entry : entries) {
			org.jbibtex.Value value = entry.getField(new Key("review"));
			if (value.toUserString().contains("")) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) throws FileNotFoundException, TokenMgrException, ParseException {

		BibTeXDatabase database = readDatabase("./testdata/papers.bib");
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = database.getEntries();

		Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
		for (org.jbibtex.BibTeXEntry entry : entries) {
			// org.jbibtex.Value value =
			// entry.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
			org.jbibtex.Value value = entry.getField(new Key("review"));

			if (value == null) {
				continue;
			}
			// Do something with the title value

			System.out.println(value.toUserString());
		}
	}

}
