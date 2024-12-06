-- Insertion des données dans la table droit
INSERT INTO droit (nom) VALUES
('employe'), -- ID 1
('redacteur'), -- ID 2
('admin'); -- ID 3

-- Insertion des données dans utilisateur avec la référence au droit
--  MDP pizza
INSERT INTO utilisateur (pseudo, password, droit_id) VALUES
('employe1', '$2a$12$fiA8HkCrdlETEP3kWm6J4.VxJagRwQ7WCYk9..w0lNpmhqu8SCwlq', 1),  -- ID 1 pour employe
('redacteur1', '$2a$12$fiA8HkCrdlETEP3kWm6J4.VxJagRwQ7WCYk9..w0lNpmhqu8SCwlq', 2),  -- ID 2 pour redacteur
('admin1', '$2a$12$fiA8HkCrdlETEP3kWm6J4.VxJagRwQ7WCYk9..w0lNpmhqu8SCwlq', 3),  -- ID 3 pour admin
('employe2', '$2a$12$fiA8HkCrdlETEP3kWm6J4.VxJagRwQ7WCYk9..w0lNpmhqu8SCwlq', 1),  -- ID 1 pour employe
('redacteur2', '$2a$12$fiA8HkCrdlETEP3kWm6J4.VxJagRwQ7WCYk9..w0lNpmhqu8SCwlq', 2);  -- ID 2 pour redacteur

-- Insertion des données dans priorite
INSERT INTO priorite (nom) VALUES
('Haute'),
('Moyenne'),
('Basse');

-- Insertion des données dans tache
INSERT INTO tache (description, nom, valide, priorite_id, utilisateur_id) VALUES
('Rédiger un rapport mensuel', 'Rapport Mensuel', 0, 3, 3), -- Assignée à redacteur1 avec priorité Moyenne
('Mettre à jour le site web', 'Mise à jour site', 1, 1, 5), -- Validée par admin1 avec priorité Haute
('Organiser une réunion d\'équipe', 'Réunion équipe', 0, 2, 2), -- Assignée à employe1 avec priorité Basse
('Vérifier les stocks', 'Stocks', 0, 1, 5), -- Assignée à employe2 avec priorité Moyenne
('Créer un plan marketing', 'Plan Marketing', 0, 1, 2); -- Assignée à redacteur2 avec priorité Haute
