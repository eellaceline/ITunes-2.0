-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: musicdb
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin_history`
--

DROP TABLE IF EXISTS `admin_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_history` (
  `changeDate` date NOT NULL,
  `report` varchar(200) NOT NULL,
  `user_user_id` int(11) NOT NULL,
  KEY `fk_admin_history_user1_idx` (`user_user_id`),
  CONSTRAINT `fk_admin_history_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_history`
--

LOCK TABLES `admin_history` WRITE;
/*!40000 ALTER TABLE `admin_history` DISABLE KEYS */;
INSERT INTO `admin_history` VALUES ('1970-01-01','Song:Both of us, Artist:Idealism, Album:no album',1),('1970-01-01','Song:Lonely, Artist:Idealism, Album:no album',1),('1970-01-01','Song:Controlla, Artist:Idealism, Album:no album',1);
/*!40000 ALTER TABLE `admin_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album` (
  `album_id` int(11) NOT NULL AUTO_INCREMENT,
  `albumName` varchar(45) NOT NULL,
  `artist_artist_id` int(11) NOT NULL,
  PRIMARY KEY (`album_id`),
  KEY `fk_album_artist1_idx` (`artist_artist_id`),
  CONSTRAINT `fk_album_artist1` FOREIGN KEY (`artist_artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'TestAlbum',1);
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist` (
  `artist_id` int(11) NOT NULL AUTO_INCREMENT,
  `artistName` varchar(45) NOT NULL,
  PRIMARY KEY (`artist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'Idealism');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `genreName` varchar(45) NOT NULL,
  PRIMARY KEY (`genreName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES ('Piano');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_number` int(11) NOT NULL AUTO_INCREMENT,
  `orderDate` date NOT NULL,
  `user_user_id` int(11) NOT NULL,
  PRIMARY KEY (`order_number`,`user_user_id`),
  KEY `fk_orders_user1_idx` (`user_user_id`),
  CONSTRAINT `fk_orders_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2018-04-27',2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_has_songs`
--

DROP TABLE IF EXISTS `orders_has_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_has_songs` (
  `orders_order_number` int(11) NOT NULL,
  `songs_song_id` int(11) NOT NULL,
  PRIMARY KEY (`orders_order_number`,`songs_song_id`),
  KEY `fk_orders_has_songs_songs1_idx` (`songs_song_id`),
  KEY `fk_orders_has_songs_orders1_idx` (`orders_order_number`),
  CONSTRAINT `fk_orders_has_songs_orders1` FOREIGN KEY (`orders_order_number`) REFERENCES `orders` (`order_number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_orders_has_songs_songs1` FOREIGN KEY (`songs_song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_has_songs`
--

LOCK TABLES `orders_has_songs` WRITE;
/*!40000 ALTER TABLE `orders_has_songs` DISABLE KEYS */;
INSERT INTO `orders_has_songs` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `orders_has_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs` (
  `song_id` int(11) NOT NULL AUTO_INCREMENT,
  `songName` varchar(80) NOT NULL,
  `songDuration` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `genre_genreName` varchar(45) NOT NULL,
  `album_album_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`song_id`,`genre_genreName`),
  KEY `fk_songs_genre1_idx` (`genre_genreName`),
  KEY `fk_songs_album1_idx` (`album_album_id`),
  CONSTRAINT `fk_songs_album1` FOREIGN KEY (`album_album_id`) REFERENCES `album` (`album_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_songs_genre1` FOREIGN KEY (`genre_genreName`) REFERENCES `genre` (`genreName`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES (1,'Both of us','1min53sec',2,'Piano',NULL),(2,'Lonely','1min59sec',2,'Piano',NULL),(3,'Controlla','1min48sec',2,'Piano',NULL);
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs_has_artist`
--

DROP TABLE IF EXISTS `songs_has_artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `songs_has_artist` (
  `songs_song_id` int(11) NOT NULL,
  `artist_artist_id` int(11) NOT NULL,
  PRIMARY KEY (`songs_song_id`,`artist_artist_id`),
  KEY `fk_songs_has_artist_artist1_idx` (`artist_artist_id`),
  KEY `fk_songs_has_artist_songs1_idx` (`songs_song_id`),
  CONSTRAINT `fk_songs_has_artist_artist1` FOREIGN KEY (`artist_artist_id`) REFERENCES `artist` (`artist_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_songs_has_artist_songs1` FOREIGN KEY (`songs_song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs_has_artist`
--

LOCK TABLES `songs_has_artist` WRITE;
/*!40000 ALTER TABLE `songs_has_artist` DISABLE KEYS */;
INSERT INTO `songs_has_artist` VALUES (1,1),(2,1),(3,1);
/*!40000 ALTER TABLE `songs_has_artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(90) NOT NULL,
  `email` varchar(90) NOT NULL,
  `password` varchar(45) NOT NULL,
  `balance` int(11) NOT NULL DEFAULT '0',
  `IsAnAdmin` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','root@root.com','wtty',0,''),(2,'ralle','r.olsson96@gmail.com','678',0,'\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_songs`
--

DROP TABLE IF EXISTS `user_has_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_songs` (
  `user_user_id` int(11) NOT NULL,
  `songs_song_id` int(11) NOT NULL,
  PRIMARY KEY (`user_user_id`,`songs_song_id`),
  KEY `fk_user_has_songs_songs1_idx` (`songs_song_id`),
  KEY `fk_user_has_songs_user1_idx` (`user_user_id`),
  CONSTRAINT `fk_user_has_songs_songs1` FOREIGN KEY (`songs_song_id`) REFERENCES `songs` (`song_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_songs_user1` FOREIGN KEY (`user_user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_songs`
--

LOCK TABLES `user_has_songs` WRITE;
/*!40000 ALTER TABLE `user_has_songs` DISABLE KEYS */;
INSERT INTO `user_has_songs` VALUES (1,1),(2,1),(2,2),(2,3);
/*!40000 ALTER TABLE `user_has_songs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-01 18:03:51
