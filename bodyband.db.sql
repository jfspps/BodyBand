BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "tblRepetition" (
	"idRepetition"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"Tension"	INTEGER NOT NULL,
	"Repetitions"	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS "tblExercise" (
	"idExercise"	INTEGER PRIMARY KEY AUTOINCREMENT,
	"ExerciseName"	TEXT NOT NULL,
	"MuscleGroup"	TEXT DEFAULT "",
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
INSERT INTO "tblRepetition" VALUES (1,8,9);
INSERT INTO "tblRepetition" VALUES (2,10,6);
INSERT INTO "tblRepetition" VALUES (3,14,4);
INSERT INTO "tblRepetition" VALUES (4,18,4);
INSERT INTO "tblExercise" VALUES (1,'Bent-over Rowing','Lats','Floor','Develop the lats','https://www.youtube.com/watch?v=TE3v7CgXiiI');
INSERT INTO "tblExercise" VALUES (2,'Lying pulldown','Lats','Base','Models a lateral pulldown','');
INSERT INTO "tblExercise" VALUES (3,'Standing Row','Lats','Chest','','');
INSERT INTO "tblExercise" VALUES (4,'Standing cable pull','Lats','Top','','');
INSERT INTO "tblExercise" VALUES (5,'Chest Press','Chest','Knee','Works the mid and upper chest','');
INSERT INTO "tblExercise" VALUES (6,'Standing Fly','Chest','Chest','','');
INSERT INTO "tblExercise" VALUES (7,'Standing Pushdown','Chest','Top','Works the lower chest','');
INSERT INTO "tblExercise" VALUES (8,'Squat','Legs','Floor','Compound exercise for the thighs','');
INSERT INTO "tblExercise" VALUES (9,'Calf Raise','Legs','Base','','');
INSERT INTO "tblExercise" VALUES (10,'Seated Bicep Curl','Arms','Base','Works the biceps','');
INSERT INTO "tblExercise" VALUES (12,'Resisted Curl','Abs','Base','Challenge the core!','');
INSERT INTO "tblSet" VALUES (1,10,1,'Now the hard work begins!','10th March');
INSERT INTO "tblSet" VALUES (2,8,3,'Getting there!','10th March');
INSERT INTO "tblSet" VALUES (3,8,4,'As expected','10th March');
COMMIT;
