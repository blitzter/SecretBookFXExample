package org.blitz.secretbook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TextPageData extends PageData {

    private String pageData;

}
