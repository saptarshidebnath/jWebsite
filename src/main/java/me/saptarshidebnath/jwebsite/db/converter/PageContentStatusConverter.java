package me.saptarshidebnath.jwebsite.db.converter;

import me.saptarshidebnath.jwebsite.db.status.PageContent;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/** Converts the {@link PageContent} to and from {@link String} for the java data base entry */
@Converter
public class PageContentStatusConverter implements AttributeConverter<PageContent, String> {
  @Override
  public String convertToDatabaseColumn(final PageContent pageContent) {
    return pageContent.toString();
  }

  @Override
  public PageContent convertToEntityAttribute(final String pageContentValue) {
    return PageContent.getPageContentForValue(pageContentValue);
  }
}
