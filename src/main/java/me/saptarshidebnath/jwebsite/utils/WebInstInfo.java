package me.saptarshidebnath.jwebsite.utils;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import java.util.HashMap;

/** Created by saptarshi on 10/3/2016. */
public enum WebInstInfo {
  INST;

  private final HashMap<String, String> cache;

  private WebInstInfo() {
    this.cache = new HashMap<>();
  }

  public String getValueFor(final String key) {
    final String returnValue = this.cache.get(key);
    JLog.info("Replying back with : " + returnValue);
    return returnValue;
  }

  public void storeValue(final String forKey, final String value) {
    JLog.info(forKey + " : " + value);
    //
    // Check if key exists or not in a synchronized fashion
    //
    synchronized (this.cache) {
      if (this.cache.containsKey(forKey)) {
        final String currentValue = this.cache.get(forKey);
        JLog.warning(
            "Key \""
                + forKey
                + "\" already present in cache with value \""
                + currentValue
                + "\" . The current value \""
                + currentValue
                + "\" will be replaced with the new "
                + "value \""
                + "\"");
      }
      this.cache.put(forKey, value);
    }
  }
}
