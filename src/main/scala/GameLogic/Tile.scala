package GameLogic

class Tile (private var _occupant: Piece) {
  def occupant: Piece = _occupant

  def update(newOcc: Piece): Unit = _occupant = newOcc

  def copy(): Tile = {
    new Tile(occupant)
  }
}