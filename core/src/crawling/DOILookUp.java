package crawling;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.ObjectResolutionException;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import parsing.Parser;

public class DOILookUp {

	// Search string google "feature model" AND ("reasoning" OR "analysis" OR
	// "automated" OR "analyses")
	// Search string scopus "feature model" AND {"reasoning" OR "analysis" OR
	// "automated" OR "analyses"}

	static String bibtexFile = "C:\\Users\\malawito\\Dropbox (US)\\Papers\\jagalindo18-computing\\bibtex-02-2018\\all.bib";

	static String url = "https://www.crossref.org/guestquery/";

	static int pagesToProcess = 5;
	static WebDriver driver;
	// static Collection<String> referencesToDownload = new LinkedList<String>();
	static Random r;
	static public HashMap<String, String> ref_cites = new HashMap<String, String>();

	public static void main(String[] args)
			throws InterruptedException, ObjectResolutionException, TokenMgrException, ParseException, IOException {
		PrintWriter out = new PrintWriter("dois.csv");

		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
		driver = new ChromeDriver();
		r = new Random();
		driver.get(url);

		Parser p = new Parser();
		BibTeXDatabase full = p.readDatabase(bibtexFile);
		double i=1;
		for (BibTeXEntry e : full.getEntries().values()) {
			String title = e.getField(BibTeXEntry.KEY_TITLE).toUserString();
			String author = e.getField(BibTeXEntry.KEY_AUTHOR).toUserString();
			if (author.indexOf(',') > 1) {
				author = author.substring(0, author.indexOf(','));
			}
			driver.findElement(By.name("atitle2")).clear();
			driver.findElement(By.name("atitle2")).sendKeys(title);
			driver.findElement(By.name("auth2")).clear();
			driver.findElement(By.name("auth2")).sendKeys(author);
			driver.findElement(By.name("article_title_search")).click();
			Thread.sleep(5000);
			for (WebElement element : driver.findElements(By.tagName("a"))) {
				String link = element.getAttribute("href");
				if (link.contains("dx.doi.org")) {
					out.println(e.getField(BibTeXEntry.KEY_TITLE).toUserString() + "|" + link);
					out.flush();
				}
			}
			System.out.println("Completed: "+i/full.getEntries().size());
			i++;
		}

		out.close();
		// Close the browser
		driver.quit();
	}

}
