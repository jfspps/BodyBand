# BodyBand
[Java](https://www.linuxuprising.com/2019/06/new-oracle-java-11-installer-for-ubuntu.html) and SQL based workout recorder.

The SQLite 3 libraries are included in this repo. The JavaFX 11 libraries reference to a folder elsewhere.

## Setting up the SQLite3 driver

SQLite3 driver is [here](https://bitbucket.org/xerial/sqlite-jdbc/downloads/). The SQLite browser is [here](https://sqlitebrowser.org/) with installation instructions.

For BodyBand, a copy is stored in /database. In IntelliJ, click File then Project Structure and then Project Settings/Libraries. Add the SQLite JAR file.

## Setting up JavaFX11 for new projects

JavaFX downloads are [here](https://gluonhq.com/products/javafx/). Extract the files to a known directory and through IntelliJ add all the /lib/*.jar files to the Global Libraries. Right-click the parent project folder in IntelliJ (or just click File, Project Structure) and hit 'Open Module Settings'. Right-click 'JavaFX11' found under 'Global Settings' and 'Add to Project Libraries'. Then right click the 'src' folder and create a new module-info.java file. Add the following and save:

```java
module BodyBand {
    requires javafx.fxml;
    requires javafx.controls;

    opens sample;
}
```

Module in this case is the project name. The folder under the src folder was set to 'sample' (as project defaults) but can be changed there and above as required.

## Database schema

### Populating tblExercise

This table details how to set up the bands and where to position them for a given exercise.

### Populating tblRepetition

This table details the number of repetitions or 'reps' for a given band, detailed in the table tblBandStat.

### Populating tblBandStat

This table details the type of band used, sometimes characterised by a colour code and always denoted by its tension. Details about whether to double over a single band is also recorded here.

### Populating tblSet

This table links all other tables in BodyBand and provides a unique identifier to every single 'set' (one group of repetitions). Details about date and time are automatically included in this table.

![MySQL workbench schema](./database/Schema.png)

The above schema was designed in MySQL workbench. The creation of the tables in SQLite using the MySQL datatypes will result in the following automatic SQLite datatype casts:

+ INT -> INTEGER
+ VARCHAR -> TEXT
+ DATE -> NUMERIC (implemented as TEXT in BodyBand)

For this project, the SQLite datatypes are passed instead of using the MySQL datatypes.

# Development outline

The initial development stages are:

+ Create SQLite3 tables, including primary and foreign keys (Model)
+ Build simple CRUD framework through Java (Controller)
+ Implement simple JavaFX interface (Viewer)
+ Port to Android and iOS using the SDKs from Gluon

## Basic setup

1. Design and build a settings page for the tables __tblExercise__ and __tblBandStat__.
2. Design and build an input page for __tblSet__.
3. Design and build a recall page which enables the user to view previous sets, searching by date.

## User interface flow

+ Initially, an admin version (raw exercise, band stat, repetition and set pages) will be added temporarily for trials.
+ "New workout" ------>   "Muscle group" ------>   "Exercise" ------>  "Rep page"
+ Previous workout --->  Date (calendar) --->  Summary -->--(Rep page)---^

The Rep page is shared, with option for CRUD of reps and band stats. Additionally, the Exercise page will have menu bar to create, update and delete exercises.

## Future development

More advanced features which would ideally be provided include:

- Recording of personal bests

- Repetition max prediction (heaviest weight for a given fixed number of reps) e.g. '1 rep max' or sometimes denoted 1RM represents the heaviest weight lifted once. Other rep max's for other repetitions e.g. 4RM, 8RM, will be made available. Generally, the 1RM weight is usually higher than all other 'xRMs'.

- Volume training trends (accumulated sum of weight lifted in a given session) plotted graphically.

- Goal setting
