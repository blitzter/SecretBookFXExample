package org.blitz.secretbook.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Hashtable;

@Data
@EqualsAndHashCode(callSuper = true)
public class MapPageData extends PageData {

    Hashtable<String, String> pageData = new Hashtable();

}
