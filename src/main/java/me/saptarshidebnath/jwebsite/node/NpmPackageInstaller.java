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

import static me.saptarshidebnath.jwebsite.utils.Cnst.WIC_KEY_NODE_INST_DIR;

public class NpmPackageInstaller {

  private static NpmPackageInstaller INSTANCE = null;
  private final File installationDir;

  private NpmPackageInstaller() throws IOException, InterruptedException {

    this.installationDir = new File(WebInstanceConstants.INST.getValueFor(WIC_KEY_NODE_INST_DIR));
    if (!this.installationDir.exists()) {
      if (this.installationDir.mkdirs() == false) {
        final String message =
            "Unable to create node app package directory : "
                + this.installationDir.getCanonicalPath();
        JLog.severe(message);
        throw new IOException(message);
      } else {
        JLog.info(
            "Node app package directory created : " + this.installationDir.getCanonicalPath());
      }
    }
    //
    //Install package provided
    //
    this.installPackageJson();
    //
    // Install package marked in the data base
    //
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

  private Process executeNpmCommand(final String installationCommand) throws IOException {
    final Process process =
        Runtime.getRuntime()
            .exec(
                installationCommand,
                null,
                new File(WebInstanceConstants.INST.getValueFor(WIC_KEY_NODE_INST_DIR)));
    //
    //Log the output
    //
    new Thread(() -> this.readInputStream("OUT-", process.getInputStream())).start();
    new Thread(() -> this.readInputStream("ERR-", process.getErrorStream())).start();
    return process;
  }

  public int installAllPackagesMarkedInDataBase() throws IOException, InterruptedException {
    JLog.info("Installing user packages from database");
    return this.installPackage(
        JwDbEntityManager.getInstance()
            .streamData(JwConfig.class)
            .filter(e -> e.getConfigName().equalsIgnoreCase(Cnst.DB_CONFIG_KEY_NPM_PACKAGE_NAME))
            .map(e -> e.getConfigValue())
            .collect(Collectors.toList()));
  }

  private int installPackageJson() throws IOException, InterruptedException {
    JLog.info("Installing user packages from package.json");
    return this.installPackage(new String[] {""});
  }

  private int installPackage(final List<String> packageName)
      throws IOException, InterruptedException {
    return this.installPackage(packageName.toArray(new String[packageName.size()]));
  }

  private int installPackage(final String... packageName) throws IOException, InterruptedException {
    JLog.info("Beginning npm package installation");
    final String installString = ("npm install " + StringUtils.join(packageName, " ")).trim();
    JLog.info("Running command : " + installString);
    final int returnCode = executeNpmCommand(installString).waitFor();
    if (returnCode == 0) {
      executeNpmCommand("npm run --list").waitFor();
    }
    return returnCode;
  }

  private void readInputStream(final String message, final InputStream inputStream) {
    final Scanner sc = new Scanner(inputStream);
    while (sc.hasNextLine()) {
      JLog.info(message + sc.nextLine());
    }
  }
}
