CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

INSERT INTO users (id, username, email, password, role) VALUES
('bdac0e1e-76fb-408a-8355-915c3f036bc3', 'admin', 'admin@example.com', '$2a$10$oHp4CiVDLvSCTXtHWWu3yOxfIsdL.EHuu0vk/WexLOAk7/Ut3fLri', 'admin'),
('3a2b9310-bdfa-4fd9-b58b-6cfc254fa3b9', 'user', 'user@example.com', '$2a$10$vs4OoNX6DHDMaTbGyN1Kd.JATTwlFvz7dY9DCpdaRjPB6X5fDB67K', 'user');