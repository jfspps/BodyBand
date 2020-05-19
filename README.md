# BodyBand
Java and SQL based workout recorder

## Database schema

### Populating tblExercise

This table details how to set up the bands and where to position them for a given exercise.

### Populating tblRepetition

This table details the number of repetitions or 'reps' for a given band, detailed in the table tblBandStat.

### Populating tblBandStat

This table details the type of band used, sometimes characterised by a colour code and always denoted by its tension. Details about whether to double over a single band is also recorded here.

### Populating tblSet

This table links all other tables in BodyBand and provides a unique identifier to every single 'set' (one group of repetitions). Details about date and time are automatically included in this table.

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

This concludes the minimal functionality of BodyBand. More advanced features which would ideally be provided include:

- Recording of personal bests

- Repetition max prediction (heaviest weight for a given fixed number of reps) e.g. '1 rep max' or sometimes denoted 1RM represents the heaviest weight lifted once. Other rep max's for other repetitions e.g. 4RM, 8RM, will be made available. Generally, the 1RM weight is usually higher than all other 'xRMs'.

- Volume training trends (accumulated sum of weight lifted in a given session) plotted graphically.

- Goal setting
