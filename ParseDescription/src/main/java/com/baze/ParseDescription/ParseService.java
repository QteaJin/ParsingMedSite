package com.baze.ParseDescription;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseService implements Parsing {
	private static final Logger LOGGER = Logger.getLogger(ParseService.class.getName());

	public XSSFWorkbook readExcelFile(String path) {
		LOGGER.info("Start reading file");
		XSSFWorkbook myExcelBook = new XSSFWorkbook();
		try (FileInputStream fis = new FileInputStream(path)) {
			myExcelBook = new XSSFWorkbook(fis);

		} catch (Exception e) {
			LOGGER.warning("Error reading file " + e.getMessage());

		}
		LOGGER.info("Ends reading file");
		return myExcelBook;
	}

	public void saveExcelFile(Workbook book) {
		LOGGER.info("Start saving to file");
		File file = new File("data.xlsx");
		try {
			book.write(new FileOutputStream(file));
		} catch (IOException e) {
			LOGGER.warning("Error saving file " + e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("Ends saving to file");
	}

	public List<String> parseMainHtmlPage(String link) { //http://ruslekar.info/E1xx-Krasiteli.html
		LOGGER.info("Start parce main page ".concat(link));
		List<String> linkList = new ArrayList<String>();
		File input = new File(link);
		Document doc;
		try {
			doc = Jsoup.parse(input, "UTF-8");
			Elements elements = doc.getElementsByAttributeValue("class", "content");
			if (elements.size() == 1) {
				Elements elementsLi = elements.get(0).getElementsByTag("li");
				for (Element element : elementsLi) {
					linkList.add(element.childNode(1).attr("href"));
					System.out.println(element.childNode(1).attr("href"));
				}
			}
		} catch (IOException e) {
			LOGGER.warning("Error parsing HTML " + e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("Stop parce main page ".concat(link).concat(" size ") + linkList.size());

		return linkList;
	}

	public String parseHtmlPage(String link) {
		// TODO Auto-generated method stub
		return null;
	}

	public Workbook setContentToCell(Workbook book, String content, int rowNumber, int cellNumber) {
		
		Sheet sheet = book.getSheet("BaseData");
		Row row = sheet.getRow(rowNumber);
		Cell cell = row.getCell(cellNumber);
		cell.setCellValue(content);
		
		return book;
	}

	@Override
	public List<LinkData> getListOfLinksForParsing(Workbook book, char stopAt) {
		List<LinkData> activeLinks = new ArrayList<>();
		List<String> links = parseMainHtmlPage("D:/Temp/temp/e1xx.html"); // временно
		Sheet sheet = book.getSheet("BaseData");
		int rowNum = 0;
		Cell cell0;
		String valueCell0;
		while (sheet.rowIterator().hasNext()) {
			
			if(sheet.getRow(rowNum) == null) {
				break;
			}
			
			cell0 = sheet.getRow(rowNum).getCell(0);
			valueCell0 = cell0.getStringCellValue().replace("Е", "E");
			
			for (String link : links) {
				if(link.contains(valueCell0)) {
					LinkData linkData = new LinkData(rowNum, link);
					activeLinks.add(linkData);
				}
			}

			rowNum++;
		}
		return activeLinks;
	}

}
