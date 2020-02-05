-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: tpjad_courses_management
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `maxStudents` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `year` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'Introducere in conceptele folosite in bazele de date: baze de date relationale, tabele, proceduri si functii stocate.',200,'Baze de date','2020'),(2,'Concepte de algoritmica (backtracking, divide et impera, greedy, sortari, cautari) si introducere in limbajul Python.',200,'Fundamentele programarii','2020'),(3,'Elemente de aritmetica modulara si criptografie.',60,'Criptografie','2019');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_management`
--

DROP TABLE IF EXISTS `courses_management`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_management` (
  `user_id` bigint(20) NOT NULL,
  `course_id` bigint(20) NOT NULL,
  KEY `FKi8ny36shyc7nuqlgpxqjjbqey` (`course_id`),
  KEY `FK9ncegjmu2g59cui27j2iwhhi5` (`user_id`),
  CONSTRAINT `FK9ncegjmu2g59cui27j2iwhhi5` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKi8ny36shyc7nuqlgpxqjjbqey` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_management`
--

LOCK TABLES `courses_management` WRITE;
/*!40000 ALTER TABLE `courses_management` DISABLE KEYS */;
INSERT INTO `courses_management` VALUES (7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,2),(32,2),(33,2),(34,2),(35,2),(36,2),(37,2),(38,2),(39,2),(40,2),(41,2),(42,2),(43,2),(44,2),(45,2),(46,2),(47,2),(48,2),(49,2),(50,2),(51,2),(52,2),(53,2),(54,2),(55,2),(56,2),(57,2),(58,2),(59,2),(60,2),(61,2),(62,2),(63,2),(64,2),(65,2),(66,2),(67,2),(68,2),(69,2),(70,3),(71,3),(72,3),(73,3),(74,3),(75,3),(76,3),(77,3),(78,3),(79,3),(80,3),(81,3),(82,3),(83,3),(84,3),(85,3),(86,3),(87,3),(88,3),(89,3),(90,3),(91,3),(92,3),(93,3),(94,3),(95,3),(96,3),(97,3),(98,3),(99,3),(100,3),(101,3),(102,3),(103,3),(104,3),(105,3),(106,3),(1,1),(3,2),(2,3);
/*!40000 ALTER TABLE `courses_management` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lectures`
--

DROP TABLE IF EXISTS `lectures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lectures` (
  `lecture_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attachment` mediumblob,
  `date` date DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `course_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`lecture_id`),
  KEY `FKsj4m8ipr4qnehoyxk7kbu3ide` (`course_id`),
  CONSTRAINT `FKsj4m8ipr4qnehoyxk7kbu3ide` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lectures`
--

LOCK TABLES `lectures` WRITE;
/*!40000 ALTER TABLE `lectures` DISABLE KEYS */;
INSERT INTO `lectures` VALUES (1,NULL,'2020-01-13',NULL,'Baze de date realtionale',1),(2,NULL,'2020-01-20',NULL,'Tabele',1),(3,NULL,'2019-10-20',NULL,'Introducere in Python',2),(4,NULL,'2019-10-27',NULL,'Cautari',2),(5,NULL,'2019-10-05',NULL,'RSA',3);
/*!40000 ALTER TABLE `lectures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `picture` mediumblob,
  `role` int(11) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'popescu@cs.ubbcluj.ro','Ioan','password',NULL,0,'Popescu'),(2,'admin@cs.ubbcluj.ro','Admin','admin',NULL,0,'Admin'),(3,'frasinariu@cs.ubbcluj.ro','Mihai','password',NULL,0,'Frasinariu'),(4,'casd1924@scs.ubbcluj.ro','Andreea','password',NULL,1,'Ciforac'),(5,'casd1927@scs.ubbcluj.ro','Alexandru','password',NULL,1,'Cosarca'),(6,'ccsd1930@scs.ubbcluj.ro','Claudia','password',NULL,1,'Coste'),(7,'email1@scs.ubbcluj.ro','Prenume1','password',NULL,1,'Nume'),(8,'email2@scs.ubbcluj.ro','Prenume2','password',NULL,1,'Nume'),(9,'email3@scs.ubbcluj.ro','Prenume3','password',NULL,1,'Nume'),(10,'email4@scs.ubbcluj.ro','Prenume4','password',NULL,1,'Nume'),(11,'email5@scs.ubbcluj.ro','Prenume5','password',NULL,1,'Nume'),(12,'email6@scs.ubbcluj.ro','Prenume6','password',NULL,1,'Nume'),(13,'email7@scs.ubbcluj.ro','Prenume7','password',NULL,1,'Nume'),(14,'email8@scs.ubbcluj.ro','Prenume8','password',NULL,1,'Nume'),(15,'email9@scs.ubbcluj.ro','Prenume9','password',NULL,1,'Nume'),(16,'email10@scs.ubbcluj.ro','Prenume10','password',NULL,1,'Nume'),(17,'email11@scs.ubbcluj.ro','Prenume11','password',NULL,1,'Nume'),(18,'email12@scs.ubbcluj.ro','Prenume12','password',NULL,1,'Nume'),(19,'email13@scs.ubbcluj.ro','Prenume13','password',NULL,1,'Nume'),(20,'email14@scs.ubbcluj.ro','Prenume14','password',NULL,1,'Nume'),(21,'email15@scs.ubbcluj.ro','Prenume15','password',NULL,1,'Nume'),(22,'email16@scs.ubbcluj.ro','Prenume16','password',NULL,1,'Nume'),(23,'email17@scs.ubbcluj.ro','Prenume17','password',NULL,1,'Nume'),(24,'email18@scs.ubbcluj.ro','Prenume18','password',NULL,1,'Nume'),(25,'email19@scs.ubbcluj.ro','Prenume19','password',NULL,1,'Nume'),(26,'email20@scs.ubbcluj.ro','Prenume20','password',NULL,1,'Nume'),(27,'email21@scs.ubbcluj.ro','Prenume21','password',NULL,1,'Nume'),(28,'email22@scs.ubbcluj.ro','Prenume22','password',NULL,1,'Nume'),(29,'email23@scs.ubbcluj.ro','Prenume23','password',NULL,1,'Nume'),(30,'email24@scs.ubbcluj.ro','Prenume24','password',NULL,1,'Nume'),(31,'email25@scs.ubbcluj.ro','Prenume25','password',NULL,1,'Nume'),(32,'email26@scs.ubbcluj.ro','Prenume26','password',NULL,1,'Nume'),(33,'email27@scs.ubbcluj.ro','Prenume27','password',NULL,1,'Nume'),(34,'email28@scs.ubbcluj.ro','Prenume28','password',NULL,1,'Nume'),(35,'email29@scs.ubbcluj.ro','Prenume29','password',NULL,1,'Nume'),(36,'email30@scs.ubbcluj.ro','Prenume30','password',NULL,1,'Nume'),(37,'email31@scs.ubbcluj.ro','Prenume31','password',NULL,1,'Nume'),(38,'email32@scs.ubbcluj.ro','Prenume32','password',NULL,1,'Nume'),(39,'email33@scs.ubbcluj.ro','Prenume33','password',NULL,1,'Nume'),(40,'email34@scs.ubbcluj.ro','Prenume34','password',NULL,1,'Nume'),(41,'email35@scs.ubbcluj.ro','Prenume35','password',NULL,1,'Nume'),(42,'email36@scs.ubbcluj.ro','Prenume36','password',NULL,1,'Nume'),(43,'email37@scs.ubbcluj.ro','Prenume37','password',NULL,1,'Nume'),(44,'email38@scs.ubbcluj.ro','Prenume38','password',NULL,1,'Nume'),(45,'email39@scs.ubbcluj.ro','Prenume39','password',NULL,1,'Nume'),(46,'email40@scs.ubbcluj.ro','Prenume40','password',NULL,1,'Nume'),(47,'email41@scs.ubbcluj.ro','Prenume41','password',NULL,1,'Nume'),(48,'email42@scs.ubbcluj.ro','Prenume42','password',NULL,1,'Nume'),(49,'email43@scs.ubbcluj.ro','Prenume43','password',NULL,1,'Nume'),(50,'email44@scs.ubbcluj.ro','Prenume44','password',NULL,1,'Nume'),(51,'email45@scs.ubbcluj.ro','Prenume45','password',NULL,1,'Nume'),(52,'email46@scs.ubbcluj.ro','Prenume46','password',NULL,1,'Nume'),(53,'email47@scs.ubbcluj.ro','Prenume47','password',NULL,1,'Nume'),(54,'email48@scs.ubbcluj.ro','Prenume48','password',NULL,1,'Nume'),(55,'email49@scs.ubbcluj.ro','Prenume49','password',NULL,1,'Nume'),(56,'email50@scs.ubbcluj.ro','Prenume50','password',NULL,1,'Nume'),(57,'email51@scs.ubbcluj.ro','Prenume51','password',NULL,1,'Nume'),(58,'email52@scs.ubbcluj.ro','Prenume52','password',NULL,1,'Nume'),(59,'email53@scs.ubbcluj.ro','Prenume53','password',NULL,1,'Nume'),(60,'email54@scs.ubbcluj.ro','Prenume54','password',NULL,1,'Nume'),(61,'email55@scs.ubbcluj.ro','Prenume55','password',NULL,1,'Nume'),(62,'email56@scs.ubbcluj.ro','Prenume56','password',NULL,1,'Nume'),(63,'email57@scs.ubbcluj.ro','Prenume57','password',NULL,1,'Nume'),(64,'email58@scs.ubbcluj.ro','Prenume58','password',NULL,1,'Nume'),(65,'email59@scs.ubbcluj.ro','Prenume59','password',NULL,1,'Nume'),(66,'email60@scs.ubbcluj.ro','Prenume60','password',NULL,1,'Nume'),(67,'email61@scs.ubbcluj.ro','Prenume61','password',NULL,1,'Nume'),(68,'email62@scs.ubbcluj.ro','Prenume62','password',NULL,1,'Nume'),(69,'email63@scs.ubbcluj.ro','Prenume63','password',NULL,1,'Nume'),(70,'email64@scs.ubbcluj.ro','Prenume64','password',NULL,1,'Nume'),(71,'email65@scs.ubbcluj.ro','Prenume65','password',NULL,1,'Nume'),(72,'email66@scs.ubbcluj.ro','Prenume66','password',NULL,1,'Nume'),(73,'email67@scs.ubbcluj.ro','Prenume67','password',NULL,1,'Nume'),(74,'email68@scs.ubbcluj.ro','Prenume68','password',NULL,1,'Nume'),(75,'email69@scs.ubbcluj.ro','Prenume69','password',NULL,1,'Nume'),(76,'email70@scs.ubbcluj.ro','Prenume70','password',NULL,1,'Nume'),(77,'email71@scs.ubbcluj.ro','Prenume71','password',NULL,1,'Nume'),(78,'email72@scs.ubbcluj.ro','Prenume72','password',NULL,1,'Nume'),(79,'email73@scs.ubbcluj.ro','Prenume73','password',NULL,1,'Nume'),(80,'email74@scs.ubbcluj.ro','Prenume74','password',NULL,1,'Nume'),(81,'email75@scs.ubbcluj.ro','Prenume75','password',NULL,1,'Nume'),(82,'email76@scs.ubbcluj.ro','Prenume76','password',NULL,1,'Nume'),(83,'email77@scs.ubbcluj.ro','Prenume77','password',NULL,1,'Nume'),(84,'email78@scs.ubbcluj.ro','Prenume78','password',NULL,1,'Nume'),(85,'email79@scs.ubbcluj.ro','Prenume79','password',NULL,1,'Nume'),(86,'email80@scs.ubbcluj.ro','Prenume80','password',NULL,1,'Nume'),(87,'email81@scs.ubbcluj.ro','Prenume81','password',NULL,1,'Nume'),(88,'email82@scs.ubbcluj.ro','Prenume82','password',NULL,1,'Nume'),(89,'email83@scs.ubbcluj.ro','Prenume83','password',NULL,1,'Nume'),(90,'email84@scs.ubbcluj.ro','Prenume84','password',NULL,1,'Nume'),(91,'email85@scs.ubbcluj.ro','Prenume85','password',NULL,1,'Nume'),(92,'email86@scs.ubbcluj.ro','Prenume86','password',NULL,1,'Nume'),(93,'email87@scs.ubbcluj.ro','Prenume87','password',NULL,1,'Nume'),(94,'email88@scs.ubbcluj.ro','Prenume88','password',NULL,1,'Nume'),(95,'email89@scs.ubbcluj.ro','Prenume89','password',NULL,1,'Nume'),(96,'email90@scs.ubbcluj.ro','Prenume90','password',NULL,1,'Nume'),(97,'email91@scs.ubbcluj.ro','Prenume91','password',NULL,1,'Nume'),(98,'email92@scs.ubbcluj.ro','Prenume92','password',NULL,1,'Nume'),(99,'email93@scs.ubbcluj.ro','Prenume93','password',NULL,1,'Nume'),(100,'email94@scs.ubbcluj.ro','Prenume94','password',NULL,1,'Nume'),(101,'email95@scs.ubbcluj.ro','Prenume95','password',NULL,1,'Nume'),(102,'email96@scs.ubbcluj.ro','Prenume96','password',NULL,1,'Nume'),(103,'email97@scs.ubbcluj.ro','Prenume97','password',NULL,1,'Nume'),(104,'email98@scs.ubbcluj.ro','Prenume98','password',NULL,1,'Nume'),(105,'email99@scs.ubbcluj.ro','Prenume99','password',NULL,1,'Nume'),(106,'email100@scs.ubbcluj.ro','Prenume100','password',NULL,1,'Nume');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-04 20:45:24
