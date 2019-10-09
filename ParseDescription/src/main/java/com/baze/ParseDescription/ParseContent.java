package com.baze.ParseDescription;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ParseContent implements Runnable {
	private static final Logger LOGGER = Logger.getLogger(ParseContent.class.getName());

	private Parsing parse;
	private Workbook book;

	ParseContent(Parsing parse) {
		this.parse = parse;
		this.book = parse.readExcelFile("data.xlsx");
	}

	@Override
	public void run() {
		Sheet sheet = book.getSheet("BaseData");
		Document docPage;
		String content;
		int count = 0;
		java.util.Iterator<Row> itr = sheet.rowIterator();
		String link;
		int lastRowNum = sheet.getLastRowNum();
		while (itr.hasNext()) {
			link = itr.next().getCell(5).getStringCellValue();
			if (link.equals("no link")) {
				count++;
				continue;
			}

			try {
				docPage = readHtmlFile(link);
				content = getContent(docPage);
				parse.setContentToCell(book, content, count, 4);

				Thread.sleep(5000);
			} catch (Exception e) {

				e.printStackTrace();
				count++;
				continue;
			}

			if (count % 30 == 0) {
				parse.saveExcelFile(book);
				System.err.println("Saving to Excel ");
			}
			System.out.println("Row number =" + count + ". Left - " + (lastRowNum - count));

			count++;

		}

		parse.saveExcelFile(book);
		System.err.println("Saving to Excel last time");
		System.err.println("Program finished");

	}

	private static Document readHtmlFile(String link) {
		LOGGER.info("Start read html page ".concat(link));
		Document document = null;
		try {
			document = Jsoup.connect(link)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:64.0) Gecko/20100101 Firefox/64.0")
					.referrer("http://www.google.com").timeout(10000).get();
		} catch (IOException e) {
			LOGGER.warning("Error read html");
			e.printStackTrace();
		}
		LOGGER.info("Ends read html page ");
		return document;

	}

	private static String getContent(Document document) {
		LOGGER.info("Start parse html page ");
		StringBuffer sb = new StringBuffer();
		try {
			Elements elements = document.getElementsByAttributeValue("class", "content");
			if (elements.size() == 1) {
				Element elem = elements.get(0);
				Elements elementsP = elem.getElementsByTag("p");
				for (Element element : elementsP) {
					sb.append(element.text());
				}
			}
		} catch (Exception e) {
			LOGGER.warning("Error parse html");
			return "Error parsing document";
		}

		LOGGER.info("Ends parse html page ");
		return sb.toString();

	}

}
