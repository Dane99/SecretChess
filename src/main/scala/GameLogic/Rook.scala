package GameLogic

class Rook extends Piece {
  def getValue(): Int = 5
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    // Note: this is an incomplete implementation of the rules involved in castling,
    if ((moveX != posX || moveY != posY)
      && (moveX == posX || moveY == posY)) {
      isPathClear(moveX, moveY, true)
    }
    else {
      false
    }
  }
}
