package parsing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Table {

	String[] headers;
	List<Object[]> rows;
	
	int rowNumber=0;
	
	public Table(String[] headers) {
		this.headers=headers;
		this.rowNumber++;
		rows= new LinkedList<Object[]>();
	}
	
	public void addRow(Object[] row) {
		if(row.length!=headers.length) {
			throw new IllegalStateException();
		}
		
		rows.add(row);
	}
	
	public void reduceToRows(int n) {
		this.rows=new LinkedList<>(rows.subList(0, n));
	}
	
	public void reduceToColumns(String[] desiredHeaders) {
		List<Integer> desiredColumns= new LinkedList<Integer>();
		
		//select the columns to keep TODO CORRECT THIS
		for(String desiredHeader:desiredHeaders) {
			for(int c=0;c<headers.length;c++) {
				if((headers[c].equals(desiredHeader))&&!desiredColumns.contains(c)) {
					desiredColumns.add(c);
					
				}
			}
		}
		//System.out.println("Desired: "+desiredColumns.size()+" Total: "+headers.length1);
//aqui hay un problema de actualizacion de indices
		String[] newHeader=new String[desiredColumns.size()];
		LinkedList<Object[]> newrows= new LinkedList<Object[]>();

		//update header
		for(int i=0;i<desiredColumns.size();i++) {
			newHeader[i]=headers[desiredColumns.get(i)];
		}
		
		for(Object[] row:rows) {
			Object[] nrow=new Object[desiredColumns.size()];
			for(int i=0;i<desiredColumns.size();i++) {
				nrow[i]=row[desiredColumns.get(i)];
			}
			newrows.add(nrow);
		}
		
		headers=newHeader;
		rows=newrows;
		
	}
	public void printLatexTable(String path) {
		try {
			FileWriter result = new FileWriter(new File(path));
			result.write(this.printLatexTable());
			result.flush();
			result.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printCSVTable(String path) {
		try {
			FileWriter result = new FileWriter(new File(path));
			result.write(this.printCSV());
			result.flush();
			result.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String printLatexTable() {
		String result="";
		
		String newLine="\\\\\\hline\r\n";
		String sep="&";
		
		for(int c=0;c<headers.length;c++) {
			if(c<headers.length-1) {
				result+=headers[c]+sep;
			}else {
				result+=headers[c];
			}
		}
		result+=newLine;
		for(Object[] row:rows) {
			for(int c=0;c<=headers.length-1;c++) {
				if(c<headers.length-1) {
					result+=row[c]+sep;
				}else {
					result+=row[c];
				}
			}
			result+=newLine;
		}
		return result;
	}
	
	public String printCSV() {
		String result="";
		
		String newLine="\r\n";
		String sep=";";
		
		for(int c=0;c<=headers.length-1;c++) {
			if(c!=headers.length) {
				result+=headers[c]+sep;
			}else {
				result+=headers[c];
			}
		}
		result+=newLine;
		for(Object[] row:rows) {
			for(int c=0;c<=headers.length-1;c++) {
				if(c!=headers.length) {
					result+=row[c]+sep;
				}else {
					result+=row[c];
				}
			}
			result+=newLine;
		}
		return result;
	}

	
}
