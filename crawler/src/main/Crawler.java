package main;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Crawler {

	// Search string google "feature model" AND ("reasoning" OR "analysis" OR "automated" OR "analyses")
	// Search string scopus "feature model" AND {"reasoning" OR "analysis" OR "automated" OR "analyses"}
	
	static String file = "30-01-2018.bib";
	static String urlDavid = "https://scholar.google.es/scholar?cites=8673778080292292169&as_sdt=2005&sciodt=0,5&hl=en";
	static String urlSearch = "https://scholar.google.es/scholar?hl=en&as_sdt=0%2C5&q=%22feature+model%22+AND+%28%22reasoning%22+OR+%22analysis%22+OR+%22automated%22+OR+%22analyses%22&btnG=";
	
	
	static WebDriver driver;
	static Collection<String> referencesToDownload = new LinkedList<String>();
	static PrintWriter out;
	static Random r;
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		
		WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
		driver = new ChromeDriver();
		out = new PrintWriter(file);
		r= new Random();
	
		
		waitForEnter();

		boolean hasMore = false;
		do {
			getCitations();
			Thread.sleep(10000+r.nextInt(20000));
			try {
				WebElement findElement = driver.findElement(By.linkText("Next"));
				findElement.click();
				hasMore = true;
			} catch (NoSuchElementException e) {
				hasMore = false;
				waitForEnter();
				try {
					WebElement findElement = driver.findElement(By.linkText("Next"));
					findElement.click();
					hasMore = true;
				} catch (NoSuchElementException e2s) {
					hasMore = false;
					
				}
			}
		} while (hasMore);

		printbibtexs();
		out.close();
		
		// Close the browser
		driver.quit();
	}

	private static void printbibtexs() throws InterruptedException {
		for (String url : referencesToDownload) {
			Thread.sleep(10000+r.nextInt(20000));
			driver.get(url);
			String bibtex = driver.getPageSource();
			out.println(bibtex);
			out.flush();
		}
	}

	public static void getCitations() {
		// Find the text input element by its name
		List<WebElement> list = driver.findElements(By
				.linkText("Import into BibTeX"));

		for (WebElement elem : list) {
			String reference = elem.getAttribute("href");
			if (reference.contains("output=citation")) {
				referencesToDownload.add(reference);

			}
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
