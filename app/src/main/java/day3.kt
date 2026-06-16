class day3 {


}

interface Drawable{
    fun draw(){
        print("正在绘制形状...")
    }
}

abstract class Shape(protected val color: String): Drawable{
    abstract fun getArea():Double

    open fun info() {
        print("颜色：${color}")
    }
}

class Circle(color:String, radius:Double):Shape(color){
    private val radius:Double = radius


    override fun getArea(): Double {
        return 3.14*radius*radius
    }

    override fun info() {
        print("r= ${radius}, area=${3.14*radius*radius}")
    }
    override  fun draw() {
        print("圆形")
    }
}

class Rectangle(color:String, width:Int, height:Int):Shape(color) {
    private val width: Int = width
    private val height: Int = height

    override  fun getArea(): Double {
        return width*height.toDouble()
    }

    override fun info() {
        print("w:${width}, h:${height}, area:${width * height}")
    }

    override  fun draw() {
        print("矩形")
    }
}

class Canvas(name: String) {
    private val name: String = name
    inner class Layer(private val layerName: String){
        fun showCanvasInfo(){
            print("${this@Canvas.name}   ${layerName}")
        }
    }

    class Tool {
        fun useTool() {
            print("使用画布工具")
        }
    }
}

fun main() {
    var lists:List<Shape> = listOf<Shape>(
        Circle("红色", 5.0),
        Rectangle("蓝色",4, 5)
    )
    for (temp:Shape in lists) {
        temp.draw()
        temp.info()
    }

    val canvas = Canvas("我的画布")
    val layer = canvas.Layer("前景层")
    layer.showCanvasInfo()

    val tool = Canvas.Tool()
    tool.useTool()


}