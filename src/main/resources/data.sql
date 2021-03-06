INSERT INTO Course(ID, NAME, CREATED_DATE, LAST_UPDATED_DATE, IS_DELETED)
VALUES (10001, 'Jpa in 50 steps', SYSDATE(), SYSDATE(), false);
INSERT INTO Course(ID, NAME, CREATED_DATE, LAST_UPDATED_DATE, IS_DELETED)
VALUES (10002, 'Spring in 50 steps', SYSDATE(), SYSDATE(), false);
INSERT INTO Course(ID, NAME, CREATED_DATE, LAST_UPDATED_DATE, IS_DELETED)
VALUES (10003, 'Java in 50 steps', SYSDATE(), SYSDATE(), false);

INSERT INTO Passport(id, number)
VALUES (30001, 'E123456');
INSERT INTO Passport(id, number)
VALUES (30002, 'F234567');
INSERT INTO Passport(id, number)
VALUES (30003, 'V345678');

INSERT INTO Student(id, name, passport_id)
VALUES (20001, 'Adrian', 30001);
INSERT INTO Student(id, name, passport_id)
VALUES (20002, 'Damian', 30002);
INSERT INTO Student(id, name, passport_id)
VALUES (20003, 'Przemek', 30003);

INSERT INTO Review(id, rating, description, course_id)
VALUES (40001, 'FIVE', 'Great', 10001);
INSERT INTO Review(id, rating, description, course_id)
VALUES (40002, 'FIVE','Super', 10001);
INSERT INTO Review(id, rating, description, course_id)
VALUES (40003, 'FOUR', 'Awesome!', 10003);

INSERT INTO Student_Course(student_id, course_id)
VALUES (20001,10001);
INSERT INTO Student_Course(student_id, course_id)
VALUES (20002,10001);
INSERT INTO Student_Course(student_id, course_id)
VALUES (20003,10001);
INSERT INTO Student_Course(student_id, course_id)
VALUES (20003,10003);