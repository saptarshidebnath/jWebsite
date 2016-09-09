package me.saptarshidebnath.jwebsite.db.entity.website;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.Map;

@Entity
@Table(name = "jwebsite_config")
public class Config {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @MapKeyColumn(name = "config_key")
  @Column(name = "config_value")
  private Map<String, String> configProperties;

  public long getId() {
    return this.id;
  }

  public Config setId(final long id) {
    this.id = id;
    return this;
  }

  public Map<String, String> getConfigProperties() {
    return this.configProperties;
  }

  public Config setConfigProperties(final Map<String, String> configProperties) {
    this.configProperties = configProperties;
    return this;
  }
}
