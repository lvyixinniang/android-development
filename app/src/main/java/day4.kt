import kotlin.random.Random

class day4 {

}
// pending, paid, cancelled
enum class OrderStatus {
    PENDING, PAID, CANCELLED
}

data class Order(
    val orderId: String,
    val amount: Double,
    val status: OrderStatus
) {
    companion object {
        fun create(amount: Double): Order {
            val id  = "ORD- ${Random.nextInt(1000, 9999)}"
            return Order(id, amount, OrderStatus.PENDING)
        }
    }
}

sealed class OrderResult {
    data class Success(val order:Order) : OrderResult()
    data class Failure(val errorMsg: String) : OrderResult()
    object Loading : OrderResult()
}

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

interface PaymentClickListener {
    fun onClick(order: Order)
}

fun main() {
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