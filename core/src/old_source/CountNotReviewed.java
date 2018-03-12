package old_source;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;

import parsing.Parser;

public class CountNotReviewed {

	public static void main(String[] args) {
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\selectedConfsAndJournals.2.bib");
		int notReview=0;
		for(BibTeXEntry e:db.getEntries().values()) {
			org.jbibtex.Value review = e.getField(new Key("review"));
			if(review==null||review.toUserString()=="") {
				notReview++;
			}
		}
		System.out.println(notReview);
	}

}
