import org.junit.Test
class DoSome {
    var some:String;
    constructor(){
        this.some = "";
    }
    constructor(some: String){
        this.some = some;
    }

    fun test1(hello:()->Unit){
        hello.invoke()
    }
    fun hi() {
        print("hello Hi")
    }

    fun hello(some: String) {
        println(this.some)
    }
}



class MyTest {

    fun test(hello: DoSome.() -> Unit){
        println(hello(DoSome()))
    }

    @Test
    fun main() {
        DoSome().test1 {
            println("hi1")
        }
        println("hello")
        test {
            this.hi()
        }
    }
}
