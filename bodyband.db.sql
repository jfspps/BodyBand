BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "tblBandStat" (
	"idBandStat"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"SingleBandTension"	REAL NOT NULL,
	"DoubledOrNot"	TEXT DEFAULT "single",
	"Units"	TEXT DEFAULT ""
);
CREATE TABLE IF NOT EXISTS "tblRepetition" (
	"idRepetition"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"BandStat_id"	INTEGER NOT NULL,
	"Repetitions"	INTEGER NOT NULL,
	FOREIGN KEY("BandStat_id") REFERENCES "tblBandStat"("idBandStat") ON DELETE CASCADE
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
	FOREIGN KEY("Rep_id") REFERENCES "tblRepetition"("idRepetition") ON DELETE CASCADE,
	FOREIGN KEY("Exercise_id") REFERENCES "tblExercise"("idExercise") ON DELETE CASCADE
);
INSERT INTO "tblBandStat" VALUES (1,12,'single','kg');
INSERT INTO "tblBandStat" VALUES (2,4,'single','kg');
INSERT INTO "tblBandStat" VALUES (3,6,'single','kg');
INSERT INTO "tblBandStat" VALUES (4,8,'single','kg');
INSERT INTO "tblBandStat" VALUES (5,10,'single','kg');
INSERT INTO "tblBandStat" VALUES (6,14,'single','kg');
INSERT INTO "tblBandStat" VALUES (7,16,'single','kg');
INSERT INTO "tblBandStat" VALUES (8,18,'single','kg');
INSERT INTO "tblBandStat" VALUES (10,22,'single','kg');
INSERT INTO "tblRepetition" VALUES (1,2,8);
INSERT INTO "tblRepetition" VALUES (2,3,7);
INSERT INTO "tblRepetition" VALUES (3,8,6);
INSERT INTO "tblRepetition" VALUES (4,8,5);
INSERT INTO "tblExercise" VALUES (1,'Bent-over Rowing','Lats','no','N/A','Floor','Develop the lats','https://www.youtube.com/watch?v=TE3v7CgXiiI');
INSERT INTO "tblExercise" VALUES (2,'Lying pulldown','Lats','yes','','Base','Models a lateral pulldown','');
INSERT INTO "tblExercise" VALUES (3,'Standing Row','Lats','yes','','Chest','','');
INSERT INTO "tblExercise" VALUES (4,'Standing cable pull','Lats','yes','','Top','','');
INSERT INTO "tblExercise" VALUES (5,'Chest Press','Chest','yes','','Knee','Works the mid and upper chest','');
INSERT INTO "tblExercise" VALUES (6,'Standing Fly','Chest','yes','','Chest','','');
INSERT INTO "tblExercise" VALUES (7,'Standing Pushdown','Chest','yes','','Top','Works the lower chest','');
INSERT INTO "tblExercise" VALUES (8,'Squat','Legs','no','','Floor','Compound exercise for the thighs','');
INSERT INTO "tblExercise" VALUES (9,'Calf Raise','Legs','yes','','Base','','');
INSERT INTO "tblExercise" VALUES (10,'Seated Bicep Curl','Arms','yes','','Base','Works the biceps','');
INSERT INTO "tblExercise" VALUES (12,'Resisted Curl','Abs','yes','','Base','Challenge the core!','');
INSERT INTO "tblSet" VALUES (1,10,1,'Now the hard work begins!','10th March');
INSERT INTO "tblSet" VALUES (2,8,3,'Getting there!','10th March');
INSERT INTO "tblSet" VALUES (3,8,4,'As expected','10th March');
COMMIT;
