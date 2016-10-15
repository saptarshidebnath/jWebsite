package me.saptarshidebnath.jwebsite.db.status;

import me.saptarshidebnath.jwebsite.utils.Utils;

import java.util.Arrays;
import java.util.List;

/** Enum to denote the state of the {@link me.saptarshidebnath.jwebsite.db.entity.page.WebPage} */
public enum PageContent {
  /** Web Page Status as Saved */
  SAVED("saved"),

  /** Web page status as published */
  PUBLISHED("published");
  /** private value store */
  private final String value;

  /**
   * The private constructor to store value
   *
   * @param value
   */
  private PageContent(final String value) {
    this.value = value.toLowerCase();
  }

  public static PageContent getPageContentForValue(final String value) {
    final List<PageContent> allValues = Arrays.asList(PageContent.values());
    return allValues
        .stream()
        .filter(e -> e.toString().equals(value.toLowerCase()))
        .collect(Utils.singletonCollector());
  }

  /**
   * String value of the entity
   *
   * @return
   */
  @Override
  public String toString() {
    return this.value;
  }
}
