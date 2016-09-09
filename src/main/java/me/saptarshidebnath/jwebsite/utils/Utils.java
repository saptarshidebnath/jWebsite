package me.saptarshidebnath.jwebsite.utils;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static me.saptarshidebnath.jwebsite.utils.Constants.ENV_DATABASE_URL;

/** Created by Saptarshi on 8/27/2016. */
public class Utils {

  public static Map<String, String> getHerokuPostgresDBDetails(
      final String vendorName, final String tokenizer) throws NoSuchAlgorithmException {
    final String databaseUrl = System.getenv(ENV_DATABASE_URL);
    final StringTokenizer st = new StringTokenizer(databaseUrl, tokenizer);
    final String dbVendor = st.nextToken(); //if DATABASE_URL is set
    final String userName = st.nextToken();
    final String password = st.nextToken();
    final String host = st.nextToken();
    final String port = st.nextToken();
    final String databaseName = st.nextToken();
    //?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory
    final String jdbcUrl =
        String.format("jdbc:" + vendorName + "://%s:%s/%s", host, port, databaseName);
    JLog.info("Reconstructed JDBC URL : " + jdbcUrl);
    JLog.info("Environment provided vendor : " + dbVendor);
    JLog.info("Environment provided userName : " + userName);
    final MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(StandardCharsets.UTF_8.encode(password));
    JLog.info(
        "Environment provided password [MD5 hashed] : "
            + String.format("%032x", new BigInteger(1, md5.digest())));
    JLog.info("Environment provided host name : " + host);
    JLog.info("Environment provided port Number : " + port);
    JLog.info("Environment provided data base name : " + databaseName);
    final Map<String, String> properties = new HashMap<>();
    properties.put("javax.persistence.jdbc.url", jdbcUrl);
    properties.put("javax.persistence.jdbc.user", userName);
    properties.put("javax.persistence.jdbc.password", password);
    return properties;
  }
}
