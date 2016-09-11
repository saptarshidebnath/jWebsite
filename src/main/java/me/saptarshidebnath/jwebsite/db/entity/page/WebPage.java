package me.saptarshidebnath.jwebsite.db.entity.page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "web_page")
public class WebPage {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "uri", nullable = false)
  @Lob
  private String uri;

  @Column(name = "title", nullable = false)
  @Lob
  private String title;

  @Column(name = "jsp_file_name", nullable = false)
  @Lob
  private String jspFileName;

  public long getId() {
    return this.id;
  }

  public WebPage setId(final long id) {
    this.id = id;
    return this;
  }

  public String getJspFileName() {
    return this.jspFileName;
  }

  public WebPage setJspFileName(final String jspFileName) {
    this.jspFileName = jspFileName;
    return this;
  }

  public long getPageId() {
    return this.id;
  }

  public WebPage setPageId(final long pageId) {
    this.id = pageId;
    return this;
  }

  public String getUri() {
    return this.uri;
  }

  public WebPage setUri(final String uri) {
    this.uri = uri;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public WebPage setTitle(final String title) {
    this.title = title;
    return this;
  }
}
