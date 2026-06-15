import androidx.collection.MutableObjectList

class day2 {


}

fun main() {
    val mutableList = mutableListOf<Student>()
    mutableList.add(Student("zhangsan", 95))
    mutableList.add(Student("lisi", 98))
    mutableList.add(Student("wangwu", 45))
    printExcellentStudents(mutableList)
    findFirstFailStudent(mutableList)
    updateScores(mutableList, 5)
}

fun printExcellentStudents(students: List<Student>) {

    run {
        students.forEach forEach@{
            if (it.score <90) {
//                continue@run
                return@forEach
            }
            println("${it.name}: ${it.score}")
        }
    }

    println("=== 优秀学生名单 ===")
    for (s:Student in students) {
        if (s.score < 90) {
            continue
        }
        println("${s.name}: ${s.score}")
    }
}

fun findFirstFailStudent(students: List<Student>) {
    students.forEach forEach@{
        if (it.score < 60) {
            println("${it.name}: ${it.score}")
            return@forEach
        }
    }

    for (s:Student in students) {
        if (s.score < 60) {
            println("${s.name}: ${s.score}")
            break
        }
    }
}

fun updateScores(students: List<Student>, bonus:Int) {
    students.forEach {
        if (it.score > (100 - bonus)) {
            return@forEach
        }
        val temp:Int = it.score + bonus
        println("${it.name}: ${temp}")
    }

    for (s:Student in students) {
        if (s.score > (100 - bonus)) {
            continue
        }
        val temp:Int = s.score + bonus
        println("${s.name}: ${temp}")
    }
}

class Student(var name1:String, var score1:Int) {
    lateinit var name:String
    var score:Int

        init {
            name = name1
            score = score1
        }
    fun isPass(): Boolean = score >= 60
}