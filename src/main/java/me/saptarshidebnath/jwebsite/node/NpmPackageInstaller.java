package me.saptarshidebnath.jwebsite.node;

import me.saptarshidebnath.jwebsite.db.JwDbEntityManager;
import me.saptarshidebnath.jwebsite.db.entity.website.JwConfig;
import me.saptarshidebnath.jwebsite.utils.Cnst;
import me.saptarshidebnath.jwebsite.utils.WebInstanceConstants;
import me.saptarshidebnath.jwebsite.utils.jlog.JLog;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NpmPackageInstaller {

  private static NpmPackageInstaller INSTANCE = null;

  private NpmPackageInstaller() throws IOException, InterruptedException {
    this.installAllPackagesMarkedInDataBase();
  }

  public static NpmPackageInstaller getInstance() throws IOException, InterruptedException {
    if (NpmPackageInstaller.INSTANCE == null) {
      synchronized (NpmPackageInstaller.class) {
        if (NpmPackageInstaller.INSTANCE == null) {
          NpmPackageInstaller.INSTANCE = new NpmPackageInstaller();
        }
      }
    }
    return NpmPackageInstaller.INSTANCE;
  }

  public int installAllPackagesMarkedInDataBase() throws IOException, InterruptedException {
    return this.installPackage(
        JwDbEntityManager.getInstance()
            .streamData(JwConfig.class)
            .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_NPM_PACKAGE_NAME))
            .map(e -> e.getConfigValue())
            .collect(Collectors.toList()));
  }

  private int installPackage(final List<String> packageName)
      throws IOException, InterruptedException {
    return this.installPackage(packageName.toArray(new String[packageName.size()]));
  }

  private int installPackage(final String... packageName) throws IOException, InterruptedException {
    final String installString =
        "npm install "
            + StringUtils.join(packageName, " ")
            + " --prefix "
            + WebInstanceConstants.INST.getValueFor(Cnst.WIC_KEY_ROOT_REAL_PATH)
            + File.separator
            + "WEB-INF";
    JLog.info("Running command : " + installString);
    final Process process = Runtime.getRuntime().exec(installString);
    new Thread(() -> this.readInputStream(process.getInputStream())).start();
    new Thread(() -> this.readInputStream(process.getErrorStream())).start();
    return process.waitFor();
  }

  private void readInputStream(final InputStream inputStream) {
    final Scanner sc = new Scanner(inputStream);
    while (sc.hasNext()) {
      JLog.info(sc.nextLine());
    }
  }
}
