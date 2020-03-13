package GameLogic

class Bishop extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(math.abs(moveX - posX) == math.abs(moveY - posY)) {
      isPathClear(moveX, moveY, true)
    } else {
      false
    }
  }
}