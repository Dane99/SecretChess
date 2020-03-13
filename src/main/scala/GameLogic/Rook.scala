package GameLogic

class Rook extends Piece {
  def canCastle(moveX: Int, moveY: Int): Boolean ={
    hasMoved && board.tiles(moveX)(moveY).occupant.isInstanceOf[King] && (!board.tiles(moveX)(moveY).occupant.hasMoved)
  }

  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    // Note: this is an incomplete implementation of the rules involved in castling,
    if ((moveX != posX || moveY != posY)
      && (moveX == posX || moveY == posY || canCastle(moveX, moveY))) {
      isPathClear(moveX, moveY, true)
    }
    else {
      false
    }
  }
}
