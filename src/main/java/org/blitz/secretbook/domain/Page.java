package org.blitz.secretbook.domain;

import lombok.Data;

@Data
public class Page {
    private String label;
    private PageType pageType;
    private PageData pageData;
}
