package crawling;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.BibTeXFormatter;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ObjectResolutionException;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.TokenMgrException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GScholar {

	// Search string google "feature model" AND ("reasoning" OR "analysis" OR
	// "automated" OR "analyses")
	// Search string scopus "feature model" AND {"reasoning" OR "analysis" OR
	// "automated" OR "analyses"}

	static String bibtexFile = "02-02-2018-bena(961).bib";

	static String urlDavid = "https://scholar.google.es/scholar?cites=8673778080292292169&as_sdt=2005&sciodt=0,5&hl=en";
	static String urlSearch = "https://scholar.google.es/scholar?hl=en&as_sdt=0%2C5&q=%22feature+model%22+AND+%28%22reasoning%22+OR+%22analysis%22+OR+%22automated%22+OR+%22analyses%22&btnG=";

	static int pagesToProcess = 19;
	static WebDriver driver;
	// static Collection<String> referencesToDownload = new LinkedList<String>();
	static PrintWriter bibtexWriter, citationWriter;
	static Random r;
	static public HashMap<String, String> ref_cites = new HashMap<String, String>();

	public static void main(String[] args) throws InterruptedException, ObjectResolutionException, TokenMgrException, ParseException, IOException {

		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
		driver = new ChromeDriver();
		bibtexWriter = new PrintWriter(bibtexFile);
		r = new Random();
		driver.get(urlDavid);

		waitForEnter();

		do {
			processPage();
			Thread.sleep(100000 + r.nextInt(20000));
			try {
				WebElement findElement = driver.findElement(By.linkText("Next"));
				findElement.click();
				pagesToProcess--;
			} catch (NoSuchElementException e) {
				pagesToProcess = 0;
				waitForEnter();
				try {
					WebElement findElement = driver.findElement(By.linkText("Next"));
					findElement.click();
					pagesToProcess--;
				} catch (NoSuchElementException e2s) {
					pagesToProcess = 0;

				}
			}
		} while (pagesToProcess > 0);

		processBibtexs();
		bibtexWriter.close();
		citationWriter.close();

		// Close the browser
		driver.quit();
	}

	public static void processPage() {

		List<WebElement> papers = driver.findElements(By.className("gs_r"));

		for (WebElement elem : papers) {
			processPaper(elem);
		}
	}

	public static void processPaper(WebElement elem) {
		String bibtexLink = elem.findElement(By.linkText("Import into BibTeX")).getAttribute("href");
		String citationCount = elem.findElement(By.partialLinkText("Cited by")).getText();
		ref_cites.put(bibtexLink, citationCount);
	}

	private static void processBibtexs() throws InterruptedException, ObjectResolutionException, TokenMgrException, ParseException, IOException {
		for (Entry<String, String> e : ref_cites.entrySet()) {
			Thread.sleep(100000 + r.nextInt(20000));
			driver.get(e.getKey());
			String bibtex = driver.findElement(By.tagName("pre")).getText();
			//-----------
			StringReader sr= new StringReader(bibtex);
			BibTeXParser bibtexParser = new BibTeXParser();
			BibTeXDatabase database = bibtexParser.parse(sr);
			BibTeXEntry entry =database.getEntries().values().iterator().next();
			entry.addField(new Key("citedBy"), new StringValue(e.getValue().replace("Cited by ", ""),StringValue.Style.BRACED));
			//-----------
			BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
			StringWriter sw = new StringWriter();
			bibtexFormatter.format(database, sw);
			bibtexWriter.println(sw.toString());
			bibtexWriter.flush();
		}
	}

	public static void waitForEnter() {
		try {
			System.out.println("Press a key to continue");
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
