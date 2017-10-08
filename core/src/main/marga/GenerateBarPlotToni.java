package main.marga;

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
		BibTeXDatabase db = p.readDatabase("./data_marga/marga-sms.bib");
		FileWriter csvwriter = new FileWriter(new File("./data_marga/trends.csv"));

		//String[] variability_facet={"k-vis","k-mmodel","k-configuration","k-testing","k-modeling","k-reverse"};
		String[] variability_facet={"QE","CS", "E","S"};

		csvwriter.write("year"+";"+"type"+";"+"papers\r\n");
		Map<String, Set<BibTeXEntry>> years_papers = p.countByKey(db.getEntries().values(), "year");
		for (Entry<String, Set<BibTeXEntry>> entry : years_papers.entrySet()) {

			System.out.println("The year :" + entry.getKey());
			for(String var_facet:variability_facet){
				Collection<BibTeXEntry> vfc= p.filterByReview(entry.getValue(),var_facet);
				if(vfc.size()>0){
					csvwriter.write(entry.getKey() + ";" + var_facet + ";" + vfc.size()/(double)entry.getValue().size()+" \r\n");
				}
			}

		}
		csvwriter.flush();
		csvwriter.close();
	
		
		
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
