import GameLogic.{Board, Team}

object HelloWorld extends App {
  println("Hello World!")

  val brd = new Board

//  val vis = brd.computeTeamVision(Team.Black)

  val pawn = brd.allPieces.take(2).last

  println(pawn.toString())

//  println(pawn.checkValidity(2, 0))

//  println(pawn.vectIsClear(2, 0))

  pawn.makeMove(2, 1)

  println(pawn.posX, pawn.posY)
  println(brd.tiles.toList.map(_.toList.map(_.occupant).toString() + "\n").toString)
}
