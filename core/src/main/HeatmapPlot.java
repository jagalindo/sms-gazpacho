package main;

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
		BibTeXDatabase db = p.readDatabase("./output_data/filtered.bib");

		FileWriter csvwriter = new FileWriter(new File("./output_data/heatmap.csv"));

		String[] research_facet={"k-experience","k-philosophical","k-opinion","k-solution","k-evaluation","k-validation"};
		String[] variability_facet={"k-vis","k-mmodel","k-configuration","k-testing","k-modeling","k-reverse"};
		
		
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
