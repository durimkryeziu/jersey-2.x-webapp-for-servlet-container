package com.programmingskillz.providers;

import com.programmingskillz.repository.DataSource;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.DestroyListener;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author Durim Kryeziu
 */
@Provider
public class SampleApplicationDestroyListener implements DestroyListener {

    @Inject
    private javax.inject.Provider<ApplicationInfo> applicationInfoProvider;

    @Override
    public void onDestroy() {

        ApplicationInfo applicationInfo = applicationInfoProvider.get();

        DataSource.close();

        deregisterJdbcDrivers();

        System.out.println("Application '"
                + applicationInfo.getResourceConfig().getApplicationName()
                + "' destroyed.");
    }

    private void deregisterJdbcDrivers() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == classLoader) {
                try {
                    System.out.println(String.format("Deregistering JDBC driver: %s v%d.%d",
                            driver.getClass().getName(),
                            driver.getMajorVersion(),
                            driver.getMinorVersion()));

                    DriverManager.deregisterDriver(driver);
                } catch (SQLException e) {
                    System.out.println("Failed to deregister JDBC driver " + driver);
                    e.printStackTrace();
                }
            }
        }
    }
}