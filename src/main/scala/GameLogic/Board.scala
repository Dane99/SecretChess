package GameLogic

import GameLogic.Team.Team

import scala.collection.mutable

class Board {
  val tiles: Array[Array[Tile]] = Array.fill[Array[Tile]](8)(Array.fill[Tile](8)(null))
  private var currentTeam = Team.White

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

  def allPieces: List[Piece] = {
    val buff = new mutable.ArrayBuffer[Piece]()
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
  def takeTurn(team: Team, piece: Piece, oldX: Int, oldY: Int): Unit = {
    tiles(oldX)(oldY).update(null)
    val movedTile = tiles(piece.posX)(piece.posY)
    movedTile.update(piece)
  }
  def switchTurn(): Unit = {
    if(currentTeam == Team.White) currentTeam = Team.Black else currentTeam = Team.White
  }
}