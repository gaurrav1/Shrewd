CREATE TABLE IF NOT EXISTS roles (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employee (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    name VARCHAR(100),
    phone VARCHAR(20),
    password VARCHAR(120) NOT NULL,
    role_id INT NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    credentials_expiry_date DATE,
    account_expiry_date DATE,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);


INSERT INTO roles (role_name, description) VALUES
    ('ORGANIZATION', 'Handles the overall organization management'),
    ('ADMIN', 'Manages users, settings, and policies'),
    ('HR', 'Handles employee relations and payroll'),
    ('MANAGER', 'Oversees teams and operations'),
    ('EMPLOYEE', 'Works on the tasks assigned by managers');