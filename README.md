#JOMIAN
The exam project for the 2016-05 Göteborg Lexicon Java Course.

The purpose of this program is to teach the participants how to build and manage a small/medium sized web project
built in Jave EE using Glassfish and PostgreSQL.

The participants in this project are:

Johan
Mikael
Andreas
##What is it?
It's going to be a small program to handle courses for a small school, mainly to keep track of the attendance of the
students.

##How to install
First off, you need to have JAVA VM, Glassfish and PostgreSQL installed first, how you do that depends on your platform,
read any of the multitudes of guides available online if you don't know how to.

##Configure Glassfish and PostgreSQL
All of these steps are based on an install from a Linux terminal prompt as that is my dev environment.

**Setup PostgreSQL for use with glassfish**

Set up a basic database using the postgres admin account as the owner, I would not recommend doing that for
any other purpose than development and/or testing:
```
sudo -u postgres createdb jomian
```

Download the postgreSQL java driver for Glassfish:
```
cd {your_glassfish_directory}/glassfish/domains/domain1/lib
wget https://jdbc.postgresql.org/download/postgresql-9.4.1209.jar
```

Create a connection pool in Glassfish: (you need to change {password} below to the password you are using for the
PostgreSQL admin account)
```
asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.DataSource --property user=postgres:password={password}:DatabaseName=jomian:servername=localhost:port=5432 JomianPool
```

Test the connection pool:
```
asadmin ping-connection-pool JomianPool
```

Bind the connection pool to a JDBC resource:
```
asadmin create-jdbc-resource --connectionpoolid forum-pool jdbc/Jomian
```
