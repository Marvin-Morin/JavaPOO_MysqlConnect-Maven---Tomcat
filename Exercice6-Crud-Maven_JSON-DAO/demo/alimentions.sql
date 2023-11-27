-- --------------------------------------------------------
-- Code à COPIER/COLLER sur Laragon pour créer la BDD
-----------------------------------------------------------


-- Listage de la structure de la base pour alimentations
CREATE DATABASE IF NOT EXISTS `alimentations` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `alimentations`;

-- Listage de la structure de la table alimentations. aliments
CREATE TABLE IF NOT EXISTS `aliments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `poids_moyen` float NOT NULL,
  `calories` int(11) NOT NULL,
  `vitamines_C` float NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  `couleur_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `couleur_id` (`couleur_id`),
  CONSTRAINT `aliments_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type_aliment` (`id`),
  CONSTRAINT `aliments_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type_aliment` (`id`),
  CONSTRAINT `aliments_ibfk_3` FOREIGN KEY (`couleur_id`) REFERENCES `couleur` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;

-- Listage des données de la table alimentations.aliments : ~80 rows (environ)
DELETE FROM `aliments`;
/*!40000 ALTER TABLE `aliments` DISABLE KEYS */;
INSERT INTO `aliments` (`id`, `nom`, `poids_moyen`, `calories`, `vitamines_C`, `type_id`, `couleur_id`) VALUES
	 (1, 'Bigaradier', 0.15, 40, 45, 1, 6),
    (2, 'Banane', 0.12, 89, 8.7, 1, 6),
    (3, 'Orange', 0.13, 47, 53.2, 1, 9),
    (4, 'Fraise', 0.012, 32, 58.8, 1, 3),
    (5, 'Abricot', 0.035, 48, 10, 1, 2),
    (6, 'Ananas', 1.2, 50, 47.8, 1, 6),
    (7, 'Avocat', 0.2, 160, 10, 1, 13),
    (8, 'Cassis', 0.005, 63, 181, 1, 1),
    (9, 'Cerise', 0.008, 50, 7, 1, 3),
    (10, 'Citron', 0.1, 29, 53, 1, 6),
	(11, 'Clémentine', 0.08, 47, 0, 1, 4),
	(12, 'Coing', 0.3, 57, 0, 1, 6),
	(13, 'Datte', 0.02, 282, 0, 1, 4),
	(14, 'Figue', 0.05, 74, 0, 1, 14),
	(15, 'Framboise', 0.01, 53, 0, 1, 3),
	(16, 'Grenade', 0.2, 83, 0, 1, 3),
	(17, 'Groseille', 0.01, 56, 0, 1, 3),
	(18, 'Kiwi', 0.07, 61, 0, 1, 20),
	(19, 'Litchi', 0.02, 66, 0, 1, 5),
	(20, 'Mandarine', 0.08, 53, 0, 1, 7),
	(21, 'Mangue', 0.3, 60, 0, 1, 8),
	(22, 'Melon', 1, 34, 0, 1, 13),
	(23, 'Mirabelle', 0.02, 67, 0, 1, 6),
	(24, 'Mûre', 0.01, 43, 0, 1, 1),
	(25, 'Myrtille', 0.01, 57, 0, 1, 5),
	(26, 'Nectarine', 0.1, 44, 0, 1, 21),
	(27, 'Noix de coco', 1, 354, 0, 1, 11),
	(28, 'Olive', 0.02, 115, 0, 1, 13),
	(29, 'Pamplemousse', 0.3, 42, 0, 1, 6),
	(30, 'Papaye', 1, 43, 0, 1, 4),
	(31, 'Pastèque', 2, 30, 0, 1, 13),
	(32, 'Pêche', 0.15, 39, 0, 1, 4),
	(33, 'Poire', 0.2, 57, 0, 1, 13),
	(34, 'Pomelo', 0.25, 38, 0, 1, 6),
	(35, 'Prune', 0.04, 46, 0, 1, 14),
	(36, 'Raisin', 0.02, 0, 0, 1, 14),
	(64, 'Carotte', 0.1, 41, 0, 4, 5),
	(65, 'Brocoli', 0.3, 34, 0, 4, 5),
	(66, 'Pomme de terre', 0.2, 77, 19.7, 4, 11),
    (67, 'Tomate', 0.1, 18, 13.7, 4, 8),
    (68, 'Artichaut', 0.4, 47, 11.7, 4, 1),
    (69, 'Asperge', 0.05, 20, 5.6, 4, 45),
    (70, 'Aubergine', 0.25, 25, 2.2, 4, 6),
	(71, 'Betterave', 0.3, 43, 0, 4, 4),
	(72, 'Blette', 0.3, 19, 0, 4, 6),
	(73, 'Brocoli', 0.3, 34, 0, 4, 8),
	(74, 'Carotte', 0.1, 41, 0, 4, 22),
	(75, 'Céleri', 0.4, 16, 0, 4, 6),
	(76, 'Champignon', 0.05, 22, 0, 4, 2),
	(77, 'Chou-fleur', 1, 25, 0, 4, 1),
	(78, 'Chou de Bruxelles', 0.02, 43, 0, 4, 16),
	(79, 'Concombre', 0.2, 15, 0, 4, 20),
	(80, 'Courgette', 0.3, 17, 0, 4, 12),
	(81, 'Échalote', 0.05, 72, 0, 4, 5),
	(82, 'Endive', 0.2, 17, 0, 4, 16),
	(83, 'Épinard', 0.2, 23, 0, 4, 12),
	(84, 'Fenouil', 0.4, 31, 0, 4, 4),
	(85, 'Haricot vert', 0.05, 31, 0, 4, 5),
	(86, 'Laitue', 0.3, 15, 0, 4, 5),
	(87, 'Maïs', 1, 86, 0, 4, 21),
	(88, 'Navet', 0.2, 28, 0, 4, 8),
	(89, 'Oignon', 0.1, 40, 0, 4, 1),
	(90, 'Panais', 0.3, 75, 0, 4, 18),
	(91, 'Petit pois', 0.01, 81, 0, 4, 21),
	(92, 'Poireau', 0.3, 0, 0, 4, 9),
	(93, 'Poivron', 0.2, 0, 0, 4, 12),
	(94, 'Pomme de terre', 0.2, 0, 0, 4, 6),
	(95, 'Potiron', 2, 0, 0, 4, 5),
	(96, 'Radis', 0.02, 0, 0, 4, 20),
	(97, 'Tomate', 0.15, 0, 0, 4, 22),
	(127, 'Riz', 0.001, 1, 0, 2, 2),
	(133, 'Litchi Mur', 0.02, 66, 71.5, 1, 6),
    (134, 'Litchi pas beau', 0.02, 66, 71.5, 1, 6);
