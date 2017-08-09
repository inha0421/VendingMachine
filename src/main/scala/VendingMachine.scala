import scala.io.StdIn


/* 상품 클래스 */
case class menu(name: String, num: Int, price: Int) // 이름, 재고량, 가격


/* 기능 구현

 - inputMoney() : 돈 투입
 - moneyCheck() : 메뉴 선택 전에 돈을 투입했는지 확인처리
 - showMenu() : 투입된 돈으로 구입 가능한 메뉴 보여주기
 - perchase() : 물품 구입
 - matchMenu() : 메뉴 선택
 - stochCheck() : 재고 처리
 - change() : 잔돈 반환

 */

class repository {

  var coke = menu("콜라", 3, 1000)
  var sprite = menu("사이다", 5, 1500)

  var nameOfCoke = coke.name
  var nameOfSprite = sprite.name

  var numOfCoke = coke.num
  var numOfSprite = sprite.num

  def inputMoney(): Int = {
    var bb = scala.io.StdIn.readInt()
    bb
  }

  def moneyCheck(bb: Int): Int = {
    var m1 = bb

    def inputCheck(m1: Any) = m1 match {
      case 1 => println(" \n돈을 먼저 넣어주세요.")
      case 2 => println("\n돈을 먼저 넣어주세요.")
      case inputInt: Int => showMenu(bb)
      case _ => println("입력 오류가 발생하였습니다.")
    }

    inputCheck(m1)
    m1
  }

  def showMenu(m: Int): Unit = {

    var input = m // 처음 받은 돈

    println("-------------------------------------------")
    println("현재 투입된 금액은 총 " + input + "원 입니다.\n")
    println("* 표시가 된 물품은 선택 가능한 물품입니다.\n")

    // 최소 구매 금액보다 작은 경우 추가 투입

    if (input < coke.price) {

      println("(메뉴) 1. 콜라 : ( ), 2. 사이다 : ( )\n")
      println("(재고) 1. 콜라 : "+ numOfCoke + "개,   2. 사이다 : " + numOfSprite + "개\n")
      println("금액이 모자라 선택 할 수 있는 메뉴가 없습니다.\n")
      println("돈을 더 넣어주세요.")
      println("-------------------------------------------")

      var rest = scala.io.StdIn.readInt() //추가로 받는 돈
      showMenu(input + rest)

    }

    else {

      if (input >= coke.price && input < sprite.price) {

        println("1. 콜라 : (*), 2. 사이다 : ( )\n")
        println("메뉴를 선택해 주세요.")

      }

      else if (input >= sprite.price) {

        println("1. 콜라 : (*), 2. 사이다 : (*) \n")
        println("메뉴를 선택해 주세요.")

      }

      println("-------------------------------------------")

      var selection = scala.io.StdIn.readInt()
      perchase(selection, input)
    }

  }

  def perchase(x: Int, y: Int) { //x : 메뉴 번호, y: 받은돈

    var select = x // 선택한 메뉴
    var input = y // 받은 돈
    var output = 0 // 잔돈


    //matchMenu(select)


    def matchMenu(x: Int): Any = x match {

      case 1 => isEnough(input, coke.name, coke.num, coke.price)

      case 2 => isEnough(input, sprite.name, sprite.num, sprite.price)

      case _ => println("잘못 입력 하셨습니다.111")

    }


    def isEnough(input: Int, name: String, num: Int, price: Int): Unit = {

      var input2 = input
      var nameOfSelection = name
      var numOfSelection = num
      var priceOfSelection = price

      if (input2 < priceOfSelection) {
        println("\n투입한 금액이 부족하여 선택 할 수 없습니다.\n")
        showMenu(input2)
      }

      else {
        println("\n" + nameOfSelection + "를 선택하셨습니다.\n")

        if (numOfSelection <= 0) {
          println("재고가 없어 구매 할 수 없습니다.")
          output = input2
        }

        else {

          output = input2 - priceOfSelection

          if (nameOfSelection == coke.name) numOfCoke = numOfCoke - 1
          else if (nameOfSelection == sprite.name) numOfSprite = numOfSprite - 1

          stockCheck(numOfCoke, numOfSprite)

          println("결제가 정상적으로 처리되었습니다.")
          println("남은 잔액은 " + output + "원 입니다.\n")

        }
      }

    }

    matchMenu(select)

    if (output > 0) {

      println("잔돈을 반환 하시겠습니까?(Y/N)")
      val changeSelect = scala.io.StdIn.readLine()
      change(changeSelect)

    }

    else if (output == 0) {
      println("판매를 종료합니다. 안녕히 가세요.")
    }

    def change(changeSelect: String): Any = changeSelect match {

      case "y" => println("판매를 종료합니다. 잔돈 " + output + "원을 가져가세요.")
        output = 0

      case "n" => showMenu(output)


      case _ => "잘못 입력하셨습니다."

    }


  }


  def stockCheck(a: Int, b: Int): Unit = {

    var stockOfCoke = a
    var stockOfSprite = b

    this.numOfCoke = stockOfCoke
    this.numOfSprite = stockOfSprite

  }


}


object VendingMachine {

  def main(args: Array[String]): Unit = {

    val Repository = new repository

    val priceOfCoke = Repository.coke.price
    val priceOfSprite = Repository.sprite.price

    // val numberOfCoke = Repository.numOfCoke
    //val numberOfSprite = Repository.numOfSprite


    val ON = "on"
    val OFF = "off"
    var power = ON


    while (power == "on") {


      val numberOfCoke = Repository.numOfCoke
      val numberOfSprite = Repository.numOfSprite

      println("\n-------------------------------------------")
      println("****** 자판기 프로그램을 시작합니다! ******\n")
      println("(메뉴) 1.콜라 : " + priceOfCoke + "원, 2.사이다 : " + priceOfSprite + "원\n")
      println("(재고) 1.콜라 : " + numberOfCoke + "개,\t" + "2.사이다 : " + numberOfSprite + "개\n")
      println("*******************************************")
      println("-------------------------------------------\n")

      println("돈을 넣어주세요.")


      // 돈을 넣지않고 메뉴 선택하면 경고
      var money = Repository.moneyCheck(Repository.inputMoney())


    }
    println("-------------------------------------------")
  }

}

