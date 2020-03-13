package GameLogic

import GameLogic.Team.Team

trait Piece {
  protected var _posX: Int = _
  protected var _posY: Int = _
  protected var _hasMoved: Boolean = false
  protected var _team: Team = null
  protected var board: Board = null

  def initPos(x: Int, y: Int): Unit = {
    _posX = x
    _posY = y
  }

  def initBoard(brd: Board): Unit = board = brd
  def initTeam(tm: Team): Unit = _team = tm

  def makeMove(moveX: Int, moveY: Int): Boolean = {
    if(checkValidity(moveX, moveY) && (moveX != posX || moveY != posY)) {
      val oldX = _posX
      val oldY = _posY
      _posX = moveX
      _posY = moveY
      _hasMoved = true
      board.takeTurn(team, this, oldX, oldY)
      true
    } else {
      false
    }
  }

  // Exclusive to start, inclusive to end
  def getPath(startX: Int, startY: Int, endX: Int, endY: Int): List[(Int, Int)] = {
    if(startX == endX){
      val dirY = if (endY - startY > 0) 1 else -1
      val xCords = List.fill(math.abs(startY - dirY))(startX)
      xCords.zip((startY + dirY to endY by dirY).toList)
    }
    else if(startY == endY){
      val dirX = if (endX - startX > 0) 1 else -1
      val yCords = List.fill(math.abs(startX - dirX))(startY)
      ((startX + dirX to endX by dirX).toList).zip(yCords)
    }
    else{
      val dirX = if (endX - startX > 0) 1 else -1
      val dirY = if (endY - startY > 0) 1 else -1
      val xCords = (startX + dirX to endX by dirX).toList
      val yCords = (startY + dirY to endY by dirY).toList
      xCords.zip(yCords)
    }
  }

  def isPathClear(moveX: Int, moveY: Int, captureMove: Boolean): Boolean = {
    val path = getPath(posX, posY, moveX, moveY).dropRight(if(captureMove && board.hasEnemy(moveX, moveY, team)) 1 else 0)
    println("Path:" + path)
    path.forall(tile => board.tiles(tile._1)(tile._2).occupant == null)
  }

//  def computeVision: Array[Array[Boolean]] = {
//    (for(x <- board.tiles.indices) yield {
//      (for(y <- board.tiles(x).indices) yield {
//        checkValidity(x, y)
//      }).toArray
//    }).toArray
//  }

  def checkValidity(moveX: Int, moveY: Int): Boolean

  def posX: Int = _posX
  def posY: Int = _posY
  def hasMoved: Boolean = _hasMoved
  def team: Team = _team
}