-- Create courses table
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    source_language VARCHAR(10) NOT NULL,
    target_language VARCHAR(10) NOT NULL,
    difficulty_level VARCHAR(50) NOT NULL,
    image_url VARCHAR(500),
    is_published BOOLEAN DEFAULT false,
    total_xp INTEGER DEFAULT 0,
    estimated_hours DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_difficulty CHECK (difficulty_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED', 'EXPERT')),
    CONSTRAINT chk_languages CHECK (source_language IN ('en', 'ro', 'es') AND target_language IN ('en', 'ro', 'es')),
    CONSTRAINT chk_different_languages CHECK (source_language != target_language)
);

-- Create modules table
CREATE TABLE modules (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    sequence_order INTEGER NOT NULL,
    is_locked BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT uq_course_sequence UNIQUE (course_id, sequence_order)
);

-- Create lessons table
CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY,
    module_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    lesson_type VARCHAR(50) NOT NULL,
    sequence_order INTEGER NOT NULL,
    xp_reward INTEGER DEFAULT 10,
    is_locked BOOLEAN DEFAULT true,
    content_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_module FOREIGN KEY (module_id) REFERENCES modules(id) ON DELETE CASCADE,
    CONSTRAINT uq_module_sequence UNIQUE (module_id, sequence_order),
    CONSTRAINT chk_lesson_type CHECK (lesson_type IN ('VOCABULARY', 'GRAMMAR', 'LISTENING', 'SPEAKING', 'READING', 'WRITING', 'MIXED'))
);

-- Create course tags table
CREATE TABLE course_tags (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_course_tags FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT uq_course_tag UNIQUE (course_id, name)
);

-- Create indexes for better query performance
CREATE INDEX idx_courses_languages ON courses(source_language, target_language);
CREATE INDEX idx_courses_difficulty ON courses(difficulty_level);
CREATE INDEX idx_courses_published ON courses(is_published);
CREATE INDEX idx_modules_course ON modules(course_id);
CREATE INDEX idx_lessons_module ON lessons(module_id);
CREATE INDEX idx_course_tags_name ON course_tags(name);

-- Insert sample data
INSERT INTO courses (title, description, source_language, target_language, difficulty_level, is_published, total_xp, estimated_hours) VALUES
('English for Romanian Speakers', 'Learn English from scratch with interactive lessons', 'ro', 'en', 'BEGINNER', true, 500, 20.0),
('Spanish Basics', 'Start your Spanish journey with everyday conversations', 'en', 'es', 'BEGINNER', true, 450, 18.0),
('Advanced Romanian Grammar', 'Master complex Romanian grammar structures', 'en', 'ro', 'ADVANCED', true, 800, 35.0);

INSERT INTO modules (course_id, title, description, sequence_order, is_locked) VALUES
(1, 'Greetings and Introductions', 'Learn how to greet people and introduce yourself', 1, false),
(1, 'Numbers and Time', 'Master numbers, dates, and telling time', 2, true),
(1, 'Daily Activities', 'Describe your daily routine and activities', 3, true);

INSERT INTO lessons (module_id, title, description, lesson_type, sequence_order, xp_reward, is_locked) VALUES
(1, 'Basic Greetings', 'Hello, goodbye, and common greetings', 'VOCABULARY', 1, 10, false),
(1, 'Introducing Yourself', 'How to say your name and where you are from', 'MIXED', 2, 15, true),
(1, 'Practice Conversation', 'Interactive greeting dialogues', 'SPEAKING', 3, 20, true);

INSERT INTO course_tags (course_id, name) VALUES
(1, 'beginner-friendly'),
(1, 'conversation'),
(2, 'beginner-friendly'),
(2, 'travel'),
(3, 'grammar');
