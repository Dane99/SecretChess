package GameLogic

class Knight extends Piece {
  def getValue(): Int = 3
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    ((math.abs(moveX - _posX) == 2 && math.abs(moveY - _posY) == 1) ||
      (math.abs(moveX - _posX) == 1 && math.abs(moveY - _posY) == 2)) && !board.hasFriend(moveX, moveY, team)
  }
}
