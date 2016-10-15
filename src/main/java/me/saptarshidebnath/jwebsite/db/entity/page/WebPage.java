package me.saptarshidebnath.jwebsite.db.entity.page;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.util.List;

@Entity
@Table(
  name = "jw_web_page",
  indexes = {@Index(name = "indx_url_path", columnList = "url_path", unique = true)}
)
public class WebPage {
  @Id
  @TableGenerator(
    name = "jwWebPageSeq",
    table = "seq_web_page",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "web_page_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwWebPageSeq")
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "url_path", nullable = false, unique = true)
  @Lob
  private String urlPath;

  @Column(name = "title", nullable = false)
  @Lob
  private String title;

  @Column(name = "jsp_file_name")
  @Lob
  private String jspFileName;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "jw_web_page_id", referencedColumnName = "id")
  @OrderBy("createTime DESC")
  private List<HtmlContent> htmlContentList;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "jw_meta_info", referencedColumnName = "id")
  private List<MetaInfo> metaInfoList;

  public long getId() {
    return this.id;
  }

  public WebPage setId(final long id) {
    this.id = id;
    return this;
  }

  public List<HtmlContent> getHtmlContentList() {
    return this.htmlContentList;
  }

  public WebPage setHtmlContentList(final List<HtmlContent> htmlContentList) {
    this.htmlContentList = htmlContentList;
    return this;
  }

  public List<MetaInfo> getMetaInfoList() {
    return this.metaInfoList;
  }

  public WebPage setMetaInfoList(final List<MetaInfo> metaInfoList) {
    this.metaInfoList = metaInfoList;
    return this;
  }

  public String getUrlPath() {
    return this.urlPath;
  }

  public WebPage setUrlPath(final String urlPath) {
    this.urlPath = urlPath;
    return this;
  }

  public String getTitle() {
    return this.title;
  }

  public WebPage setTitle(final String title) {
    this.title = title;
    return this;
  }

  public String getJspFileName() {
    return this.jspFileName;
  }

  public WebPage setJspFileName(final String jspFileName) {
    this.jspFileName = jspFileName;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof WebPage)) return false;

    final WebPage webPage = (WebPage) o;

    return new EqualsBuilder()
        .append(getId(), webPage.getId())
        .append(getUrlPath(), webPage.getUrlPath())
        .append(getTitle(), webPage.getTitle())
        .append(getJspFileName(), webPage.getJspFileName())
        .append(getHtmlContentList(), webPage.getHtmlContentList())
        .append(getMetaInfoList(), webPage.getMetaInfoList())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getUrlPath())
        .append(getTitle())
        .append(getJspFileName())
        .append(getHtmlContentList())
        .append(getMetaInfoList())
        .toHashCode();
  }
}
