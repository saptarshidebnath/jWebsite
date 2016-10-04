package me.saptarshidebnath.jwebsite.db.entity.page;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "jw_meta_info")
public class MetaInfo {
  @Id
  @TableGenerator(
    name = "jwMetaInfoSeq",
    table = "seq_meta_info",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "meta_info_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwMetaInfoSeq")
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "content", nullable = false)
  private String content;

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", this.id)
        .append("name", this.name)
        .append("content", this.content)
        .toString();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof MetaInfo)) return false;

    final MetaInfo metaInfo = (MetaInfo) o;

    return new EqualsBuilder()
        .append(getId(), metaInfo.getId())
        .append(getName(), metaInfo.getName())
        .append(getContent(), metaInfo.getContent())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getName())
        .append(getContent())
        .toHashCode();
  }

  public long getId() {
    return this.id;
  }

  public MetaInfo setId(final long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return this.name;
  }

  public MetaInfo setName(final String name) {
    this.name = name;
    return this;
  }

  public String getContent() {
    return this.content;
  }

  public MetaInfo setContent(final String content) {
    this.content = content;
    return this;
  }
}
