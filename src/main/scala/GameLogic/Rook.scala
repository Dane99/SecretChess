package GameLogic

class Rook extends Piece {
  def checkValidity(moveX: Int, moveY: Int): Boolean = {
    if(vectIsClear(moveX, moveY)) {
      ((moveX == _posX && moveY != _posY) || (moveX != _posX && moveY == _posY)) &&
        (!board.hasFriend(moveX, moveY, team) || board.tiles(moveX)(moveY).occupant.isInstanceOf[King])
    } else false
  }
}
