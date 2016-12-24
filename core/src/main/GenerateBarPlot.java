package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class GenerateBarPlot {

	public static void main(String[] args) throws IOException, InterruptedException {
		String CURRENT_DIR="C:\\Users\\malawito\\Documents\\Repositorios\\sms-gazpacho\\core\\";
		String R_HOME="C:\\Program Files\\R\\R-3.3.2\\bin\\x64";
		
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/done.bib");
		String[] selectedConferences = { "SPLC", "ICSE", "VAMOS", "ISSTA", "ICSR", "ASE", "ICSE" };
		FileWriter csvwriter = new FileWriter(new File("./output_data/barplot.csv"));
//		csvwriter.write("year;journal;qac;nqac\r\n");
		csvwriter.write("year;papers;type\r\n");
		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db, "year");
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

//			csvwriter.write(entry.getKey() + ";" + journal + ";" + qac + ";" + nqac+"\r\n");
			csvwriter.write(entry.getKey() + ";" + journal + ";" + "Journal"+" \r\n");
			csvwriter.write(entry.getKey() + ";" + qac + ";" + "Variability Related Conference" +"\r\n");
			csvwriter.write(entry.getKey() + ";" + nqac + ";" + "Non Variability Related Conference" +"\r\n");

		}
		csvwriter.flush();
		csvwriter.close();

//		ProcessBuilder pb = new ProcessBuilder(R_HOME+"\\Rscript.exe "+CURRENT_DIR+"rscripts\\barplot.R");
//		pb.redirectOutput(Redirect.INHERIT);
//		pb.redirectError(Redirect.INHERIT);
//		Process exec = pb.start();
	
		
		
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
