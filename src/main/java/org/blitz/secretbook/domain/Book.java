package org.blitz.secretbook.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Book {
    List<Page> pages = new ArrayList<>();
}
