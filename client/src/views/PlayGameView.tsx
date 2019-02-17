import React, { Component } from 'react';
import Board from '../components/Board';
import GameModel from '../models/GameModel';
import Graveyard from '../components/Graveyard';
import MoveList from '../components/MoveList';
import PositionModel from '../models/PositionModel';
import { Link } from 'react-router-dom';
import BASE_URL from '../Config';
import Game from '../components/Game';

interface PlayGameProps {
    match: any
}

interface PlayGameState { 

    isGameOver: boolean
    isWhiteWinner: boolean
}

export default class PlayGameView extends Component<PlayGameProps, PlayGameState> {

    constructor(props : PlayGameProps) {
        super(props)

        this.state = {
            isGameOver: false,
            isWhiteWinner: false
        }
    
        this.onGameOver = this.onGameOver.bind(this);
    }

    onGameOver(isWhiteWinner: boolean) { 
        console.log("onGameOver")
        this.setState({
            isGameOver: true,
            isWhiteWinner
        })
    }

    render() {
        var gameId = this.props.match.params.gameId;

        if (this.state.isGameOver) {
            var winner = this.state.isWhiteWinner ? <p>White Wins!</p> : <p>Black wins!</p> 
            var historyUrl = `/history/${gameId}/0`
            return (<div>
                <h3>Game Over!!</h3>
                {winner}
                <p>Review the game! <Link to={historyUrl}>Game Analysis</Link></p>
            </div>)
        }       
    
        return (
            <Game gameId={gameId} onGameOver={this.onGameOver}></Game>
        )   
    }
}
