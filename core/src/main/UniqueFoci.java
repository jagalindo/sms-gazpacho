package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Value;

import parsing.Parser;

public class UniqueFoci {

	public static void main(String[] args) throws IOException, InterruptedException {
		String CURRENT_DIR = "C:\\Users\\malawito\\Documents\\Repositorios\\sms-gazpacho\\core\\";
		String R_HOME = "C:\\Program Files\\R\\R-3.3.2\\bin\\x64";
		Properties prop = new Properties();
		prop.load(new FileReader("./data/abbreviation"));
		Parser p = new Parser();
		BibTeXDatabase db = p.readDatabase("./output_data/done.bib");
		FileWriter csvwriter = new FileWriter(new File("./output_data/conferenceTable.tex"));

		Map<String, Integer> conferences = new HashMap<String, Integer>();
		Map<String, Integer> journals = new HashMap<String, Integer>();

		for (BibTeXEntry bte : db.getEntries().values()) {
			if (bte.getType().getValue().equals("Article")) {
				Value journalV = bte.getField(BibTeXEntry.KEY_JOURNAL);
				Integer integer = journals.get(journalV.toUserString());

				if (integer == null) {
					journals.put(journalV.toUserString(), 1);
				} else {
					journals.put(journalV.toUserString(), integer + 1);
				}

			} else if (bte.getType().getValue().equals("InProceedings")) {
				Value booktitleV = bte.getField(BibTeXEntry.KEY_BOOKTITLE);
				Integer integer = conferences.get(booktitleV.toUserString());

				if (integer == null) {
					conferences.put(booktitleV.toUserString(), 1);
				} else {
					conferences.put(booktitleV.toUserString(), integer + 1);
				}
			}
		}
		int i = 1;
		int c = 0;
		System.out.println("There are: " + conferences.size() + " conferences and " + journals.size());
		conferences=sortByValue(conferences);
		for (Entry<String, Integer> e : conferences.entrySet()) {
			csvwriter.write(i + " & " + e.getValue() + " & " + prop.getProperty(e.getKey()) + "\\\\ \\hline \r\n");
			c += e.getValue();
			i++;
		}
		System.out.println("There is a total of " + c + "conference papers");
		i = 1;
		int j = 0;
		csvwriter.write("Journals\\\r\n");
		journals=sortByValue(journals);
		for (Entry<String, Integer> e : journals.entrySet()) {
			csvwriter.write(i + " & " +e.getValue() + " & " + prop.getProperty(e.getKey()) + "\\\\ \\hline \r\n");
			j += e.getValue();
			i++;
		}
		System.out.println("There is a total of " + j + "journal papers");

		csvwriter.flush();
		csvwriter.close();

		// ProcessBuilder pb = new ProcessBuilder(R_HOME+"\\Rscript.exe
		// "+CURRENT_DIR+"rscripts\\barplot.R");
		// pb.redirectOutput(Redirect.INHERIT);
		// pb.redirectError(Redirect.INHERIT);
		// Process exec = pb.start();

	}

	public static boolean isInList(String value, String[] array) {

		for (String s : array) {
			if (value.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;

	}

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

}
