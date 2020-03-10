package GameLogic

import GameLogic.Team.Team

import scala.collection.mutable

class Board {
  val tiles: Array[Array[Tile]] = Array.fill[Array[Tile]](8)(Array.fill[Tile](8)(null))
  private var currentTeam = Team.White

  for(i <- 0 until 8) {
    for(j <- 0 until 8) {
      if (i == 0 || i == 7) {
        if (j == 0 || j == 7) tiles(i)(j) = new Tile(new Rook)
        else if(j == 1 || j == 6) tiles(i)(j) = new Tile(new Knight)
        else if(j == 2 || j == 5) tiles(i)(j) = new Tile(new Bishop)
        else if(j == 3) tiles(i)(j) = new Tile(new Queen)
        else tiles(i)(j) = new Tile(new King)
        tiles(i)(j).occupant.initTeam(if (i == 0) Team.Black else Team.White)
        tiles(i)(j).occupant.initBoard(this)
        tiles(i)(j).occupant.initPos(i, j)
      } else if(i == 1 || i == 6) {
        tiles(i)(j) = new Tile(new Pawn)
        tiles(i)(j).occupant.initTeam(if(i == 1) Team.Black else Team.White)
        tiles(i)(j).occupant.initBoard(this)
        tiles(i)(j).occupant.initPos(j, i)
      } else tiles(i)(j) = new Tile(null)
    }
  }

//  def computeTeamVision(team: Team): Array[Array[Boolean]] = {
//    val visionArr: Array[Array[Boolean]] = tiles.map(_.map(x => false))
//    allPieces.filter(_.team == team).foreach {p =>
//      val pVision = p.computeVision
//      for(x <- visionArr.indices) {
//        for(y <- visionArr(x).indices) {
//          if(pVision(x)(y)) visionArr(x)(y) = true
//        }
//      }
//    }
//    visionArr
//  }

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