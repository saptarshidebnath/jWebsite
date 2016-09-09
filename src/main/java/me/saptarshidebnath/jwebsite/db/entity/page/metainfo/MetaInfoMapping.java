package me.saptarshidebnath.jwebsite.db.entity.page.metainfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta_info_mapping")
public class MetaInfoMapping {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "ref_web_page", nullable = false)
  private long refWebPage;

  @Column(name = "ref_meta_info", nullable = false)
  private long refMetaInfo;

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

  public long getRefMetaInfo() {
    return this.refMetaInfo;
  }

  public MetaInfoMapping setRefMetaInfo(final long refMetaInfo) {
    this.refMetaInfo = refMetaInfo;
    return this;
  }
}
