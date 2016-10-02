package me.saptarshidebnath.jwebsite.db.entity.page;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/** Created by saptarshi on 10/1/2016. */
@Entity
@Table(name = "jw_html_content")
public class HtmlContent {

  @Column(name = "create_time", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime;

  @Id
  @TableGenerator(
    name = "jwHtmlContentSeq",
    table = "seq_html_content",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "html_content_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwHtmlContentSeq")
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "html_content", nullable = false)
  @Lob
  private String htmlContent;

  public Date getCreateTime() {
    return this.createTime;
  }

  public HtmlContent setCreateTime(final Date createTime) {
    if (createTime != null) {
      this.createTime = createTime;
    } else {
      JLog.warning("Received input is null. Setting current time as default create time");
      this.createTime = new Date();
    }

    return this;
  }

  public long getId() {
    return this.id;
  }

  public HtmlContent setId(final long id) {
    this.id = id;
    return this;
  }

  public String getHtmlContent() {
    return this.htmlContent;
  }

  public HtmlContent setHtmlContent(final String htmlContent) {
    this.htmlContent = htmlContent;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof HtmlContent)) return false;

    final HtmlContent that = (HtmlContent) o;

    return new EqualsBuilder()
        .append(getId(), that.getId())
        .append(getCreateTime(), that.getCreateTime())
        .append(getHtmlContent(), that.getHtmlContent())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getCreateTime())
        .append(getId())
        .append(getHtmlContent())
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("createTime", this.createTime)
        .append("id", this.id)
        .append("htmlContent", this.htmlContent)
        .toString();
  }
}
