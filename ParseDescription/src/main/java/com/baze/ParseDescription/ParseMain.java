package com.baze.ParseDescription;


import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public class ParseMain 
{
    public static void main( String[] args )
    {
      ParseService psService = new ParseService();
      Workbook book = psService.readExcelFile("data.xlsx");
//      List<LinkData> myLinkList =  psService.getListOfLinksForParsing(book, '1');
//      
//      for (LinkData linkData : myLinkList) {
//    	  book = psService.setContentToCell(book, linkData.getLink(), linkData.getRowNumber(), 5);
//	}
//      psService.saveExcelFile(book);
//      new ParseContent(psService).run();

    }
}
