import React, { Component } from 'react';
import './Piece.css';
import PieceModel from '../models/PieceModel';

import WhiteBishop from '../img/white-bishop.png'; 
import WhiteKing from '../img/white-king.png'; 
import WhiteKnight from '../img/white-knight.png'; 
import WhitePawn from '../img/white-pawn.png'; 
import WhiteQueen from '../img/white-queen.png'; 
import WhiteRook from '../img/white-rook.png'; 

import BlackBishop from '../img/black-bishop.png'; 
import BlackKing from '../img/black-king.png'; 
import BlackKnight from '../img/black-knight.png'; 
import BlackPawn from '../img/black-pawn.png'; 
import BlackQueen from '../img/black-queen.png'; 
import BlackRook from '../img/black-rook.png'; 

export interface PieceProps {
    piece : PieceModel,
    x: number,
    y: number

    onDragStart?(x: number, y : number, e :any) : void
}

export default class Piece extends Component<PieceProps, {}> {

    pieceSrc(color :string, type : string) {
        if (color === "WHITE" && type === "BISHOP") {
            return WhiteBishop;
        } else if (color === "WHITE" && type === "KING") {
            return WhiteKing;
        } else if (color === "WHITE" && type === "KNIGHT") {
            return WhiteKnight;
        } else if (color === "WHITE" && type === "PAWN") {
            return WhitePawn;
        } else if (color === "WHITE" && type === "QUEEN") {
            return WhiteQueen;
        } else if (color === "WHITE" && type === "ROOK") {
            return WhiteRook;
        }  

        if (color === "BLACK" && type === "BISHOP") {
            return BlackBishop;
        } else if (color === "BLACK" && type === "KING") {
            return BlackKing;
        } else if (color === "BLACK" && type === "KNIGHT") {
            return BlackKnight;
        } else if (color === "BLACK" && type === "PAWN") {
            return BlackPawn;
        } else if (color === "BLACK" && type === "QUEEN") {
            return BlackQueen;
        } else if (color === "BLACK" && type === "ROOK") {
            return BlackRook;
        } 
    }

    render() {
        if (!this.props.piece) {
            return <span> </span>;
        }

        const {
            piece: {
                color,
                type
            },
            x,
            y
        } = this.props;

        if (this.props.onDragStart) {
            return (<img 
                className="piece" alt="piece" draggable={true} 
                onDragStart={this.props.onDragStart.bind(this, this.props.x, this.props.y)} 
                src={this.pieceSrc(color,type)}></img>)
        } else {
            return (<img className="piece" alt="piece" src={this.pieceSrc(color,type)}></img>)
        }
    }
}
