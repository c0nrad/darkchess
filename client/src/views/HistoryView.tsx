import React, { Component } from 'react';

import Board from '../components/Board';
import Graveyard from '../components/Graveyard';
import MoveList from '../components/MoveList';
import TurnSelector from '../components/TurnSelector';
import HistoryModel from '../models/HistoryModel';
import PositionModel from '../models/PositionModel';
import BASE_URL from '../Config';

export interface HistoryViewProps {
  match: any
}

class HistoryView extends Component<HistoryViewProps, any> {
  constructor(props : HistoryViewProps) {
    super(props);

    this.state = {
        history: {
          board: [],
          graveyard: [],
          moves: [],
          turnCount: 0,
          fogBoardBlack: {},
          fogBoardWhite: {}
        },
    }
    
    this.loadGameHistory = this.loadGameHistory.bind(this); 
    this.onBoardSquareClick = this.onBoardSquareClick.bind(this);
  }

  loadGameHistory() { 
    var gameId = this.props.match.params.gameId;
    var turnCount = this.props.match.params.turnCount;

    fetch(`${BASE_URL}/api/v1/history/${gameId}/${turnCount}`)
      .then( (response) => {
          return response.json()
      })
      .then( (json : HistoryModel) => {
        console.log(json);
        this.setState({history: json})
      }
    );
  }

  onBoardSquareClick(x : number, y : number, e : any) {
    var gameId = this.props.match.params.gameId;

    console.log("You clicked me!!" + x + y)
    var selectedPosition : PositionModel = {x, y}
    
    fetch(`${BASE_URL}/api/v1/games/${gameId}`)
        .then( (response) => {
            return response.json();
        })
        .then( (json: PositionModel[]) => {
            this.setState({
                selected: selectedPosition,
                possibleMoves: json
            })
        });

    return false;
}


  componentDidMount() {
    this.loadGameHistory()
  }

  componentDidUpdate(prevProps : HistoryViewProps) {
    console.log("componentdidupdate" + prevProps.match.params.turnCount + " " + this.props.match.params.turnCount);
    if (this.props.match.params.turnCount != prevProps.match.params.turnCount) {
      this.loadGameHistory()
    }
  }

  render() {
    var turnCount = this.props.match.params.turnCount;
    var gameId = this.props.match.params.gameId;


      return <div className="row">
          <div className="col-8">
            {/* <h3>Master Board</h3>
            <Board pieces={this.state.history.board.pieces}></Board> */}

            <div className="row">
              <div className="col-6">
              <h3>White Perspective</h3>
            <Board onBoardSquareClick={this.onBoardSquareClick} turnCount={turnCount} gameId={gameId} pieces={this.state.history.fogBoardWhite.pieces} perspective={"WHITE"}></Board>
              
              </div>

              <div className="col-6">
              <h3>Black Perspective</h3>
            <Board onBoardSquareClick={this.onBoardSquareClick} turnCount={turnCount} gameId={gameId} pieces={this.state.history.fogBoardBlack.pieces} perspective={"WHITE"}></Board>
              
              </div>

            </div>


          </div>

          <div className="col-4">
            <TurnSelector gameId={this.state.history.gameId} turnCount={turnCount} maxTurnCount={this.state.history.maxTurnCount}></TurnSelector>
            <Graveyard pieces={this.state.history.graveyard}></Graveyard>
            <MoveList moves={this.state.history.moves}></MoveList>
          </div>
      </div>
  }
}

export default HistoryView;
