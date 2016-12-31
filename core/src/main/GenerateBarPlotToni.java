package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class GenerateBarPlotToni {

	public static void main(String[] args) throws IOException, InterruptedException {
		String CURRENT_DIR="C:\\Users\\malawito\\Documents\\Repositorios\\sms-gazpacho\\core\\";
		String R_HOME="C:\\Program Files\\R\\R-3.3.2\\bin\\x64";
		
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/filtered.bib");
		FileWriter csvwriter = new FileWriter(new File("./output_data/barplot2.csv"));
		String[] variability_facet={"k-vis","k-mmodel","k-configuration","k-testing","k-modeling","k-reverse"};
		
		//		csvwriter.write("year;journal;qac;nqac\r\n");
		csvwriter.write("year;type;papers\r\n");
		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db, "year");
		for (Entry<String, Set<BibTeXEntry>> entry : years_papers.entrySet()) {

			System.out.println("The year :" + entry.getKey());
			for(String var_facet:variability_facet){
				Collection<BibTeXEntry> vfc= p.filterByReview(entry.getValue(),var_facet);
				csvwriter.write(entry.getKey() + ";" + var_facet + ";" + entry.getValue().size()/vfc.size()+" \r\n");

			}

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
