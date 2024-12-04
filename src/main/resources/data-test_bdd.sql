--ajout de données test

INSERT INTO status (designation) VALUES ('Active'), ('Inactive'), ('Suspended'), ('Pending'), ('Oh_no');

-- MDP = pizza
INSERT INTO user (email, password, status_id, admin) VALUES('user1@example.com', '$2a$10$7//eddhojFOHURstX01rJekfzGiJIckiLWuzvJ65XAEU.nyPSWrZK', 1, 1), ('user2@example.com', '$2a$10$7//eddhojFOHURstX01rJekfzGiJIckiLWuzvJ65XAEU.nyPSWrZK', 2, 0), ('user3@example.com', '$2a$10$7//eddhojFOHURstX01rJekfzGiJIckiLWuzvJ65XAEU.nyPSWrZK', 3, 0), ('user4@example.com', '$2a$10$7//eddhojFOHURstX01rJekfzGiJIckiLWuzvJ65XAEU.nyPSWrZK', 4, 0), ('user5@example.com', '$2a$10$7//eddhojFOHURstX01rJekfzGiJIckiLWuzvJ65XAEU.nyPSWrZK', 5, 0);

INSERT INTO skills (name) VALUES ('JavaScript'), ('Issue'), ('SQL'), ('HTML'), ('CSS'), ('Java'), ('C#'), ('Ruby'), ('PHP'), ('C++');

INSERT INTO user_skill (user_id, skills_id) VALUES (1, 6), (3, 2), (4, 9), (5, 10);