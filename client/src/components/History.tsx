import React, { Component } from 'react';

import Board from '../components/Board';
import Graveyard from '../components/Graveyard';
import MoveList from '../components/MoveList';
import TurnSelector from '../components/TurnSelector';
import HistoryModel from '../models/HistoryModel';
import PositionModel from '../models/PositionModel';
import BASE_URL from '../Config';

export interface HistoryProps {
  gameId : string, 
}

export interface HistoryState {
    turnCount: number, 
    history?: HistoryModel

    selected?: PositionModel
    possibleMoves?: PositionModel[]
}

class History extends Component<HistoryProps, HistoryState> {
  constructor(props : HistoryProps) {
    super(props);

    this.state = {
        turnCount: 0
    }
    
    this.loadGameHistory = this.loadGameHistory.bind(this); 
    this.onBoardSquareClick = this.onBoardSquareClick.bind(this);
  }

  loadGameHistory() { 
    var gameId = this.props.gameId;
    var turnCount = this.state.turnCount;

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
    var gameId = this.props.gameId;

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

//   componentDidUpdate(prevProps : HistoryProps) {
//     if (this.state.turnCount != prevProps.turnCount) {
//       this.loadGameHistory()
//     }
//   }

  render() {
    var turnCount = this.state.turnCount;
    var gameId = this.props.gameId;

        if (this.state.history) {

        
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
                    <h3>State {this.state.turnCount} / {this.state.history.maxTurnCount} </h3>
                        <button onClick={() => this.setState({turnCount: this.state.turnCount-1}, () => this.loadGameHistory())}>Back</button>
                        <button onClick={() => this.setState({turnCount: this.state.turnCount+1}, () => this.loadGameHistory())}>Forward</button>
                            
                    <Graveyard pieces={this.state.history.graveyard}></Graveyard>
                    <MoveList moves={this.state.history.moves}></MoveList>
                </div>
            </div>
        } else {
            return <p>Loading</p>;
        }
    }
}

export default History;
