package GameLogic

import GameLogic.Team.Team

import scala.collection.mutable

case class Move(piece: Piece, x: Int, y: Int)

class Board(botsEnabled: Boolean) {
  var tiles: Array[Array[Tile]] = Array.fill[Array[Tile]](8)(Array.fill[Tile](8)(null))
  private var currentTeam = Team.White
  private var bot = new Bot(this)
  private var undoMove = mutable.Stack[Move]()
  private var lastMoveCapture = mutable.Stack[Piece]()

  for(x <- 0 until 8) {
    for(y <- 0 until 8) {
      val team = if(y == 0 || y == 1) Team.Black else Team.White
      if (y == 0 || y == 7) {
        x match {
          case 0 => tiles(x)(y) = new Tile(new Rook)
          case 1 => tiles(x)(y) = new Tile(new Knight)
          case 2 => tiles(x)(y) = new Tile(new Bishop)
          case 3 => tiles(x)(y) = new Tile(new Queen)
          case 4 => tiles(x)(y) = new Tile(new King)
          case 5 => tiles(x)(y) = new Tile(new Bishop)
          case 6 => tiles(x)(y) = new Tile(new Knight)
          case 7 => tiles(x)(y) = new Tile(new Rook)
        }
      }
      else if(y == 1 || y == 6) tiles(x)(y) = new Tile(new Pawn)
      else tiles(x)(y) = new Tile(null)

      if(tiles(x)(y).occupant != null){
        tiles(x)(y).occupant.initTeam(team)
        tiles(x)(y).occupant.initBoard(this)
        tiles(x)(y).occupant.initPos(x, y)
      }
    }
  }

  def copy(): Board = {
    println("Board was copied")
    val board = new Board(botsEnabled)
    board.setTurn(currentTeam)
    board.tiles = for(inner <- tiles) yield {
      for (elem <- inner) yield {
        elem.copy
      }
    }
    board
  }

  // Move piece from x1,x2 to x2, y2.
  def makeMove(move: Move, force: Boolean = false): Boolean = {
    if(move.piece == null) {
      println("Bad move due to null piece")
      return false
    }
    if(currentTeam != move.piece.team) {
      println("Bad move due to wrong team")
      return false
    }

    val x1 = move.piece.posX
    val y1 = move.piece.posY
    val p1 = move.piece
    val x2 = move.x
    val y2 = move.y
    val p2 = tiles(x2)(y2)
    if(!(x1 != x2 || y1 != y2)){
      println("Bad move due to no movement")
      return false
    }
    if((force || p1.checkValidity(x2, y2)) && (x1 != x2 || y1 != y2)) {
      if(!force) {
        undoMove.push(Move(p1, x1, y1))
        lastMoveCapture.push(tiles(x2)(y2).occupant)
      }
      //TODO: if(tiles(move.x)(move.y).occupant.isInstanceOf[King]) println("You Win!")
      tiles(x2)(y2).update(p1)
      tiles(x2)(y2).occupant.updatePosition(x2, y2)
      tiles(x1)(y1).update(null)
      switchTurn
      true
    } else {
      false
    }
  }
  
  def makeUndoMove(): Unit = {
    val capture = lastMoveCapture.pop
    val move = undoMove.pop
    val x1 = move.piece.posX
    val y1 = move.piece.posY
    tiles(move.x)(move.y).update(move.piece)
    tiles(move.x)(move.y).occupant.revertPosition(move.x, move.y)
    tiles(x1)(y1).update(null)
    if(capture != null) tiles(capture.posX)(capture.posY).update(capture)
    switchTurn()
  }

  def update(): Unit = {
    if(currentTeam == Team.Black && botsEnabled){
        bot.takeTurn
    }
  }

  def getTeamVision(team: Team): Array[Array[Boolean]] = {
    val vision = Array.fill(8, 8)(false)
    for(piece <- allPieces if piece.team == team){
      for(x <- 0 until 8; y <- 0 until 8){
        vision(x)(y) |= piece.checkValidity(x, y)
        vision(piece.posX)(piece.posY) |= true
      }
    }
    vision
  }

  def getAllMoves(): List[Move] = {
    val buff = new mutable.ListBuffer[Move]()
    for(piece <- allPieces){
      for(x <- 0 until 8; y <- 0 until 8){
        if(piece.checkValidity(x, y)){
          buff.addOne(Move(piece, x, y))
        }
      }
    }
    buff.toList
  }

  def allPieces: List[Piece] = {
    val buff = new mutable.ListBuffer[Piece]()
    for(arr <- tiles) {
      for(x <- arr) {
        if (x.occupant != null) {
          buff.addOne(x.occupant)
        }
      }
    }
    buff.toList
  }


  def hasFriend(posX: Int, posY: Int, team: Team): Boolean = {
    if(hasPiece(posX, posY)) {
      tiles(posX)(posY).occupant.team == team
    } else false
  }
  def hasEnemy(posX: Int, posY: Int, team: Team): Boolean = {
    if(hasPiece(posX, posY)) {
      tiles(posX)(posY).occupant.team != team
    } else false
  }
  def hasPiece(posX: Int, posY: Int): Boolean = tiles(posX)(posY).occupant != null

  def switchTurn(): Unit = {
    if(currentTeam == Team.White) currentTeam = Team.Black else currentTeam = Team.White
  }

  def getCurrentTeam: Team = currentTeam

  def setTurn(team: Team): Unit = {
    currentTeam = team
  }
}