/*!40000 ALTER TABLE `aliments` ENABLE KEYS */;

-- Listage de la structure de la table alimentations. couleur
CREATE TABLE IF NOT EXISTS `couleur` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `hexadecimal_rvb` char(7) DEFAULT '#FFFFFF',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- Listage des données de la table alimentations.couleur : ~21 rows (environ)
DELETE FROM `couleur`;
/*!40000 ALTER TABLE `couleur` DISABLE KEYS */;
INSERT INTO `couleur` (`id`, `nom`, `hexadecimal_rvb`) VALUES
(1, 'Gris', '#808080'),
(2, 'Blanc', '#FFFFFF'),
(3, 'Rouge', '#FF0000'),
(4, 'Citron', '#FFF700'),
(5, 'Bleu RVB', '#0000FF'),
(6, 'Jaune', '#FFFF00'),
(7, 'Cyan / Aqua', '#00FFFF'),
(8, 'Magenta / Fuchsia', '#FF00FF'),
(9, 'Argent', '#C0C0C0'),
(11, 'Bordeaux', '#800000'),
(12, 'Olive', '#808000'),
(13, 'Vert', '#008000'),
(14, 'Violet', '#800080'),
(15, 'Sarcelle', '#008080'),
(16, 'Marine', '#000080'),
(20, 'Crème', '#FFFDD0'),
(22, 'Noir', '#000000'),
(45, 'Vermillion', '#E34234');


/*!40000 ALTER TABLE `couleur` ENABLE KEYS */;

-- Listage de la structure de la table alimentations. type_aliment
CREATE TABLE IF NOT EXISTS `type_aliment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Listage des données de la table alimentations.type_aliment : ~4 rows (environ)
DELETE FROM `type_aliment`;
/*!40000 ALTER TABLE `type_aliment` DISABLE KEYS */;
INSERT INTO `type_aliment` (`id`, `nom`) VALUES
	(1, 'fruit'),
	(2, 'céréale'),
	(3, 'condiment'),
	(4, 'légume');
/*!40000 ALTER TABLE `type_aliment` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
