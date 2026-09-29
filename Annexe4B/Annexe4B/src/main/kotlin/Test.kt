fun main(){
//    var mot:String ?= null
//        println(mot!!.length)

//    var mot:String = "abc"
//    var nombre:Int? = mot as Int
//    println(nombre)

    val listeAvecDesNull: List<String?> = listOf("hugo", "loic", null, "eddy")
    for (item in listeAvecDesNull) {
        item?.let { println(item.length) }
    }



}