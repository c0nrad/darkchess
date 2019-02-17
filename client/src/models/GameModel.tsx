import PieceModel from "./PieceModel";
import MoveModel from "./MoveModel";
import BoardModel from "./BoardModel";

export default interface GameModel {
    id: string

    board : BoardModel
    graveyard: PieceModel[]
    moves: MoveModel[]
    fogmap: boolean[]

    whitePlayer: string,
    blackPlayer: string

    isGameOver: boolean 
    isWhiteWinner: boolean
}