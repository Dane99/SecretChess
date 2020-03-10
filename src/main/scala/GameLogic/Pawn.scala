package GameLogic

class Pawn extends Piece {
  def checkValidity(moveY: Int, moveX: Int): Boolean = {
    println("MoveY:" + moveY + " MoveX:" + moveX + " PosY:" + _posY + " PosX:" + _posX)
    if(vectIsClear(moveX, moveY)) {
      if (moveX == _posX && !board.hasPiece(moveX, moveY)) {
        team match {
          case Team.Black => moveY - _posY == 2 || moveY - _posY == 1
          case Team.White => _posY- moveY == 2 || _posY - moveY == 1
          case _ => throw new Exception("Team must be Black or White.")
        }
      }
      else if (math.abs(moveX - _posX) == 1
        && (board.hasEnemy(moveX, moveY, team)
        || (if (team == Team.White) board.hasEnemy(moveX, math.min(7, moveX + 1), team)
      else board.hasEnemy(moveY, math.max(0, moveX -  1), team) && !board.hasFriend(moveY, moveX, team)))) {
        println("world ending 1")
        team match {
          case Team.Black => moveX - _posX == 1
          case Team.White => _posX - moveX == 1
          case _ => throw new Exception("Team must be Black or White.")
        }
      } else {
        println("world ending")
        false
      }
    } else false
  }
}