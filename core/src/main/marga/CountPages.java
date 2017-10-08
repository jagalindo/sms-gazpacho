package main.marga;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class CountPages {

	public static void main(String[] args) throws IOException, InterruptedException {
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./data_ana/2017-06-21-todo.bib");
		FileWriter csvwriter = new FileWriter(new File("./data_ana/title_pages_greater_equal_10.csv"));

		BibTeXDatabase out = new BibTeXDatabase();

		for (BibTeXEntry bte : db.getEntries().values()) {
			Value field = bte.getField(org.jbibtex.BibTeXEntry.KEY_PAGES);
			Value title = bte.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);

			if (field != null) {
				String userStr = field.toUserString();
				if (!userStr.contains("----") && !userStr.contains("n/a") && !userStr.contains(":")) {
					int first = userStr.indexOf("-");
					int last = userStr.lastIndexOf("-");
					if (first > 0 && last > 0) {
						try {
							String izq = userStr.substring(0, first);
							String der = userStr.substring(last + 1, userStr.length());
							int izqInt = Integer.parseInt(izq.replaceAll(" ", ""));
							int derint = Integer.parseInt(der.replaceAll(" ", ""));
							if (!existInDataBase(title.toUserString(), out)&&((derint - izqInt )>=10)) {
								csvwriter.write(title.toUserString() + ";" + (derint - izqInt ) + "\r\n");
								out.addObject(bte);
							}
						} catch (NumberFormatException e) {
							System.err.println("Error en :" + userStr);
						}
					}
				} else {
					if (!existInDataBase(title.toUserString(), out)) {
						out.addObject(bte);
					}
				}
			} else {
				if (!existInDataBase(title.toUserString(), out)) {
					out.addObject(bte);
				}
			}

		}

		csvwriter.flush();
		csvwriter.close();
		p.writeDatabase(out, "./data_ana/greater_equal_10.bib");
		

	}

	public static boolean existInDataBase(String titulo, BibTeXDatabase db) {
		boolean res = false;

		for (BibTeXEntry e : db.getEntries().values()) {

			String title = e.getField(org.jbibtex.BibTeXEntry.KEY_TITLE).toUserString();
			if (title.equalsIgnoreCase(titulo)) {
				res = true;
				break;
			}
		}
		return res;

	}

}
