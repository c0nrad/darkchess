import React, { Component } from 'react';
import Piece from './Piece'
import PieceModel from '../models/PieceModel'

import './Graveyard.css'

export interface GraveyardProps {
  pieces : PieceModel[]
}

class Graveyard extends Component<GraveyardProps, {}> {
  render() {
    return (
      <div>
        <h3>Graveyard</h3> 
      { this.props.pieces.map( (piece, i) =>
        <span key={i} className="graveyardPiece"><Piece x={-1} y={-1} piece={piece} /></span>
      )}
      </div>
    )
  }
}

export default Graveyard;