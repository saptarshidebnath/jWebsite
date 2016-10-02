package me.saptarshidebnath.jwebsite.db.entity.page.metainfo;

import javax.persistence.CascadeType;
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
@Table(name = "jw_meta_info_mapping")
public class MetaInfoMapping {
  @Id
  @TableGenerator(
    name = "jwMetaInfoMappingSeq",
    table = "seq_meta_info_mapping",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "meta_info_mapping_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwMetaInfoMappingSeq")
  @Column(name = "id", nullable = false)
  private long id;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "meta_info_id", referencedColumnName = "id")
  private List<MetaInfo> metaInfoList;

  public long getId() {
    return this.id;
  }

  public MetaInfoMapping setId(final long id) {
    this.id = id;
    return this;
  }

  public List<MetaInfo> getMetaInfoList() {
    return this.metaInfoList;
  }

  public MetaInfoMapping setMetaInfoList(final List<MetaInfo> metaInfoList) {
    this.metaInfoList = metaInfoList;
    return this;
  }
}
