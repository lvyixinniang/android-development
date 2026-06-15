class day1 {


}

fun main() {
    // 我不会kotlin的输入
    // 只能设置假数据了

    lateinit var grade: String // 变量
    lateinit var stage: String
    val grade2 = 100 // 常量，是不是不可变

    when(grade2){
        in 0 .. 59 -> {
            println("不及格")
            grade = "不及格"
        }
        in 60 .. 89 -> {
            println("良好")
            grade = "良好"
        }
        in 90 .. 100 -> {
            println("优秀")
            grade = "优秀"
        }
        else ->{
            println("无效成绩")
            grade = "无效成绩"
        }
    }

    when(grade2) {
        in 0 ..< 50 -> {
            stage = "需要大幅提高"
        }
        in 50 .. 59 -> {
            stage = "差一点及格，再努力一点"
        }
        in 85 .. 100 -> {
            stage = "恭喜！达到优秀线以上"
        }
        else -> {
            stage = "保持桀纣，继续进步"
        }
    }

    println("你的成绩是 ${grade} 分，评级为：${stage}.")
}