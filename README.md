# True/False Game API

This app provides a simple API for a questionnaire game called True/False, which is a game, where the player has to answer questions with a true or false choice.
An example of such a question could be `The first Star Wars movie came out in 1977. True or false?`. Here the correct answer is `true`
You can of course structure your game or application however you like, with this API as the backbone.

## How to run the API

This project is dockerized and you can use [Docker](https://www.docker.com/get-started/) to run the project, if you have Docker Desktop installed on your local machine.
If you dont want to use docker, then you can package your app with [maven](https://maven.apache.org/install.html) locally, using the command `mvn clean package`.

- Start by cloning the project down to your local machine, and open it up in your preferred IDE.

### 1. Prepare the MySQL database

This app is using a MySQL database for storage. You can connect to your own MySQL database installation, or use a dockerized MySQL.
To use a dockerized MySQL database, copy the following yml code into a yaml file named `docker-compose-mysql.yml` anywhere on your machine.
You can of course use the default file name, which is `docker-compose.yml`, if you choose this option, you can ommit the -f when you run the compose commands later.

```yaml title="docker-compose-mysql.yml"
services:
  mysqldb:
    image: mysql:8.0
    container_name: mysql_container
    environment:
      MYSQL_ROOT_PASSWORD: example_password
      MYSQL_DATABASE: example_db
      MYSQL_USER: example_user
      MYSQL_PASSWORD: example_password
    ports:
      - "3306:3306"
    expose:
      - "3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      default: {}

volumes:
  mysql_data:

networks:
  default:
    name: mysql_db_network
    attachable: true
```

- Remember to change the environment variables (DB user, password and DB name) to your liking, before proceding to the next step.

To run the file open op a terminal at the location where you saved your `docker-compose-mysql.yml` file, and then use the following command:

```bash
docker compose -f docker-compose-mysql.yml up -d
```

This command will fetch the MySQL image and then run a container with MySQL in the background. You can now access MySQL using the credentials you provided in the compose file.

- To stop the MySQL container, you can use the following command:

```bash
docker compose -f docker-compose-mysql.yml down
```

the down command will stop and remove the container. The compose file have created a volume, so you databases will still be persisted.

> Important note: This docker compose file creates a shared network called mysql_db_network. This shared network is also used in the projects docker compose file. If you want to change the name of the network, remember to change it in both the MySQL and the project docker compose files.

### 2. Setup production environment variables

Create an environment file called `.env.docker.prod` at the project root and populate it with the following environment variables, using your database credentials, and setting an api key.
Notice that the **host** may not be `localhost` if you are running the database from the docker compose file above. In this case the database host is the service name, which is `mysqldb`

```properties title=".env.docker.prod"
DATABASE_URL=jdbc:mysql://<YOUR_HOST>:3306/<YOUR_DATABSE_NAME>
DATABASE_USERNAME=<YOUR_DB_USERNAME>
DATABASE_PASSWORD=<YOUR_DB_PASSWORD>
API_KEY=<SECRET_API_KEY>
```

The naming is important, as the projects docker compose file will use the environment variables from this specific file.

> Notice: If you also want to run the project from the IDE itself, then you will need to create a file called .env with the environment variables inside. The database host in this instant will be localhost, also if you run the database from the docker compose file above

### 3. Run the project using Docker

The project already have a docker compose file, which use a shared network with the MySQL container. If you are using your own local MySQL database or a seperate database server, the shared network will not be used.

To run the project using docker, open up a terminal window at the project root location. Then use the following command:

```bash
docker compose up -d
```

This will build the project and start it up. You can now access the API at `http://localhost:8080/swagger-ui/index.html` 
To test the api, I can also recommend using [Postman API Platform](https://www.postman.com/) as it offers more features.

To stop the container, use the following command:

```bash
docker compose down
```

## Flyway Plugin for development

This application uses flyway under the hood to migrate the database. To use the flyway plugin with maven for further development of the app, 
you will have to configure it to your database. To do this, you can create a file in the project root called `flyway.conf` and populate it with the db url, user and password properties. 
I usually also set the cleanDisabled property to false when developing, to allow running the clean command to the databsae. The file properties could be set as follows:

```properties title="flyway.conf"
flyway.url=jdbc:mysql://<DB_HOST>:3306/<DB_NAME>
flyway.user=<DB_USER>
flyway.password=<DB_PASSWORD>
flyway.cleanDisabled=false
```

You can now use the maven flyway commands like `mvn clean flyway:migrate` to migrate with flyway. This requires that [maven](https://maven.apache.org/install.html) and [java JDK](https://www.oracle.com/java/technologies/downloads/) is installed locally on your machine.

## Populate the database with data

You can create and insert your own questions and categories in the database, or you can use the predefined questions from the `testdata.sql` file, 
which is found in the project root. You can also do a combination of course.

> even though the file is called `testdata.sql`, the questions can be used in production if you want to

---

&copy; 2025 Martin Rohwedder