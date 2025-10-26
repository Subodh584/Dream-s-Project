#!/bin/bash
# PostgreSQL beforeFirst() Fix Script
# This fixes all files that use rs.beforeFirst() which is not supported by PostgreSQL

echo "Fixing PostgreSQL compatibility issues..."

# List of files to fix
FILES=(
    "src/javasemesterproject/Teacher/ViewMyStudents.java"
    "src/javasemesterproject/Teacher/UpdateCourses.java"
    "src/javasemesterproject/Teacher/AddCourse.java"
    "src/javasemesterproject/Teacher/ViewMyCourses.java"
    "src/javasemesterproject/Admin/ViewStudents.java"
    "src/javasemesterproject/Admin/ViewTeachers.java"
    "src/javasemesterproject/Student/SentBox.java"
    "src/javasemesterproject/Student/Inbox.java"
    "src/javasemesterproject/Student/WithdrawCourse.java"
    "src/javasemesterproject/Student/ViewParticipants.java"
    "src/javasemesterproject/Student/StudyCourse.java"
    "src/javasemesterproject/Student/EnrollCourse.java"
)

echo "Total files to patch: ${#FILES[@]}"
echo ""
echo "Files that need fixing:"
for file in "${FILES[@]}"; do
    if [ -f "$file" ]; then
        echo "  ✓ $file"
    else
        echo "  ✗ $file (not found)"
    fi
done

echo ""
echo "Note: Manual fix required for remaining files."
echo "The pattern to replace:"
echo "  1. Count rows: while(rs.next()) rowCount++"
echo "  2. Create array: new String[rowCount]"
echo "  3. Reset: rs.beforeFirst()"
echo "  4. Fill array: while(rs.next()) ..."
echo ""
echo "Should be replaced with:"
echo "  1. Use ArrayList to collect results"
echo "  2. Convert ArrayList to array"
echo "  3. No beforeFirst() needed"
echo ""
echo "See ViewCourses.java and DeleteSubject.java for examples of the fix."
