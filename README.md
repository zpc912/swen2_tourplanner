# Tour Planner
**From the project specification:**
Develop an application based on the GUI frameworks C# / WPF or Java / JavaFX. The user creates (bike-, hike-, running- or vacation-) tours in advance and manages the logs and statistical data of accomplished tours.

---

## Prerequisites

### Libraries
For the application to run properly the following libraries are needed:

- [iText7 (io, commons, kernel, layout)](https://github.com/itext/itext7)
- [Jackson Databind](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-databind)
- [Jackson Core](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-core)
- [Jackson Annotations](https://jar-download.com/artifacts/com.fasterxml.jackson.core/jackson-annotations)
- [log4j](https://logging.apache.org/log4j/2.x/download.html)
- [slf4j (slf4j-api, slf4j-nop)](https://repo1.maven.org/maven2/org/slf4j/)
- [postgresql-42.3.1](https://jdbc.postgresql.org/download.html)
- JUnit *(to be imported from Maven)*
- javafx.scene.web *(to be imported from Maven)*
  - **NOTE:** In order to import the module properly the following command must be added in the VM options of the application: ``--add-modules javafx.web``


### Configruation File

The ``config.properties`` file consists of the following properties which need to be declared and initialized with the right values:

- DbConnectionString (URL to the database)
- DbUser
- DbPassword
- ConsumerKey (from MapQuest API)


### Database

The database consists of the tables ``tours`` and ``tourlogs``. The SQL format of the database can be downloaded [here](https://ufile.io/k1fm27vt).

---

## Link to GitHub
[TourPlanner on GitHub](https://github.com/zpc912/tourplanner_swen2)