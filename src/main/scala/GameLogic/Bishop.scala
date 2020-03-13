package GameLogic

class Bishop extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(isPathClear(moveX, moveY)) {
      math.abs(moveX - posX) == math.abs(moveY - posY)
    } else {
      false
    }
  }
}