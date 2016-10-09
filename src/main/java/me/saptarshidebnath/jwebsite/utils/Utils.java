package me.saptarshidebnath.jwebsite.utils;

import me.saptarshidebnath.jwebsite.utils.jlog.JLog;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static me.saptarshidebnath.jwebsite.utils.Cnst.ENV_DATABASE_URL;

/** Created by Saptarshi on 8/27/2016. */
public class Utils {

  public static void writeFile(final File fileToWrite, final String contentToWrite)
      throws IOException {
    Files.write(Paths.get(fileToWrite.getAbsolutePath()), contentToWrite.getBytes());
  }

  public static <T> ArrayList<T> getAsArrayList(final T... array) {
    final int arrayLength = array.length;
    final ArrayList<T> list = new ArrayList<>(arrayLength);
    for (int i = 0; i < arrayLength; i++) {
      list.add(i, array[i]);
    }
    return list;
  }

  public static <T> Collector<T, ?, T> singletonCollector() {
    return Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
          if (list.size() != 1) {
            throw new IllegalStateException(
                "List size is : " + list.size() + ". Ideally it " + "should be 1");
          }
          return list.get(0);
        });
  }

  public static String digestStringAsMd5(final String clearText) throws NoSuchAlgorithmException {
    final MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(StandardCharsets.UTF_8.encode(clearText));
    return String.format("%032x", new BigInteger(1, md5.digest()));
  }

  public static boolean doRequestContainsAttribute(
      final HttpServletRequest request, final String attributeName) {
    return request.getAttribute(attributeName) == null ? false : true;
  }

  public static HttpServletRequest setAttribute(
      final HttpServletRequest request, final String attributeName, final Object value) {
    request.setAttribute(attributeName, value);
    return request;
  }

  public static Map<String, String> getHerokuPostgresDBDetails(
      final String vendorName, final String tokenizer) throws NoSuchAlgorithmException {
    final String databaseUrl = System.getenv(ENV_DATABASE_URL);
    //    final String databaseUrl = "postgres://vagrant:vagrant@localhost:5432/vagrant";
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

    JLog.info("Environment provided password [MD5 hashed] : " + digestStringAsMd5(password));
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
