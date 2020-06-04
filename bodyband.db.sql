BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "tblBandStat" (
	"idBandStat"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"SingleBandTension"	INTEGER NOT NULL,
	"DoubledOrNot"	TEXT NOT NULL DEFAULT "single",
	"Units"	TEXT DEFAULT ""
);
CREATE TABLE IF NOT EXISTS "tblRepetition" (
	"idRepetition"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"BandStat_id"	INTEGER NOT NULL,
	"Repetitions"	INTEGER NOT NULL,
	FOREIGN KEY("BandStat_id") REFERENCES "tblBandStat"("idBandStat")
);
CREATE TABLE IF NOT EXISTS "tblExercise" (
	"idExercise"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ExerciseName"	TEXT NOT NULL,
	"MuscleGroup"	TEXT DEFAULT "",
	"AnchorNeeded"	TEXT DEFAULT "",
	"AnchorHeight"	TEXT DEFAULT "",
	"AnchorPosition"	TEXT DEFAULT "",
	"Description"	TEXT DEFAULT "",
	"VideoURL"	TEXT DEFAULT ""
);
CREATE TABLE IF NOT EXISTS "tblSet" (
	"idSet"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"Exercise_id"	INTEGER NOT NULL,
	"Rep_id"	INTEGER NOT NULL,
	"Comments"	TEXT DEFAULT "",
	"SetDate"	TEXT NOT NULL DEFAULT "",
	FOREIGN KEY("Rep_id") REFERENCES "tblRepetition"("idRepetition"),
	FOREIGN KEY("Exercise_id") REFERENCES "tblExercise"("idExercise")
);
INSERT INTO "tblBandStat" VALUES (1,12,'no','kg');
INSERT INTO "tblExercise" VALUES (1,'Bent-over Rowing','Lats','no','N/A','','Develop the lats','https://www.youtube.com/watch?v=TE3v7CgXiiI');
COMMIT;
