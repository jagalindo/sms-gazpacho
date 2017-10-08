package main.marga;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;

public class HeatmapPlot {

	public static void main(String[] args) throws IOException, InterruptedException {
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./data_marga/marga-sms.bib");

		FileWriter csvwriter = new FileWriter(new File("./data_marga/heatmap.csv"));
		String[] variability_facet={"QE","CS", "E","S"};
		 
		String[] research_facet={"C2","C3","C4","C5","C6","C7","C8","C9","C10","C11","C12"};
		
		
		
		csvwriter.write("rf;vf;count"+"\r\n");
		for(String rf:research_facet){
			Collection<BibTeXEntry> rfc= p.filterByReview(db.getEntries().values(),rf);
			for(String vf:variability_facet){
				Collection<BibTeXEntry> vfc= p.filterByReview(rfc,vf);
				csvwriter.write(rf+";"+vf+";"+vfc.size()+"\r\n");
			}
		}				
		
		csvwriter.flush();
		csvwriter.close();

	}
}
