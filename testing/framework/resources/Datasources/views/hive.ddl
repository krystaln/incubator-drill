DROP TABLE IF EXISTS rankings1;
CREATE EXTERNAL TABLE rankings1 (
    pageRank INT,
    pageURL STRING,
    avgDuration INT
)
ROW FORMAT DELIMITED FIELDS
TERMINATED BY '|'
STORED AS TEXTFILE LOCATION "/drill/testdata/views/hive/rankings/";

DROP TABLE IF EXISTS uservisits1;
CREATE EXTERNAL TABLE uservisits1 (
    sourceIP STRING,
    destinationURL STRING,
    visitDate STRING,
    adRevenue FLOAT,
    userAgent STRING,
    countryCode STRING,
    languageCode STRING,
    searchWord STRING,
    duration INT
)
ROW FORMAT DELIMITED FIELDS TERMINATED BY "|"
STORED AS TEXTFILE LOCATION "/drill/testdata/views/hive/uservisits/";

DROP TABLE IF EXISTS PAGES;
CREATE EXTERNAL TABLE PAGES (
    URL STRING,
    DESCRIPTION STRING
)
ROW FORMAT DELIMITED FIELDS
TERMINATED BY '|'
STORED AS TEXTFILE LOCATION "/drill/testdata/views/hive/pages/";


CREATE DATABASE IF NOT EXISTS MARKETING;
USE MARKETING;
DROP TABLE IF EXISTS HIVESTUDENTS;
CREATE EXTERNAL TABLE HIVESTUDENTS
(
    ID VARCHAR(24),
    NAME VARCHAR(24),
    STREET VARCHAR(256),
    ZIPCODE INT,
    STATE VARCHAR(20),
    PERCENTAGE DECIMAL
)
ROW FORMAT DELIMITED FIELDS
TERMINATED BY ','
STORED AS TEXTFILE LOCATION "/drill/testdata/information-schema/students/";

DROP TABLE IF EXISTS VOTER;
CREATE EXTERNAL TABLE VOTER (
    voter_id INT,
    name string,
    age tinyint,
    registration string,
    contributions float,
    voterzone smallint,
    create_time timestamp
) 
ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t'
STORED AS TEXTFILE LOCATION "/drill/testdata/views/hive/voter/";