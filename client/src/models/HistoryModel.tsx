import BoardModel from "./BoardModel";
import PieceModel from "./PieceModel";
import MoveModel from "./MoveModel";

export default interface HistoryModel {
    turnCount : number
    maxTurnCount: number
    board : BoardModel
    fogBoardBlack: BoardModel 
    fogBoardWhite: BoardModel

    gameId: string

    graveyard: PieceModel[]
    moves: MoveModel[]

    playerWhite: string,
    playerBlack: string
}