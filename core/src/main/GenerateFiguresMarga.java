package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import data.Barplot;
import data.HeatMap;
import data.TrendsEvolution;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import parsing.SMSToolkit;
import parsing.Table;

public class GenerateFiguresMarga {

	// Defining mapping dimmensions
	static String[] varDims = { "SU", "QE", "EX", "CS" };
	static String[] resDims = { "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12" };

	// Load abbrivation for journals and confenreces
	static Properties abbreviation = new Properties();
	static SMSToolkit toolkit = new SMSToolkit();

	// Input File paths
	static String fullBibPath = "C:\\Users\\malawito\\Dropbox (US)\\LatexMapping\\marga-sms.bib";

	// Output File paths
	static String outputPath = "./output/";

	public static void main(String[] args) throws FileNotFoundException, IOException, TemplateException {
		// load abbreviation
		abbreviation.load(new FileReader("C:\\Users\\malawito\\Dropbox (US)\\LatexMapping\\abbreviation"));

		List<String> allDims = new ArrayList<String>();
		allDims.addAll(Arrays.asList(varDims));
		allDims.addAll(Arrays.asList(resDims));

		// ------------------------------------------------------
		// ---------Where are the papers published---------------
		// ------------------------------------------------------

		 generateJournalTables(fullBibPath, abbreviation, allDims);
		 generateConferencesTables(fullBibPath, abbreviation, allDims);

		// ------------------------------------------------------
		// ---------------Who are the authors--------------------
		// ------------------------------------------------------

		 generateAuthorsTables(fullBibPath,allDims);
		 generateInstitutionsTables(fullBibPath,allDims);

		// ------------------------------------------------------
		// --------------------------Why-------------------------
		// ------------------------------------------------------
		 generateKeyTables(fullBibPath,Arrays.asList(varDims),Arrays.asList(resDims));

		// ------------------------------------------------------
		// -------------------------When-------------------------
		// ------------------------------------------------------
		//generateConferenceVsCites(fullBibPath);
		generateHeatMap(fullBibPath,abbreviation);
		generateTrendsEvolution(fullBibPath);
		generateBarPlot(fullBibPath);
	}

