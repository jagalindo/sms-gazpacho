package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

import parsing.Parser;

public class GenAll {

	public static void main(String[] args) throws IOException {

		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/filtered.bib");

		FileWriter table_var = new FileWriter(new File("./output_data/table_variability.csv"));
		FileWriter data_var = new FileWriter(new File("./output_data/data_variability.csv"));

		String[] variability_facet={"k-vis","k-mmodel","k-configuration","k-testing","k-modeling","k-reverse"};
		data_var.write("thing;count\r\n");
		for(String var:variability_facet){
			table_var.write(var+"&\\citeS{");
			Collection<BibTeXEntry> rfc= p.filterByReview(db.getEntries().values(),var);
			data_var.write(var+";"+rfc.size()+"\r\n");
			
			for(BibTeXEntry be: rfc){
				table_var.write(be.getKey().getValue()+",");
			}
			table_var.write("}"+"\\hline\r\n");
		}				
		
		table_var.close();
		data_var.close();
		
		FileWriter table_res = new FileWriter(new File("./output_data/table_research.csv"));
		FileWriter data_res = new FileWriter(new File("./output_data/data_research.csv"));

		String[] research_facet={"k-experience","k-philosophical","k-opinion","k-solution","k-evaluation","k-validation"};
		data_res.write("thing;count\r\n");
		for(String var:research_facet){
			table_res.write(var+"&\\citeS{");
			Collection<BibTeXEntry> rfc= p.filterByReview(db.getEntries().values(),var);
			data_res.write(var+";"+rfc.size()+"\r\n");
			
			for(BibTeXEntry be: rfc){
				table_res.write(be.getKey().getValue()+",");
			}
			table_res.write("}"+"\\hline\r\n");
		}				
		
		table_res.close();
		data_res.close();
				
	}

}
