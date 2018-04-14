package org.grobid.tools;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.Table;
import technology.tabula.TextChunk;
import technology.tabula.extractors.BasicExtractionAlgorithm;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class TabulaTools {
    private static final String EU_002_PDF = "data/26295473.PDF";

    /*	Counts the number of pages,loop through all the pages and pass to tablesInPage*/
    public static  void pagesInFile(File file) throws IOException
    {
        PDDocument document = PDDocument .load(file);
        int pageCount=document.getNumberOfPages();
        BasicExtractionAlgorithm bel = new BasicExtractionAlgorithm(document);
        ObjectExtractor oe = new ObjectExtractor(document);
        Page page = null;
        for(int currentPage = 1; currentPage <= pageCount; currentPage++)
        {
            page = oe.extract(currentPage);
            tablesInPage(page,currentPage);
        }
    }

    /*Counts the number of tables,loop through all the tables and pass tables to covertToCSV */
    public static void tablesInPage(Page page,int currentPage) throws IOException
    {
        SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
        int pageNum = currentPage;
        //System.out.println(pageNum+":"+sea.isTabular(page));



        /*Counting num of rectangles available*/


		/*SpreadsheetDetectionAlgorithm sda=new SpreadsheetDetectionAlgorithm();
		List<Rectangle> rect=sda.detect(page);
		Iterator<Rectangle> itrrect=rect.iterator();
		int co=0;

		System.out.println("\n\n\nPAGE: "+pageNum +"\n Rectangles  \n\n");
  	  while(itrrect.hasNext()){
  		Rectangle t=itrrect.next();
  		System.out.println("Heyyyy:"+t.toString());
  		++co;
  	  }
		System.out.println("Number of rectangles:"+co);*/

        /*Counting num of rectangles available*/
        List<Table> tablist = new ArrayList<Table>();
        tablist = (List<Table>)sea.extract(page);
        int tableNum = 0;
        Iterator<Table> itr = tablist.iterator();
        while(itr.hasNext()){
            Table t = itr.next();
            convertToCSV(t, pageNum, ++tableNum);
            System.out.println(t.getExtractionAlgorithm().toString());
        }
    }

    /*Prints the content of the tables and makes separate CSV file for each table*/
    public static void convertToCSV(Table table,int currentPage,int currentTable) throws IOException
    {
        CSVWriter csvfile = new CSVWriter ();
        StringBuilder sb = new StringBuilder();
        csvfile.write(sb, table);
        String myvalues = sb.toString();
        System.out.println(myvalues);
        int pageNum = currentPage;
        int tableNum = currentTable;
        FileOutputStream oS = new FileOutputStream(new File(EU_002_PDF + pageNum + "_" + tableNum + ".csv"));
        try{
        oS.write(myvalues.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File(EU_002_PDF);
        pagesInFile(file);
    }
}