	public static void generateHeatMap(String DBPath, Properties abbreviation) throws IOException, TemplateException {
		Table heatmapTable = toolkit.generateHeatMapCSV(DBPath, resDims, varDims);
		heatmapTable.printCSVTable(outputPath + "heatmap.csv");

		Map<String, String> names = new HashMap<String, String>();

		names.put("SU", "Survey");
		names.put("CS", "Case Study");
		names.put("EX", "Experiment");
		names.put("QE", "Quasi-experiment");
		names.put("C2", "C2 - Software requirements");
		names.put("C3", "C3 - Software design");
		names.put("C4", "C4 - Software construction");
		names.put("C5", "C5 - Software testing");
		names.put("C6", "C6 - Software maintenance");
		names.put("C7", "C7 - Software configuration management");
		names.put("C8", "C8 - Software engineering management");
		names.put("C9", "C9 - Software engineering process");
		names.put("C10", "C10 - Software engineering tools and methods");
		names.put("C11", "C11 - Software quality");
		names.put("C12", "C12 - Related Disciplines of Software Engineering");

		HeatMap hm = new HeatMap("./heatmap.csv", "./heatmap.pdf", "Types of empirical study used",
				"Knowledge Areas (KAs)", resDims, varDims, names);

		/* Create a data-model */
		Map root = new HashMap();
		root.put("data", hm);

		/* Get the template (uses cache internally) */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setDirectoryForTemplateLoading(new File("./src/scripts/"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);

		Template temp = cfg.getTemplate("heatmap.R");

		/* Merge data-model with template */
		Writer out = new OutputStreamWriter(new FileOutputStream(outputPath + "heatmap.R"), "UTF-8");
		temp.process(root, out);

	}

	public static void generateTrendsEvolution(String DBPath) throws IOException, TemplateException {
		Table heatmapTable = toolkit.generateTrendsEvolution(DBPath, varDims);
		heatmapTable.printCSVTable(outputPath + "trendsEvolution.csv");

		Map<String, String> names = new HashMap<String, String>();

		names.put("SU", "Survey");
		names.put("CS", "Case Study");
		names.put("EX", "Experiment");
		names.put("QE", "Quasi-experiment");
		names.put("C2", "C2 - Software requirements");
		names.put("C3", "C3 - Software design");
		names.put("C4", "C4 - Software construction");
		names.put("C5", "C5 - Software testing");
		names.put("C6", "C6 - Software maintenance");
		names.put("C7", "C7 - Software configuration management");
		names.put("C8", "C8 - Software engineering management");
		names.put("C9", "C9 - Software engineering process");
		names.put("C10", "C10 - Software engineering tools and methods");
		names.put("C11", "C11 - Software quality");
		names.put("C12", "C12 - Related Disciplines of Software Engineering");

		String[] years= new String[] {"2010","2011","2012","2013","2014","2015","2016","2017"};
		TrendsEvolution hm = new TrendsEvolution("./trendsEvolution.csv", "./trendsEvolution.pdf",
				"Knowledge Areas (KAs)", names,years);

		/* Create a data-model */
		Map root = new HashMap();
		root.put("data", hm);

		/* Get the template (uses cache internally) */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setDirectoryForTemplateLoading(new File("./src/scripts/"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);

		Template temp = cfg.getTemplate("trendsEvolution.R");

		/* Merge data-model with template */
		Writer out = new OutputStreamWriter(new FileOutputStream(outputPath + "trendsEvolution.R"), "UTF-8");
		temp.process(root, out);
	}
	
	public static void generateBarPlot(String DBPath) throws IOException, TemplateException {
		Table heatmapTable = toolkit.generateBarPlot(DBPath);
		heatmapTable.printCSVTable(outputPath + "barPlot.csv");

		Map<String, String> names = new HashMap<String, String>();

		names.put("SU", "Survey");
		names.put("CS", "Case Study");
		names.put("EX", "Experiment");
		names.put("QE", "Quasi-experiment");
		names.put("C2", "C2 - Software requirements");
		names.put("C3", "C3 - Software design");
		names.put("C4", "C4 - Software construction");
		names.put("C5", "C5 - Software testing");
		names.put("C6", "C6 - Software maintenance");
		names.put("C7", "C7 - Software configuration management");
		names.put("C8", "C8 - Software engineering management");
		names.put("C9", "C9 - Software engineering process");
		names.put("C10", "C10 - Software engineering tools and methods");
		names.put("C11", "C11 - Software quality");
		names.put("C12", "C12 - Related Disciplines of Software Engineering");

		String[] years= new String[] {"2010","2011","2012","2013","2014","2015","2016","2017"};
		Barplot hm = new Barplot("./barPlot.csv", "./barPlot.pdf",years);

		/* Create a data-model */
		Map root = new HashMap();
		root.put("data", hm);

		/* Get the template (uses cache internally) */
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		cfg.setDirectoryForTemplateLoading(new File("./src/scripts/"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);

		Template temp = cfg.getTemplate("barPlot.R");

		/* Merge data-model with template */
		Writer out = new OutputStreamWriter(new FileOutputStream(outputPath + "barPlot.R"), "UTF-8");
		temp.process(root, out);
	}
	

	public static void generateJournalTables(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateVenueTable(DBPath, abbreviation, SMSDimmensions, true);

		Table basicTable = fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym" });

		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym", "SU", "QE", "EX", "QS" });

		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym", "C2", "C3", "C4", "C5", "C6",
				"C7", "C8", "C9", "C10", "C11", "C12" });

		fullTable.printLatexTable(outputPath + "fullTableJournal.tex");
		basicTable.printLatexTable(outputPath + "basicTableJournal.tex");
		varTable.printLatexTable(outputPath + "varTableJournal.tex");
		resTable.printLatexTable(outputPath + "resTableJournal.tex");

		fullTable.printCSVTable(outputPath + "fullTableJournal.csv");
		basicTable.printCSVTable(outputPath + "basicTableJournal.csv");
		varTable.printCSVTable(outputPath + "varTableJournal.csv");
		resTable.printCSVTable(outputPath + "resTableJournal.csv");

	}

	public static void generateConferencesTables(String DBPath, Properties abbreviation, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateVenueTable(DBPath, abbreviation, SMSDimmensions, false);

		Table basicTable = fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym" });

		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym", "SU", "QE", "EX", "QS" });

		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank", "#Papers", "Name", "Acronym", "C2", "C3", "C4", "C5", "C6",
				"C7", "C8", "C9", "C10", "C11", "C12" });

