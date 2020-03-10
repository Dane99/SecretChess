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

  def makeMove(moveX: Int, moveY: Int): Unit = {
    if(checkValidity(moveX, moveY)) {
      val oldX = _posX
      val oldY = _posY
      _posX = moveX
      _posY = moveY
      _hasMoved = true
      board.takeTurn(team, this, oldX, oldY)
    } else {
      ()
    }
  }

  def vectIsClear(moveX: Int, moveY: Int): Boolean = {
    if(moveX == posX) {
      (posY + 1 until moveY).forall(y => board.tiles(moveX)(y).occupant == null)
    } else if(moveY == posY) {
      (posX until moveX).forall(x => board.tiles(x)(moveY).occupant == null)
    } else {
      (posX until moveX).forall(a => board.tiles(a)(posY + a - posX).occupant == null)
    }
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