import kotlin.random.Random

class day4 {

}
// 1. 枚举类：固定状态集合
// 【企业注记】：通常配合数据库的 status 字段（tinyint）映射，比如 0=PENDING, 1=PAID...
enum class OrderStatus {
    PENDING, PAID, CANCELLED
}

// 2. 数据类：自动生成 toString/equals/copy
// 【企业注记】：copy() 非常常用，比如后台更新订单状态时，
// 直接 order.copy(status = PAID) 生成新对象，保持不可变性。
data class Order(
    val orderId: String,
    val amount: Double,
    val status: OrderStatus
) {
    // 3. 伴生对象：相当于 Java 的 static
    // 【企业注记】：通常在这里定义常量 companion object { const val TAG = "Order" }
    // 或者作为静态工厂方法，比直接调用构造函数更语义化
    companion object {
        fun create(amount: Double): Order {
            val id  = "ORD- ${Random.nextInt(1000, 9999)}"
            return Order(id, amount, OrderStatus.PENDING)
        }
    }
}

// 4. 密封类：限定结果类型（只能有 Success, Failure, Loading）
// 【企业注记】：这是最标准的网络请求封装写法！在 Android 的 ViewModel 里，
// 用 sealed class 包装 LiveData/Flow，UI 层用 when 处理，绝不漏掉 Loading 状态。
sealed class OrderResult {
    data class Success(val order:Order) : OrderResult()
    data class Failure(val errorMsg: String) : OrderResult()
    object Loading : OrderResult()
}

// 5. 单例对象（Service 层）
// 【企业注记】：在 Koin/Spring 中，Service 默认就是单例。这里模拟业务逻辑。
object OrderService {
    fun pay(order: Order) : OrderResult {
        println("模拟支付")

        return if (order.amount > 200.0) {
            OrderResult.Failure("余额不足，支付失败")
        } else {
            val updatedOrder = order.copy(status = OrderStatus.PAID)
            OrderResult.Success(updatedOrder)
        }
    }
}

// 模拟 Android/Java 的点击监听接口
interface PaymentClickListener {
    fun onClick(order: Order)
}

fun main() {
    // 6. 对象表达式：创建匿名内部类（一次性使用）
    // 【企业注记】：在 Android 中，button.setOnClickListener(object : View.OnClickListener{...})
    // 在后台 Java 中， new Thread(new Runnable(){...}) 的完美替代品。
    val clickListener = object : PaymentClickListener {
        override fun onClick(order: Order) {
            println(">>> 用户点击了支付按钮，订单号: ${order.orderId}")

            // 调用单例 Service
            val result = OrderService.pay(order)

            // 【重点】：when 处理密封类，不需要 else！因为编译器知道只有这 3 种可能。
            // 如果以后新增子类，这里没处理，编译器会报错，这就是密封类的威力！
            when (result) {
                is OrderResult.Loading -> println("⏳ 显示加载转圈圈...")
                is OrderResult.Success -> println("✅ 支付成功！新状态：${result.order.status}")
                is OrderResult.Failure -> println("❌ 支付失败：${result.errorMsg}")
            }
        }
    }

    // 测试业务
    // 1. 使用伴生对象创建订单
    val order1 = Order.create(amount = 99.0)
    val order2 = Order.create(amount = 299.0)

    // 2. 触发点击监听
    clickListener.onClick(order1)
    println("--- 分割线 ---")
    clickListener.onClick(order2)
}