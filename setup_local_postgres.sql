-- Create ELearningSystem database
CREATE DATABASE IF NOT EXISTS ELearningSystem;

-- Connect to the database
\c ELearningSystem

-- Admin Table
CREATE TABLE IF NOT EXISTS Admin(
    Adminid SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    Email_ID VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20),
    Gender VARCHAR(10),
    picture BYTEA
);

INSERT INTO Admin (fname, lname, Email_ID, username, password, Gender)
VALUES('Suwaid', 'Aslam', 'Suwaid@admin.com', 'admin', 'admin', 'Male')
ON CONFLICT (username) DO NOTHING;

-- Student Table
CREATE TABLE IF NOT EXISTS Student(
    stdID SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    Email_ID VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20),
    Gender VARCHAR(10),
    Registration_Date DATE NOT NULL DEFAULT CURRENT_DATE,
    Last_Login TIMESTAMP,
    picture BYTEA
);

-- Teacher Table
CREATE TABLE IF NOT EXISTS Teacher(
    teacherID SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    Email_ID VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20),
    Gender VARCHAR(10),
    Registration_Date DATE NOT NULL DEFAULT CURRENT_DATE,
    Last_Login TIMESTAMP,
    picture BYTEA
);

-- Subjects Table
CREATE TABLE IF NOT EXISTS Subjects(
    Subject_ID SERIAL PRIMARY KEY,
    Name VARCHAR(150) NOT NULL UNIQUE,
    Adminid INT NOT NULL,
    CONSTRAINT Adminid_fk FOREIGN KEY (Adminid) REFERENCES Admin(Adminid) ON DELETE CASCADE
);

-- Courses Table
CREATE TABLE IF NOT EXISTS Courses(
    course_ID SERIAL PRIMARY KEY,
    Name VARCHAR(250) NOT NULL UNIQUE,
    Description VARCHAR(1000) NOT NULL,
    Content VARCHAR(3000) NOT NULL,
    teacherID INT NOT NULL,
    Subject_ID INT NOT NULL,
    CONSTRAINT teacherID_fk FOREIGN KEY (teacherID) REFERENCES Teacher(teacherID) ON DELETE CASCADE,
    CONSTRAINT subjectID_fk FOREIGN KEY (Subject_ID) REFERENCES Subjects(Subject_ID) ON DELETE CASCADE
);

-- Enrollments Table
CREATE TABLE IF NOT EXISTS Enrollments(
    Enrollment_ID SERIAL PRIMARY KEY,
    Enrollment_Date DATE NOT NULL DEFAULT CURRENT_DATE,
    course_ID INT NOT NULL,
    stdID INT NOT NULL,
    CONSTRAINT courseID_fk FOREIGN KEY (course_ID) REFERENCES Courses(course_ID) ON DELETE CASCADE,
    CONSTRAINT stdID_fk FOREIGN KEY (stdID) REFERENCES Student(stdID) ON DELETE CASCADE,
    UNIQUE(course_ID, stdID)
);

-- Messages Table
CREATE TABLE IF NOT EXISTS Messages(
    Message_ID SERIAL PRIMARY KEY,
    message VARCHAR(2000) NOT NULL,
    time_Stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    User_ID INT NOT NULL,
    toUser_ID INT NOT NULL
);

-- MessageUsers Table
CREATE TABLE IF NOT EXISTS MessageUsers(
    User_ID INT NOT NULL,
    Message_ID INT NOT NULL,
    CONSTRAINT message_ID_fk FOREIGN KEY (Message_ID) REFERENCES Messages(Message_ID) ON DELETE CASCADE
);

-- Verify tables created
SELECT tablename FROM pg_tables WHERE schemaname='public';
