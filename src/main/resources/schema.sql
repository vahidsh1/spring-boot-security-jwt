CREATE TABLE IF NOT EXISTS "USERS"
(
    "id"           int AUTO_INCREMENT PRIMARY KEY,
    "username"     VARCHAR(255) NOT NULL UNIQUE,
    "email"        VARCHAR(255) NOT NULL UNIQUE,
    "isFirstLogin" binary default true,
    "password"     VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS "ROLES"
(
    "id"   int AUTO_INCREMENT PRIMARY KEY,
    "name" VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS "USER_ROLES"
(
    "user_id" INT    NOT NULL,
    "role_id" BIGINT NOT NULL,
    FOREIGN KEY ("user_id") REFERENCES users ("id"),
    FOREIGN KEY ("role_id") REFERENCES ROLES ("id"),
    PRIMARY KEY ("user_id", "role_id")
);

