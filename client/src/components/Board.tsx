import React, { Component } from 'react';
import Piece from './Piece';

import "./Board.css";
import PieceModel from '../models/PieceModel';
import PositionModel from '../models/PositionModel';

export interface BoardProps {
    pieces : PieceModel[]
    perspective: string 

    gameId : string
    turnCount?: number

    selected?: PositionModel
    possibleMoves?: PositionModel[]
    fogmap?: boolean[]

    draggableColor?: string

    onBoardSquareClick(x : number, y : number, e : any) : void
    onHandleMove?(from: PositionModel, to: PositionModel): void
}

export interface BoardState {
    dragStart? : PositionModel
}

class Board extends Component<BoardProps, BoardState> {

    constructor(props : BoardProps) {
        super(props)
        this.state = {
        };

        this.onDragPieceStart = this.onDragPieceStart.bind(this);
        this.onDragPieceDrop = this.onDragPieceDrop.bind(this);
        this.onDragPieceOver = this.onDragPieceOver.bind(this);

        this.onSquareClick = this.onSquareClick.bind(this);
    }

    onSquareClick(x : number, y : number, e : any) {
        if (this.props.selected && this.props.onHandleMove && this.props.possibleMoves) {
            for (let possilbeMove of this.props.possibleMoves) {
                if (possilbeMove.x == x && possilbeMove.y == y) {
                    var to : PositionModel = {x, y}
                    this.props.onHandleMove(this.props.selected, to)
                    return
                }
            }
        }

        this.props.onBoardSquareClick(x, y, e);
    }

    onDragPieceStart(x : number, y: number, e : any) {
        this.props.onBoardSquareClick(x, y, e)

        var p : PositionModel = { x, y}
        this.setState({
            dragStart: p
        })
    }

    onDragPieceDrop(x: number, y: number, e : any) {
        var to : PositionModel = {x, y}
        if (!this.state.dragStart || (this.state.dragStart.x == to.x && this.state.dragStart.y == to.y)) {
            return
        }

        if (this.props.onHandleMove && this.state.dragStart) {
            this.props.onHandleMove(this.state.dragStart, to)
        }
    }

    onDragPieceOver(e : any ) {
        e.preventDefault();
    }

    render() {

        if (!this.props.pieces) {
            return "No Data";
        } 

        var perspective = this.props.perspective;
        if (perspective != "BLACK" && perspective != "WHITE") {
            perspective = "WHITE";
        }

        var tbody = [];
        for (var y= 0; y < 8; y++) {
            
            var row=[];
            for (var x = 0; x < 8; x++) {
                var id = x + (8 * y);
                var backgroundColor = (x+y) % 2 == 1 ? 'grey' : 'white';
                var classes = "boardsquare"

                if (this.props.possibleMoves) {
                    for (var i = 0; i < this.props.possibleMoves.length; i++) {
                        var highlight = this.props.possibleMoves[i];
                        if (highlight.x == x && highlight.y == y) {
                            backgroundColor = "blue";
                        }
                    }
                }

                if (this.props.selected) {
                    if (this.props.selected.x == x && this.props.selected.y == y) {
                        backgroundColor = "red";
                    }
                }

                if (this.props.fogmap) {
                    if (this.props.fogmap[id] && (backgroundColor == 'grey' || backgroundColor == "white")) {
                        backgroundColor = "silver"
                    }
                }

                var piece = <h2>placeholder</h2>
                if (this.props.pieces[id] && this.props.draggableColor && this.props.pieces[id].color == this.props.draggableColor) {
                    piece = <Piece onDragStart={this.onDragPieceStart} piece={this.props.pieces[id]} x={x} y={y} />
                } else {
                    piece = <Piece piece={this.props.pieces[id]} x={x} y={y} />
                }

                row.push(<td style={{backgroundColor}} data-x={x} data-y={y} onDragOver={this.onDragPieceOver} onDrop={this.onDragPieceDrop.bind(this, x, y)} onClick={this.onSquareClick.bind(this, x, y)} className={classes} key={id}>
                    {piece}
                </td>);
            }

            if (perspective == "BLACK") {
                tbody.push(<tr key={y}>{row}</tr>);
            } else {
                tbody.unshift(<tr key={y}>{row}</tr>);
            }
        }

        return (
            <table className="board">
                <tbody>{tbody}</tbody>
            </table>
        )
    }
}

export default Board;