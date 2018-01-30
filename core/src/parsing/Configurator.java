package parsing;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;
import org.jbibtex.Value;

public class Configurator {

	public void configure(BibTeXDatabase db) {

		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("~");
		Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = db.getEntries();
		Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();

		for (BibTeXEntry entry : entries) {

			Value title_v = entry.getField(new Key("title"));
			Value abstract_v = entry.getField(new Key("abstract"));
			Value review_v = entry.getField(new Key("review"));

			System.out.println("Title: " + title_v.toUserString());
			
			if (abstract_v == null || abstract_v.toUserString() == "") {
				System.err.println("Please introduce the abstract");
				String abstractStr = scanner.next();
				entry.addField(new Key("abstract"), new StringValue(abstractStr, StringValue.Style.QUOTED));
				abstract_v = entry.getField(new Key("abstract"));

			}
			
			System.out.println("Abstract:\r\n" + abstract_v.toUserString());
			if (review_v == null || review_v.toUserString() == "") {
				System.err.println("Please introduce the review");
				String reviewStr = scanner.next();
				entry.addField(new Key("review"), new StringValue(reviewStr, StringValue.Style.QUOTED));
				review_v = entry.getField(new Key("review"));
			}			
			
			System.out.println("Current review: " + review_v.toUserString());

			
		}

		scanner.close();
	}

}
