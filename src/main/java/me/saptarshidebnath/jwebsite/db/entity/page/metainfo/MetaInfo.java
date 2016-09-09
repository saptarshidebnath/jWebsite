package me.saptarshidebnath.jwebsite.db.entity.page.metainfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta_info")
public class MetaInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "content", nullable = false)
  private String content;

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
