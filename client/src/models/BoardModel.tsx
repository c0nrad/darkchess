import PieceModel from "./PieceModel";

export default interface BoardModel {
    id: string,
    pieces: PieceModel[]
}