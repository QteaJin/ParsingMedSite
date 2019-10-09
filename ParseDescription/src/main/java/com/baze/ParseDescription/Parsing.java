package com.baze.ParseDescription;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface Parsing {

	XSSFWorkbook readExcelFile (String path);
	void saveExcelFile (Workbook book);
	List<String> parseMainHtmlPage(String link);
	String parseHtmlPage(String link);
	Workbook setContentToCell(Workbook book,String content, int rowNumber, int cellNumber);
	List<LinkData> getListOfLinksForParsing (Workbook book, char stopAt);
	
}
