package com.programmingskillz.samplejerseywebapp.config.providers;

import com.programmingskillz.samplejerseywebapp.data.DataSource;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.DestroyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

/**
 * @author Durim Kryeziu
 */
@Provider
public class SampleApplicationDestroyListener implements DestroyListener {

  private static final Logger LOGGER
      = LoggerFactory.getLogger(SampleApplicationDestroyListener.class);

  @Inject
  private javax.inject.Provider<ApplicationInfo> applicationInfoProvider;

  @Override
  public void onDestroy() {

    ApplicationInfo applicationInfo = applicationInfoProvider.get();

    DataSource.getInstance().close();

    deregisterJdbcDrivers();

    LOGGER.info("Application '{}' destroyed.",
        applicationInfo.getResourceConfig().getApplicationName());
  }

  private void deregisterJdbcDrivers() {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    Enumeration<Driver> drivers = DriverManager.getDrivers();

    while (drivers.hasMoreElements()) {
      LOGGER.debug("Deregistering JDBC Drivers:");
      Driver driver = drivers.nextElement();
      if (driver.getClass().getClassLoader() == classLoader) {
        try {
          LOGGER.debug("  {} v{}.{}",
              driver.getClass().getName(),
              driver.getMajorVersion(),
              driver.getMinorVersion()
          );

          DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
          LOGGER.error("Failed to deregister JDBC driver: ", e);
        }
      }
    }
  }
}
