package me.saptarshidebnath.jwebsite.db.entity.website;

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
@Table(name = "jwebsite_config")
public class Config {
  @Id
  @TableGenerator(
    name = "jwSequence",
    table = "jwebsite_sequence",
    pkColumnName = "key",
    valueColumnName = "value",
    pkColumnValue = "jw_config_pk",
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "jwSequence")
  @Column(name = "id")
  private long id;

  @Column(name = "config_name", nullable = false)
  private String configName;

  @Column(name = "config_value", nullable = false)
  private String configValue;

  public long getId() {
    return this.id;
  }

  public Config setId(final long id) {
    this.id = id;
    return this;
  }

  public String getConfigName() {
    return this.configName;
  }

  public Config setConfigName(final String configName) {
    this.configName = configName;
    return this;
  }

  public String getConfigValue() {
    return this.configValue;
  }

  public Config setConfigValue(final String configValue) {
    this.configValue = configValue;
    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Config)) return false;

    final Config config = (Config) o;

    return new EqualsBuilder()
        .append(getId(), config.getId())
        .append(getConfigName(), config.getConfigName())
        .append(getConfigValue(), config.getConfigValue())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getConfigName())
        .append(getConfigValue())
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", this.id)
        .append("configName", this.configName)
        .append("configValue", this.configValue)
        .toString();
  }
}
