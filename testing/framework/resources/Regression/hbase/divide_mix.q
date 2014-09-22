select(cast(student.twocf.age as integer)/cast(student.threecf.gpa as float)) from student where row_key=10;
