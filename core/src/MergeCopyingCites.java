import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;
import org.jbibtex.Value;

import parsing.Parser;

public class MergeCopyingCites {

	public static void main(String[] args) {

		Parser parser = new Parser();
		BibTeXDatabase newDB = parser.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\2nd-step\\2.2.notGoodSources.bib");
		BibTeXDatabase oldDB = parser.readDatabase(
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\2nd-step\\nov16WithReviews.bib");

		BibTeXDatabase resultDB = new BibTeXDatabase();
		int count = 0;
		for (BibTeXEntry nEntry : newDB.getEntries().values()) {

			String nTitle = nEntry.getField(org.jbibtex.BibTeXEntry.KEY_TITLE).toUserString();
			boolean exists = parser.hasPaperWithTitle(nTitle, oldDB);

			if (exists) {
				// get old
				BibTeXEntry oldPaper = parser.getPaperWithTitle(nTitle, oldDB);
				// remove cites if there are
				oldPaper.removeField(new Key("note"));
				oldPaper.removeField(new Key("citedBy"));
				// get new cites
				Integer cites = 0;

				Value citedBy = nEntry.getField(new Key("citedBy"));
				Value note = nEntry.getField(new Key("citedBy"));

				if (citedBy != null) {
					// has cites
					cites = Integer.parseInt(citedBy.toUserString());
				} else if (note != null) {
					// no cites but note
					cites = Integer.parseInt(note.toUserString().replaceAll("cited By ", ""));
				} else {
					// no cites anyhow
				}

				// add new cites
				oldPaper.addField(new Key("citedBy"), new StringValue(cites.toString(), StringValue.Style.BRACED));
				// add to the result database
				resultDB.addObject(oldPaper);
				count++;
			} else {
				Integer cites = 0;

				Value citedBy = nEntry.getField(new Key("citedBy"));
				Value note = nEntry.getField(new Key("citedBy"));

				if (citedBy != null) {
					// has cites
					cites = Integer.parseInt(citedBy.toUserString());
				} else if (note != null) {
					// no cites but note
					cites = Integer.parseInt(note.toUserString().replaceAll("cited By ", ""));
				} else {
					// no cites anyhow
				}
				// remove cites if there are
				nEntry.removeField(new Key("note"));
				nEntry.removeField(new Key("citedBy"));
				// remove crappy field
				nEntry.removeField(new Key("__markedentry"));
								
				//insert a new clean citedBy
				nEntry.addField(new Key("citedBy"), new StringValue(cites.toString(), StringValue.Style.BRACED));

				resultDB.addObject(nEntry);
			}
		}
		System.out.println("Total reviews saved: " + count);
		parser.writeDatabase(resultDB,
				"C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\2nd-step\\result.bib");

	}

}
