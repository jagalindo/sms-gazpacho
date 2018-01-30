package main.jose;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class GenerateBarPlot {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/done.bib");
		String[] selectedConferences = { "SPLC", "ICSE", "VAMOS", "ASE" };
		FileWriter csvwriter = new FileWriter(new File("./output_data/barplot.csv"));
//		csvwriter.write("year;journal;qac;nqac\r\n");
		csvwriter.write("year;papers;type\r\n");
		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db.getEntries().values(), "year");
		int tjournal = 0;
		int tconferences = 0;
		int tqac = 0;
		int tnqac = 0;

		for (Entry<String, Set<BibTeXEntry>> entry : years_papers.entrySet()) {

			System.out.println("The year :" + entry.getKey());

			int journal = 0;
			int conferences = 0;
			int qac = 0;
			int nqac = 0;

			for (BibTeXEntry bte : entry.getValue()) {
				if (bte.getType().getValue().equals("Article")) {
					journal++;
//					Value booktitle = bte.getField(BibTeXEntry.KEY_JOURNAL);
				} else if (bte.getType().getValue().equals("InProceedings")) {
					conferences++;
					Value booktitle = bte.getField(BibTeXEntry.KEY_BOOKTITLE);
					if (isInList(booktitle.toUserString(), selectedConferences)) {
						qac++;
					} else {
						nqac++;
					}
				}
			}

			System.out.println("Journal: " + journal + "; Conferences: " + conferences + "; QAConferences: " + qac
					+ "; NQAConferences: " + nqac);
			tjournal+=journal;
			tconferences+=conferences;
			tqac+=qac;
			tnqac+=nqac;
				
			double total=journal+qac+nqac;
//			csvwriter.write(entry.getKey() + ";" + journal + ";" + qac + ";" + nqac+"\r\n");
			csvwriter.write(entry.getKey() + ";" + truncate(journal/total) + ";" + "Journal"+" \r\n");
			csvwriter.write(entry.getKey() + ";" + truncate(qac/total) + ";" + "Variability Related Conference" +"\r\n");
			csvwriter.write(entry.getKey() + ";" + truncate(nqac/total) + ";" + "Non Variability Related Conference" +"\r\n");

		}
		csvwriter.flush();
		csvwriter.close();
		System.out.println("TOTAL Journal: " + tjournal + "; Conferences: " + tconferences + "; QAConferences: " + tqac
				+ "; NQAConferences: " + tnqac);
		
//		ProcessBuilder pb = new ProcessBuilder(R_HOME+"\\Rscript.exe "+CURRENT_DIR+"rscripts\\barplot.R");
//		pb.redirectOutput(Redirect.INHERIT);
//		pb.redirectError(Redirect.INHERIT);
//		Process exec = pb.start();
	
		
		
	}

	private static double truncate(double d) {
		return BigDecimal.valueOf(d)
	    .setScale(2, RoundingMode.HALF_UP)
	    .doubleValue();
	}
	public static boolean isInList(String value, String[] array) {

		for (String s : array) {
			if (value.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;

	}

}
