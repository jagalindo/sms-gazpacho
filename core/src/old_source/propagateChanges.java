package old_source;

import java.util.Collection;
import java.util.LinkedList;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class propagateChanges {

	public static void main(String[] args) {

		Parser p = new Parser();

		Collection<String> selectedConferences = new LinkedList<String>();
		selectedConferences.add("SPLC");
		selectedConferences.add("ICSE");
		selectedConferences.add("VAMOS");
		selectedConferences.add("ASE");

		BibTeXDatabase full = p.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\all.bib");
		BibTeXDatabase filtered = p.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\selectedConfsAndJournals.bib");

		System.out.println("FULL size " + full.getEntries().size());
		System.out.println("FILTERED size " + filtered.getEntries().size());

		Collection<BibTeXEntry> fullUpdated = new LinkedList<BibTeXEntry>();

		// add back the journals and selected Conferences
		fullUpdated.addAll(filtered.getEntries().values());

		int d = 0;
		int s = 0;
		int j = 0;
		// Remove discarted titles after filtering
		for (BibTeXEntry entry : full.getEntries().values()) {
			if (entry.getType().getValue().equals("InProceedings")) {
				Value booktitle = entry.getField(BibTeXEntry.KEY_BOOKTITLE);
				String conferenceName = booktitle.toUserString();

				if (!(conferenceName.equals("SPLC") 
						|| conferenceName.equals("ICSE") 
						|| conferenceName.equals("VAMOS")
						|| conferenceName.equals("ASE"))) {
					fullUpdated.add(entry);
					s++;
				} else {
					// System.out.println(conferenceName);
					d++;
				}

			} else {
				j++;
			}
		}
		System.out.println("S " + s);
		System.out.println("D " + d);
		System.out.println("J " + j);
		System.out.println("FUPDATED " + fullUpdated.size());
		BibTeXDatabase db2 = new BibTeXDatabase();
		for (BibTeXEntry e : fullUpdated) {
			db2.addObject(e);
		}
		System.out.println("DB2 " + db2.getEntries().size());
		p.writeDatabase(db2,
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\all-corrected.bib");

	}

}