		fullTable.printLatexTable(outputPath + "fullTableConference.tex");
		basicTable.printLatexTable(outputPath + "basicTableConference.tex");
		varTable.printLatexTable(outputPath + "varTableConference.tex");
		resTable.printLatexTable(outputPath + "resTableConference.tex");

		fullTable.printCSVTable(outputPath + "fullTableConference.csv");
		basicTable.printCSVTable(outputPath + "basicTableConference.csv");
		varTable.printCSVTable(outputPath + "varTableConference.csv");
		resTable.printCSVTable(outputPath + "resTableConference.csv");

	}

	public static void generateAuthorsTables(String DBPath, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateAuthorTable(DBPath, SMSDimmensions);

		Table basicTable = fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] { "Rank", "#Papers", "Author" });

		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank", "#Papers", "Author", "SU", "QE", "EX", "QS" });

		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank", "#Papers", "Author", "C2", "C3", "C4", "C5", "C6", "C7", "C8",
				"C9", "C10", "C11", "C12" });

		fullTable.printLatexTable(outputPath + "fullTableAuthor.tex");
		basicTable.printLatexTable(outputPath + "basicTableAuthor.tex");
		varTable.printLatexTable(outputPath + "varTableAuthor.tex");
		resTable.printLatexTable(outputPath + "resTableAuthor.tex");

		fullTable.printCSVTable(outputPath + "fullTableAuthor.csv");
		basicTable.printCSVTable(outputPath + "basicTableAuthor.csv");
		varTable.printCSVTable(outputPath + "varTableAuthor.csv");
		resTable.printCSVTable(outputPath + "resTableAuthor.csv");

	}

	public static void generateInstitutionsTables(String DBPath, List<String> SMSDimmensions) {
		Table fullTable = toolkit.generateInstitutionTable(DBPath, SMSDimmensions);

		Table basicTable = fullTable.cloneTable();
		basicTable.reduceToColumns(new String[] { "Rank", "#Papers", "Institution" });

		Table varTable = fullTable.cloneTable();
		varTable.reduceToColumns(new String[] { "Rank", "#Papers", "Institution", "SU", "QE", "EX", "QS" });

		Table resTable = fullTable.cloneTable();
		resTable.reduceToColumns(new String[] { "Rank", "#Papers", "Institution", "C2", "C3", "C4", "C5", "C6", "C7",
				"C8", "C9", "C10", "C11", "C12"

		});

		fullTable.printLatexTable(outputPath + "fullTableInstitution.tex");
		basicTable.printLatexTable(outputPath + "basicTableInstitution.tex");
		varTable.printLatexTable(outputPath + "varTableInstitution.tex");
		resTable.printLatexTable(outputPath + "resTableInstitution.tex");

		fullTable.printCSVTable(outputPath + "fullTableInstitution.csv");
		basicTable.printCSVTable(outputPath + "basicTableInstitution.csv");
		varTable.printCSVTable(outputPath + "varTableInstitution.csv");
		resTable.printCSVTable(outputPath + "resTableInstitution.csv");

	}

	public static void generateKeyTables(String DBPath, List<String> variability, List<String> research) {
		Table fullVarTable = toolkit.generateKeyTable(DBPath, variability);
		fullVarTable.printLatexTable(outputPath + "tableVariability.tex");
		fullVarTable.printCSVTable(outputPath + "tableVariability.csv");

		Table fullResTable = toolkit.generateKeyTable(DBPath, research);
		fullResTable.printLatexTable(outputPath + "tableResearch.tex");
		fullResTable.printCSVTable(outputPath + "tableResearch.csv");

	}

}
