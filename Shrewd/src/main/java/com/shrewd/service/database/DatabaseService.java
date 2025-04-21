package com.shrewd.service.database;
import com.shrewd.config.TenantContext;
import com.shrewd.config.tenant.TenantDataSourceProvider;
import com.shrewd.model.users.model.ROLES;
import com.shrewd.model.users.model.Role;
import com.shrewd.model.users.model.Users;
import com.shrewd.model.users.repository.UsersRepository;
import com.shrewd.model.users.repository.roles.RolesRepository;
import com.shrewd.security.communication.request.RegisterRequest;
import jakarta.transaction.Transactional;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final TenantDataSourceProvider tenantDataSourceProvider;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    public DatabaseService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, TenantDataSourceProvider tenantDataSourceProvider, UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.tenantDataSourceProvider = tenantDataSourceProvider;
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    private boolean isValidTenantName(String tenantId) {
        return tenantId == null || !tenantId.matches("^[a-zA-Z0-9_-]+$");
    }

    public Boolean createDatabaseForTenant(String tenantId) {
        if (isValidTenantName(tenantId)) {
            return false;
        }

        String createDatabaseQuery = String.format("CREATE DATABASE IF NOT EXISTS %s", tenantId);

        try {
            // https://www.digitalocean.com/community/tutorials/sql-injection-in-java
            jdbcTemplate.execute(createDatabaseQuery);
            System.out.println("\n\n\nCreated\n\n\n");
            return true;
        } catch (Exception e) {
            // Log the exception (use a proper logger instead of System.out.println)
            return false;
        }
    }

    public void deleteDatabaseForTenant(String tenantId) {
        if (isValidTenantName(tenantId)) {
            throw new IllegalArgumentException("Invalid tenant name: " + tenantId);
        }

        String dropDatabaseQuery = String.format("DROP DATABASE IF EXISTS org_%s", tenantId);

        try {
            jdbcTemplate.execute(dropDatabaseQuery);
            System.out.println("Database org_" + tenantId + " deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting database for tenant: " + tenantId);
            e.printStackTrace();
        }
    }

//    @Transactional
//    protected void initializeDatabase(JdbcTemplate jdbcTemplate, RegisterRequest registerRequest) {
//        try {
//            String createRolesTable = "CREATE TABLE IF NOT EXISTS roles ("
//                    + "role_id INT AUTO_INCREMENT PRIMARY KEY, "
//                    + "role_name VARCHAR(20) NOT NULL) ";
//
//            String createEmployeeTable = "CREATE TABLE IF NOT EXISTS users ("
//                    + "user_id BIGINT AUTO_INCREMENT PRIMARY KEY, "
//                    + "username VARCHAR(50) NOT NULL, "
//                    + "email VARCHAR(100) NOT NULL, "
//                    + "name VARCHAR(100), "
//                    + "phone VARCHAR(20), "
//                    + "password VARCHAR(120) NOT NULL, "
//                    + "role_id INT NOT NULL, "
//                    + "account_non_locked BOOLEAN NOT NULL, "
//                    + "account_non_expired BOOLEAN NOT NULL, "
//                    + "credentials_non_expired BOOLEAN NOT NULL, "
//                    + "enabled BOOLEAN NOT NULL, "
//                    + "credentials_expiry_date DATE, "
//                    + "account_expiry_date DATE, "
//                    + "created_date DATETIME DEFAULT CURRENT_TIMESTAMP, "
//                    + "updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, "
//                    + "FOREIGN KEY (role_id) REFERENCES roles(role_id))";
//
//            String insertRoles = "INSERT INTO roles (role_name) VALUES "
//                    + "('ORGANIZATION'), "
//                    + "('ADMIN'), "
//                    + "('HR'), "
//                    + "('MANAGER'), "
//                    + "('EMPLOYEE') "
//                    + "ON DUPLICATE KEY UPDATE role_name = role_name";  // Prevent duplicates
//
//            jdbcTemplate.execute(createRolesTable);
//            jdbcTemplate.execute(createEmployeeTable);
//            jdbcTemplate.update(insertRoles);
//
//            String insertEmployee = "INSERT INTO users (username, email, name, phone, password, role_id, "
//                    + "account_non_locked, account_non_expired, credentials_non_expired, enabled, "
//                    + "credentials_expiry_date, account_expiry_date) "
//                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//            jdbcTemplate.update(insertEmployee,
//                    registerRequest.getUsername(),
//                    registerRequest.getEmail(),
//                    registerRequest.getName(),
//                    registerRequest.getPhone(),
//                    passwordEncoder.encode(registerRequest.getPassword()),
//                    1, true, true, true, true, null, null
//            );
//
//
//            String createAttendanceTable = "CREATE TABLE IF NOT EXISTS attendance (" +
//                    "    id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL," +
//                    "    date DATE NOT NULL, clock_in TIME," +
//                    "    clock_out TIME, work_hours TIME, INDEX (user_id, date)" +
//                    ") ;";
//            jdbcTemplate.execute(createAttendanceTable);
//
//            System.out.println("Database schema initialized successfully.");
//
//        } catch (Exception e) {
//            throw new RuntimeException("Error initializing tenant database: " + e.getMessage(), e);
//        }
//    }

    public void triggerHibernateDDL(String tenantId) {
        TenantContext.setTenantId(tenantId); // critical: makes routingDataSource pick the new DS

        DataSource dataSource = tenantDataSourceProvider.getDataSource(tenantId);

        // Bootstrap EntityManagerFactory with hbm2ddl.auto=create
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(dataSource);
        emfBean.setPackagesToScan("com.shrewd.model.users");
        emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        jpaProperties.put(AvailableSettings.HBM2DDL_AUTO, "create");
        emfBean.setJpaPropertyMap(jpaProperties);

        emfBean.afterPropertiesSet(); // Initializes EMF and triggers schema generation
        emfBean.getObject().close();  // Optional: Close EMF if not needed further
    }

    public void createUsersForTenant(RegisterRequest registerRequest) {
        Users users = new Users(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );

        Role role = rolesRepository.findByRoleName(ROLES.ORGANIZATION)
                .orElseThrow(() -> new RuntimeException("Error: ORGANIZATION role not found."));;

        users.setName(registerRequest.getName());
        users.setPhone(registerRequest.getPhone());
        users.setAccountNonLocked(true);
        users.setAccountNonExpired(true);
        users.setCredentialsNonExpired(true);
        users.setEnabled(true);
        users.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        users.setAccountExpiryDate(LocalDate.now().plusYears(1));
        users.setRole(role);

        usersRepository.save(users);
    }
}
