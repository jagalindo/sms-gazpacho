package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.TokenMgrException;
import org.jbibtex.Value;

public class Parser {

	public BibTeXDatabase readDatabase(String file) {
		java.io.Reader reader = null;
		org.jbibtex.BibTeXParser bibtexParser;
		try {
			reader = new FileReader(new File(file));
			bibtexParser = new org.jbibtex.BibTeXParser();
			return bibtexParser.parse(reader);
		} catch (FileNotFoundException e) {
			System.err.println("The file you used does not exist.");
			e.printStackTrace();
		} catch (TokenMgrException | ParseException e) {
			System.err.println("There was a problem parsing this file.");
			e.printStackTrace();
		}
		return null;

	}

	public void writeDatabase(BibTeXDatabase db, String file) {

		Writer writer;
		try {
			writer = new FileWriter(new File(file));
			BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
			bibtexFormatter.format(db, writer);
		} catch (IOException e) {
			System.err.println("It was impossible to write out the database.");
			e.printStackTrace();
		}

	}

	public int countNumberOfPapers(BibTeXDatabase db) {
		return db.getEntries().size();
	}

	public BibTeXDatabase importEntries(BibTeXDatabase from, BibTeXDatabase to) {

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

	public Map<String, Set<BibTeXEntry>> countByKey(Collection<BibTeXEntry> entries, String key) {
		Map<String, Set<BibTeXEntry>> res = new HashMap<String, Set<BibTeXEntry>>();

		for (org.jbibtex.BibTeXEntry entry : entries) {
			org.jbibtex.Value value = entry.getField(new Key(key));
			if (value != null) {
				Set<BibTeXEntry> count = res.get(value.toUserString());
				if (count == null) {
					Set<BibTeXEntry> set = new HashSet<BibTeXEntry>();
					set.add(entry);
					res.put(value.toUserString(), set);
				} else {
					count.add(entry);
				}
			}
		}
		return res;

	}

	public BibTeXDatabase addInformation(BibTeXDatabase db, String information) {
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = db.getEntries();
		for (org.jbibtex.BibTeXEntry entry : entryMap.values()) {
			entry.addField(new Key("information"), new StringValue(information, org.jbibtex.StringValue.Style.QUOTED));
		}

		return db;
	}

	public int countNumberOfPapersWithReview(BibTeXDatabase database, String... conditions) {
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

	public BibTeXDatabase merge(BibTeXDatabase... inputs) {

		BibTeXDatabase res = new BibTeXDatabase();
		for (BibTeXDatabase in : inputs) {
			res.getEntries().putAll(in.getEntries());
		}
		return res;
	}

	public BibTeXDatabase mergeIntelligently(BibTeXDatabase... inputs) {

		BibTeXDatabase res = new BibTeXDatabase();
		for (BibTeXDatabase in : inputs) {
			Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = in.getEntries();
			Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
			for (org.jbibtex.BibTeXEntry entry : entries) {
				if (!contains(res, entry)) {
					res.addObject(entry);
				}
			}
		}
		return res;
	}

	private boolean contains(BibTeXDatabase res, BibTeXEntry in) {
		Value in_value = in.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);

		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = res.getEntries();
		Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
		for (org.jbibtex.BibTeXEntry entry : entries) {
			org.jbibtex.Value value = entry.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
			if (value != null) {
				if (value.toUserString().equalsIgnoreCase(in_value.toUserString())) {
					return true;
				}
			}
		}

		return false;
	}

	public void remove(BibTeXDatabase db, BibTeXDatabase db2) {
		Collection<BibTeXEntry> toRemove = new LinkedList<BibTeXEntry>();

		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = db.getEntries();
		Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
		for (org.jbibtex.BibTeXEntry entry : entries) {
			if (!contains(db2, entry)) {
				toRemove.add(entry);
			}

		}

		for (BibTeXEntry e : toRemove) {
			db.removeObject(e);
		}

	}

	public Collection<BibTeXEntry> filterJournals(Collection<BibTeXEntry> values) {
		Collection<BibTeXEntry> res = new LinkedList<BibTeXEntry>();

		for (BibTeXEntry entry : values) {
			if (entry.getType().getValue().equals("Article")) {
				res.add(entry);
			}
		}

		return res;
	}

	public Collection<BibTeXEntry> filterByConference(Collection<BibTeXEntry> values, String rf) {
		Collection<BibTeXEntry> res = new LinkedList<BibTeXEntry>();

		for (BibTeXEntry entry : values) {
			if (entry.getType().getValue().equals("InProceedings")) {
				Value booktitle = entry.getField(BibTeXEntry.KEY_BOOKTITLE);
				String conferenceName = booktitle.toUserString();
				if (conferenceName.equalsIgnoreCase(rf)) {
					res.add(entry);
				}
			}
		}

		return res;
	}

	public Collection<BibTeXEntry> filterByReview(Collection<BibTeXEntry> values, String rf) {
		Collection<BibTeXEntry> res = new LinkedList<BibTeXEntry>();

		for (BibTeXEntry entry : values) {
			Value review = entry.getField(new Key("review"));
			if (review != null) {
				String reviewString = review.toUserString();
				if (reviewString.contains(rf)) {
					res.add(entry);
				}
			}
		}

		return res;
	}

	// Venue, set of contrib
	public Map<String, Collection<BibTeXEntry>> groupContributionsByVenue(Collection<BibTeXEntry> db) {

		Map<String, Collection<BibTeXEntry>> groupedPapers = new HashMap<String, Collection<BibTeXEntry>>();

		for (BibTeXEntry bte : db) {

			// Get the venue name
			String venueName = "";
			if (bte.getType().getValue().equals("Article")) {
				venueName = bte.getField(BibTeXEntry.KEY_JOURNAL).toUserString();
			} else if (bte.getType().getValue().equals("InProceedings")) {
				venueName = bte.getField(BibTeXEntry.KEY_BOOKTITLE).toUserString();
			}

			Collection<BibTeXEntry> group = groupedPapers.get(venueName);
			if (group == null) {
				group = new LinkedList<>();
			}
			group.add(bte);
			groupedPapers.put(venueName, group);
		}
		return groupedPapers;
	}

	public Map<String, Collection<BibTeXEntry>> getLongestKey(Map<String, Collection<BibTeXEntry>> grupedAuthors) {
		Map<String, Collection<BibTeXEntry>> res = new HashMap<String, Collection<BibTeXEntry>>();

		for (Entry<String, Collection<BibTeXEntry>> e : grupedAuthors.entrySet()) {
			String currentName = e.getKey();
			for (BibTeXEntry be : e.getValue()) {
				String author = be.getField(org.jbibtex.BibTeXEntry.KEY_AUTHOR).toUserString();
				// get the first author
				if (author.contains("and")) {
					author = author.substring(0, author.indexOf("and")).trim();
					if (author.length() > currentName.length()) {
						currentName = author;
					}
				}
			}
			res.put(currentName, e.getValue());
		}

		return res;
	}

	// group by authos
	public Map<String, Collection<BibTeXEntry>> groupByAuthor(Collection<BibTeXEntry> db, boolean onlyFirstAuthor) {

		Map<String, Collection<BibTeXEntry>> groupedPapers = new HashMap<String, Collection<BibTeXEntry>>();
		for (BibTeXEntry bte : db) {
			String authors = bte.getField(org.jbibtex.BibTeXEntry.KEY_AUTHOR).toUserString();

			if (onlyFirstAuthor) {
				int indexOf = authors.indexOf("and");
				// It does have more than one author
				if (indexOf > 0) {
					authors = authors.substring(0, indexOf);
				}

				authors = authors.trim();
				// this thing here is to take into account only the surname and the first letter
				// of the name
				// TODO aqui hay que hacer que meta el nombre largo para despues poder sacar
				// bien la institucion
				if (authors.contains(",")) {
					authors = authors.substring(0, authors.indexOf(", ") + 3);
				}

				Collection<BibTeXEntry> collection = groupedPapers.get(authors);
				if (collection == null) {
					collection = new LinkedList<BibTeXEntry>();
				}
				collection.add(bte);
				groupedPapers.put(authors, collection);

			} else if (!onlyFirstAuthor) {
				StringTokenizer tokenizer = new StringTokenizer(authors, "and");
				while (tokenizer.hasMoreTokens()) {
					String author = tokenizer.nextToken().trim();
					if (author.contains(",")) {
						author = authors.substring(0, author.indexOf(",") + 3);
					}
					Collection<BibTeXEntry> collection = groupedPapers.get(author);
					if (collection == null) {
						collection = new LinkedList<BibTeXEntry>();
					}
					collection.add(bte);
					groupedPapers.put(author, collection);

				}
			}
		}
		return groupedPapers;
	}

	public Collection<Entry<String, Collection<BibTeXEntry>>> orderedByValueSize(
			Map<String, Collection<BibTeXEntry>> groupedContributions) {
		// Create a comparator for the entry set of the venues
		Comparator<Entry<String, Collection<BibTeXEntry>>> sizeValueReverseComparator = new Comparator<Entry<String, Collection<BibTeXEntry>>>() {
			@Override
			public int compare(Entry<String, Collection<BibTeXEntry>> e1, Entry<String, Collection<BibTeXEntry>> e2) {
				Integer v1 = e1.getValue().size();
				Integer v2 = e2.getValue().size();
				return v2.compareTo(v1);
			}
		};

		List<Entry<String, Collection<BibTeXEntry>>> listOfEntries = new ArrayList<Entry<String, Collection<BibTeXEntry>>>(
				groupedContributions.entrySet());

		// order that list
		Collections.sort(listOfEntries, sizeValueReverseComparator);
		return listOfEntries;
	}

	public Collection<BibTeXEntry> filterConferences(Collection<BibTeXEntry> values) {
		Collection<BibTeXEntry> res = new LinkedList<BibTeXEntry>();

		for (BibTeXEntry entry : values) {
			if (entry.getType().getValue().equals("InProceedings")) {
				res.add(entry);
			}
		}

		return res;
	}

}
