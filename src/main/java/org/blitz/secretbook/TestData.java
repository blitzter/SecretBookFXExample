package org.blitz.secretbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.blitz.secretbook.domain.*;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class TestData {

    public static void main(String[] args) throws JsonProcessingException {
        Page firstPage = new Page();
        firstPage.setPageType(PageType.MAP);
        firstPage.setLabel("testMapPage");
        MapPageData mapPageData = new MapPageData();
        Hashtable hashTable = new Hashtable<>();
        hashTable.putAll(Map.of("1", "1data", "2", "2data"));
        mapPageData.setPageData(hashTable);
        firstPage.setPageData(mapPageData);

        Page secondPage = new Page();
        secondPage.setPageType(PageType.TEXT);
        secondPage.setLabel("testTextPage");
        TextPageData textPageData = new TextPageData();
        textPageData.setPageData("Data\ndata\ndata\n");
        secondPage.setPageData(textPageData);

        Book book = new Book();
        book.setPages(List.of(firstPage, secondPage));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(book);
        System.out.println("writing = " + json);
        Book readBook = mapper.readValue(json, Book.class);
        String secondJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readBook);
        System.out.println("second = " + secondJson);
    }
}
