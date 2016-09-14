package me.saptarshidebnath.jwebsite.db.entity.page;

import me.saptarshidebnath.jwebsite.db.entity.page.metainfo.MetaInfoMapping;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.util.List;

@Entity
@Table(name = "web_page", schema = "jw")
public class WebPage {
  @Id
  @TableGenerator(
    name = "jwWebPageSeq",
    table = "web_page",
    schema = "seq",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "web_page_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwWebPageSeq")
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

  @OneToMany
  @JoinColumn(name = "meta_info_mapping", referencedColumnName = "id")
  private List<MetaInfoMapping> metaInfoMappingList;

  public long getId() {
    return this.id;
  }

  public WebPage setId(final long id) {
    this.id = id;
    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", this.id)
        .append("uri", this.uri)
        .append("title", this.title)
        .append("jspFileName", this.jspFileName)
        .toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof WebPage)) return false;

    final WebPage webPage = (WebPage) o;

    return new EqualsBuilder()
        .append(getId(), webPage.getId())
        .append(getUri(), webPage.getUri())
        .append(getTitle(), webPage.getTitle())
        .append(getJspFileName(), webPage.getJspFileName())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getUri())
        .append(getTitle())
        .append(getJspFileName())
        .toHashCode();
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

  public String getJspFileName() {
    return this.jspFileName;
  }

  public WebPage setJspFileName(final String jspFileName) {
    this.jspFileName = jspFileName;
    return this;
  }
}
