package top.potmot.model.associations.real.manyToMany

const val javaRealFkResult = """
[(top/potmot/Course.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinTable;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "COURSE")
public interface Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany
    @JoinTable(
            name = "COURSE_STUDENT_MAPPING",
            joinColumnName = "COURSE_ID",
            inverseJoinColumnName = "STUDENT_ID"
    )
    List<Student> students();

    @IdView("students")
    List<Long> studentIds();
}
), (top/potmot/Student.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "STUDENT")
public interface Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany(mappedBy = "students")
    List<Course> courses();

    @IdView("courses")
    List<Long> courseIds();
}
)]
"""

const val javaFakeFkResult = """
[(top/potmot/Course.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ForeignKeyType;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.JoinTable;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "COURSE")
public interface Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany
    @JoinTable(
            name = "COURSE_STUDENT_MAPPING",
            joinColumns = {
                    @JoinColumn(name = "COURSE_ID", foreignKeyType = ForeignKeyType.REAL),
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "STUDENT_ID", foreignKeyType = ForeignKeyType.REAL),
            }
    )
    List<Student> students();

    @IdView("students")
    List<Long> studentIds();
}
), (top/potmot/Student.java, package top.potmot;

import java.util.List;
import org.babyfish.jimmer.sql.Column;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.GenerationType;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToMany;
import org.babyfish.jimmer.sql.Table;

/**
 * @author 
 */
@Entity
@Table(name = "STUDENT")
public interface Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    long id();

    @ManyToMany(mappedBy = "students")
    List<Course> courses();

    @IdView("courses")
    List<Long> courseIds();
}
)]
"""

const val kotlinRealFkResult = """
[(top/potmot/Course.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "COURSE")
interface Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany
    @JoinTable(
        name = "COURSE_STUDENT_MAPPING",
        joinColumnName = "COURSE_ID",
        inverseJoinColumnName = "STUDENT_ID"
    )
    val students: List<Student>

    @IdView("students")
    val studentIds: List<Long>
}
), (top/potmot/Student.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "STUDENT")
interface Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany(mappedBy = "students")
    val courses: List<Course>

    @IdView("courses")
    val courseIds: List<Long>
}
)]
"""

const val kotlinFakeFkResult = """
[(top/potmot/Course.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ForeignKeyType
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.JoinTable
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "COURSE")
interface Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany
    @JoinTable(
        name = "COURSE_STUDENT_MAPPING",
        joinColumns = [
            JoinColumn(name = "COURSE_ID", foreignKeyType = ForeignKeyType.REAL),
        ],
        inverseJoinColumns = [
            JoinColumn(name = "STUDENT_ID", foreignKeyType = ForeignKeyType.REAL),
        ]
    )
    val students: List<Student>

    @IdView("students")
    val studentIds: List<Long>
}
), (top/potmot/Student.kt, package top.potmot

import org.babyfish.jimmer.sql.Column
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Table

/**
 * @author 
 */
@Entity
@Table(name = "STUDENT")
interface Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long

    @ManyToMany(mappedBy = "students")
    val courses: List<Course>

    @IdView("courses")
    val courseIds: List<Long>
}
)]
"""
