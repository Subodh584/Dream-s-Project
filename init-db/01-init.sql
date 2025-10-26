-- Database initialization script for Docker
-- Automatically runs when PostgreSQL container starts

-- Database Name = 'ELearningSystem'
-- PostgreSQL Version (Compatible with Docker)
-- This script creates all tables for the E-Learning System

-- ============================================================================
-- Admin Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS admin(
    adminid SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    email_id VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(10),
    picture BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO admin (fname, lname, email_id, username, password, gender)
VALUES('Suwaid', 'Aslam', 'Suwaid@admin.com', 'admin', 'admin', 'Male')
ON CONFLICT (username) DO NOTHING;

-- ============================================================================
-- Student Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS student(
    stdid SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    email_id VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(10),
    registration_date DATE NOT NULL DEFAULT CURRENT_DATE,
    last_login TIMESTAMP,
    picture BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample student (Subodh)
INSERT INTO student (fname, lname, email_id, username, password, gender, registration_date)
VALUES ('Subodh', 'Student', 'subodh@student.com', 'AALu', '12312123123', 'Male', CURRENT_DATE)
ON CONFLICT (username) DO NOTHING;

-- ============================================================================
-- Teacher Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS teacher(
    teacherid SERIAL PRIMARY KEY,
    fname VARCHAR(150) NOT NULL,
    lname VARCHAR(150),
    email_id VARCHAR(250),
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255),
    gender VARCHAR(10),
    registration_date DATE NOT NULL DEFAULT CURRENT_DATE,
    last_login TIMESTAMP,
    picture BYTEA,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- Subjects Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS subjects(
    subject_id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    adminid INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT adminid_fk FOREIGN KEY (adminid) REFERENCES admin(adminid) ON DELETE CASCADE
);

CREATE INDEX idx_subjects_adminid ON subjects(adminid);

-- ============================================================================
-- Courses Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS courses(
    course_id SERIAL PRIMARY KEY,
    name VARCHAR(250) NOT NULL UNIQUE,
    description VARCHAR(1000) NOT NULL,
    content VARCHAR(3000) NOT NULL,
    teacherid INTEGER NOT NULL,
    subject_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT teacherid_fk FOREIGN KEY (teacherid) REFERENCES teacher(teacherid) ON DELETE CASCADE,
    CONSTRAINT subject_id_fk FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE
);

CREATE INDEX idx_courses_teacherid ON courses(teacherid);
CREATE INDEX idx_courses_subject_id ON courses(subject_id);

-- ============================================================================
-- Enrollments Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS enrollments(
    enrollment_id SERIAL PRIMARY KEY,
    enrollment_date DATE NOT NULL DEFAULT CURRENT_DATE,
    course_id INTEGER NOT NULL,
    stdid INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT course_id_fk FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    CONSTRAINT stdid_fk FOREIGN KEY (stdid) REFERENCES student(stdid) ON DELETE CASCADE,
    UNIQUE(course_id, stdid)
);

CREATE INDEX idx_enrollments_stdid ON enrollments(stdid);
CREATE INDEX idx_enrollments_course_id ON enrollments(course_id);

-- ============================================================================
-- Messages Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS messages(
    message_id SERIAL PRIMARY KEY,
    message VARCHAR(2000) NOT NULL,
    time_stamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    to_user_id INTEGER NOT NULL,
    message_type VARCHAR(20) DEFAULT 'text',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_messages_user_id ON messages(user_id);
CREATE INDEX idx_messages_to_user_id ON messages(to_user_id);
CREATE INDEX idx_messages_timestamp ON messages(time_stamp DESC);

-- ============================================================================
-- MessageUsers Table
-- ============================================================================
CREATE TABLE IF NOT EXISTS message_users(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    message_id INTEGER NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT message_id_fk FOREIGN KEY (message_id) REFERENCES messages(message_id) ON DELETE CASCADE
);

CREATE INDEX idx_message_users_user_id ON message_users(user_id);

-- ============================================================================
-- Views for easier querying
-- ============================================================================

CREATE OR REPLACE VIEW user_inbox AS
SELECT 
    m.message_id,
    m.message,
    m.time_stamp,
    m.user_id,
    m.to_user_id,
    COALESCE(s.fname || ' ' || s.lname, t.fname || ' ' || t.lname, a.fname || ' ' || a.lname) as sender_name,
    m.is_read
FROM messages m
LEFT JOIN student s ON m.user_id = s.stdid
LEFT JOIN teacher t ON m.user_id = t.teacherid
LEFT JOIN admin a ON m.user_id = a.adminid
WHERE m.to_user_id IS NOT NULL
ORDER BY m.time_stamp DESC;

CREATE OR REPLACE VIEW user_sentbox AS
SELECT 
    m.message_id,
    m.message,
    m.time_stamp,
    m.user_id,
    m.to_user_id,
    COALESCE(s.fname || ' ' || s.lname, t.fname || ' ' || t.lname, a.fname || ' ' || a.lname) as recipient_name,
    m.is_read
FROM messages m
LEFT JOIN student s ON m.to_user_id = s.stdid
LEFT JOIN teacher t ON m.to_user_id = t.teacherid
LEFT JOIN admin a ON m.to_user_id = a.adminid
WHERE m.user_id IS NOT NULL
ORDER BY m.time_stamp DESC;

-- ============================================================================
-- Performance Indexes
-- ============================================================================
CREATE INDEX IF NOT EXISTS idx_admin_username ON admin(username);
CREATE INDEX IF NOT EXISTS idx_student_username ON student(username);
CREATE INDEX IF NOT EXISTS idx_student_email ON student(email_id);
CREATE INDEX IF NOT EXISTS idx_teacher_username ON teacher(username);
CREATE INDEX IF NOT EXISTS idx_teacher_email ON teacher(email_id);

-- ============================================================================
-- Initialization Complete
-- ============================================================================
-- All tables created and initialized successfully!
-- Database is ready for use.
