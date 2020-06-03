-- create and use a database with sqlite3 bodyband.db

CREATE TABLE tblSet (
	idSet INTEGER PRIMARY KEY AUTOINCREMENT, 
	Exercise_id INTEGER NOT NULL, 
	Rep_id INTEGER NOT NULL, 
	Comments TEXT DEFAULT "", 
	SetDate TEXT NOT NULL DEFAULT "",
	FOREIGN KEY(Exercise_id) REFERENCES tblExercise(idExercise),
	FOREIGN KEY(Rep_id) REFERENCES tblRepetition(idRepetition)
);

CREATE TABLE tblExercise (
	idExercise INTEGER PRIMARY KEY AUTOINCREMENT,
    ExerciseName TEXT NOT NULL,
    MuscleGroup TEXT DEFAULT "",
	AnchorNeeded TEXT DEFAULT "", 
	AnchorHeight TEXT DEFAULT "", 
	AnchorPosition TEXT DEFAULT "", 
    Description TEXT DEFAULT "",
    VideoURL TEXT DEFAULT ""
);

CREATE TABLE tblRepetition (
	idRepetition INTEGER PRIMARY KEY AUTOINCREMENT, 
	BandStat_id INTEGER NOT NULL, 
	Repetitions INTEGER NOT NULL,
	FOREIGN KEY(BandStat_id) REFERENCES tblBandStat(idBandStat)
);

CREATE TABLE tblBandStat (
	idBandStat INTEGER PRIMARY KEY AUTOINCREMENT, 
	SingleBandTension INTEGER NOT NULL, 
	DoubledOrNot TEXT NOT NULL DEFAULT "single",
	Units TEXT DEFAULT ""
);

-- Populate with a record. Note that due to the FK contrainsts, tblExercises and tblBandStat must be populated before tblRepetition.
INSERT INTO tblExercise (
	ExerciseName, Description, VideoURL
	) VALUES(
	"Bent-over Rowing", "Develop the lats", "https://www.youtube.com/watch?v=TE3v7CgXiiI");
