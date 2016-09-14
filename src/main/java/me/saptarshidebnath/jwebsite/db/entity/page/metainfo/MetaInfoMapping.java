package me.saptarshidebnath.jwebsite.db.entity.page.metainfo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import java.util.List;

@Entity
@Table(name = "meta_info_mapping", schema = "jw")
public class MetaInfoMapping {
  @Id
  @TableGenerator(
    name = "jwMetaInfoMappingSeq",
    table = "meta_info_mapping",
    schema = "seq",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "meta_info_mapping_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwMetaInfoMappingSeq")
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "ref_web_page", nullable = false)
  private long refWebPage;

  @OneToMany
  @JoinColumn(name = "meta_info_id", referencedColumnName = "id")
  private List<MetaInfo> metaInfoList;

  public long getId() {
    return this.id;
  }

  public MetaInfoMapping setId(final long id) {
    this.id = id;
    return this;
  }

  public long getRefWebPage() {
    return this.refWebPage;
  }

  public MetaInfoMapping setRefWebPage(final long refWebPage) {
    this.refWebPage = refWebPage;
    return this;
  }

  public List<MetaInfo> getMetaInfoList() {
    return this.metaInfoList;
  }

  public MetaInfoMapping setMetaInfoList(final List<MetaInfo> metaInfoList) {
    this.metaInfoList = metaInfoList;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof MetaInfoMapping)) return false;

    final MetaInfoMapping that = (MetaInfoMapping) o;

    return new EqualsBuilder()
        .append(this.id, that.id)
        .append(this.refWebPage, that.refWebPage)
        .append(this.metaInfoList, that.metaInfoList)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(this.id)
        .append(this.refWebPage)
        .append(this.metaInfoList)
        .toHashCode();
  }
}
