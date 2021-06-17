package org.blitz.secretbook.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MapPageData.class, name = "mapPageData"),
        @JsonSubTypes.Type(value = TextPageData.class, name = "textPageData"),
})
@Data
@EqualsAndHashCode
public abstract class PageData {
}
