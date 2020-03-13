package GameLogic

class King extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if ((moveX != posX || moveY != posY) && (math.abs(moveX - posX) == 1 || math.abs(moveY - posY) == 1)) {
      isPathClear(moveX, moveY, true)
    }
    else {
      false
    }
  }
}
