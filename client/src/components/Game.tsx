import React, {Component} from 'react'
import GameModel from '../models/GameModel';
import PositionModel from '../models/PositionModel';
import BASE_URL from '../Config';
import { Link } from 'react-router-dom';
import Board from './Board';
import Graveyard from './Graveyard';
import MoveList from './MoveList';

interface GameProps {
    gameId: string
    onGameOver(isWhiteWinner: boolean) : void
    showTitle?: boolean
}

interface GameState {
    game : GameModel

    selected?: PositionModel
    possibleMoves?: PositionModel[]
}


export default class Game extends Component<GameProps, GameState> {

    constructor(props : GameProps) {
        super(props)

        this.state = {
            game: {
                id: "",
                board: {
                    id: "",
                    pieces: []
                },
                graveyard: [],
                moves: [],
                fogmap: [],
                whitePlayer: "",
                blackPlayer: "",
                isWhiteWinner: false, 
                isGameOver: false,
            },
        }
    
        this.onBoardSquareClick = this.onBoardSquareClick.bind(this);

        this.onHandleMove = this.onHandleMove.bind(this);
    }

    loadGame() { 
        var gameId = this.props.gameId;
        
        fetch(`${BASE_URL}/api/v1/games/${gameId}`)
          .then( (response) => {
              return response.json()
          })
          .then( (game : GameModel) => {
            this.setState({game: game})

            if (game.isGameOver) {
                console.log(game);
                this.props.onGameOver(game.isWhiteWinner);
            }
          }
        );
    }

    onBoardSquareClick(x : number, y : number, e : any) {
        var gameId = this.props.gameId;
        var selectedPosition : PositionModel = {x, y}
        
        fetch(`${BASE_URL}/api/v1/games/${gameId}/moves?x=${x}&y=${y}`)
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
        this.loadGame();
    }

    onHandleMove(from : PositionModel, to : PositionModel) {
        console.log("[+] Handle Move", from, to)
        var gameId = this.props.gameId;

        fetch(`${BASE_URL}/api/v1/games/${gameId}/moves`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },    
            body: JSON.stringify({
                from, to
            })
        })
        .then(response => response.json())
        .then( (game : GameModel) => {
            this.setState({
                game: game,
                selected: undefined, 
                possibleMoves: []
            })

            if (game.isGameOver) {
                console.log(game);

                this.props.onGameOver(game.isWhiteWinner);
            }
        })
        .catch( () => {
        });

    }

    render() {
        var gameId = this.props.gameId;

        var title = this.props.showTitle ? 
            <h3>{this.state.game.whitePlayer} vs {this.state.game.blackPlayer}</h3> :
            "";

        return (
            <div>
            {title}            
            <div className="row">
            <div className="col-8">
                <Board
                    gameId={gameId} 
                    onBoardSquareClick={this.onBoardSquareClick} 
                    pieces={this.state.game.board.pieces} 
                    perspective={"WHITE"}

                    selected={this.state.selected}
                    possibleMoves={this.state.possibleMoves}
                    
                    draggableColor={"WHITE"}
                    onHandleMove={this.onHandleMove}

                    fogmap={this.state.game.fogmap}

                    // onDragPieceStart={this.onDragPieceStart}
                    // onDragPieceDrop={this.onDragPieceDrop}
                    ></Board> 
            </div>
    
            <div className="col-4">
                <Graveyard pieces={this.state.game.graveyard}></Graveyard>
                <MoveList moves={this.state.game.moves}></MoveList>
            </div>
        </div> 
        </div>)   
    }
}