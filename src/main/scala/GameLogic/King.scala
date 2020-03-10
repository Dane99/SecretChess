package GameLogic

class King extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(vectIsClear(moveX, moveY)) {
      !(moveX == 0 && moveY == 0) && (moveX == 1 || moveX == 0) && (moveY == 1 || moveY == 0)
    } else false
  }
}
