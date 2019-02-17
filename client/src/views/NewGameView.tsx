import React, { Component } from 'react';

import Board from '../components/Board';
import Graveyard from '../components/Graveyard';
import MoveList from '../components/MoveList';
import TurnSelector from '../components/TurnSelector';
import HistoryModel from '../models/HistoryModel';
import BASE_URL from '../Config';

export interface NewGameProps {
  match: any,
  history: any
}

export interface NewGameState {
    isBotGame?: boolean
    isPlayerWhite?: boolean
    otherPlayerType?: string

    errorMsg?: string
}

export default class NewGameView extends Component<NewGameProps, NewGameState, any> {

    constructor(props : NewGameProps) {
        super(props);

        this.state = {
            otherPlayerType: "RANDBOT"
        }

        this.newGame = this.newGame.bind(this);
        this.onSelectChange = this.onSelectChange.bind(this);
    }

    onSelectChange(color : string, e : any) {
        this.setState({
            otherPlayerType: e.target.value 
        })

        console.log(color, e.target.value);
    }

    newGame() {

        var payload : any = {

        }

        if (this.state.isPlayerWhite) {
            payload['white'] = "HUMAN"
            payload['black'] = this.state.otherPlayerType
        } else {
            payload['black'] = "HUMAN"
            payload['white'] = this.state.otherPlayerType
        }

        fetch(`${BASE_URL}/api/v1/games`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },    
            body: JSON.stringify(payload)
        })
        .then(response => response.json())
        .then( response => {
            this.props.history.push("/play/" + response.id);
        }).catch( (err : Error) => {
            this.setState({
                errorMsg: err.message
            })
        })
    }

    render() {

        var errorMsg = this.state.errorMsg ? <p>{this.state.errorMsg}</p> : <span></span>

        if (typeof this.state.isBotGame == "undefined") {
            return (<div>
                <p>Would you like to:</p>
                    <button className="btn btn-primary" onClick={ () => this.setState({isBotGame: true})}>Play against a bot</button>
                    <button className="btn btn-primary" onClick={ () => this.setState({isBotGame: false})} disabled>Play against another player</button>
                <br></br>
                <small>(Only bot mode is supported currently)</small>
            </div>)
        }

        if (typeof this.state.isPlayerWhite == "undefined") {
            return (<div>
                <p>Would you like to:</p>
                    <button className="btn btn-primary" onClick={ () => this.setState({isPlayerWhite: true})}>Play as white</button>
                    <button className="btn btn-primary" onClick={ () => this.setState({isPlayerWhite: false})} disabled>Play as black</button>
                    <br></br>
                    <small>(Only playing as white is supported currently)</small>
            </div>)
        }


        if (typeof this.state.isPlayerWhite != "undefined" && this.state.isBotGame) {
            return (<div>
                <p>Which bot would you like to play against:</p>

                <div className="form-group">
                <label>Black Player</label>
                <select className="form-control" defaultValue="RANDBOT" onChange={this.onSelectChange.bind(this, 'black')}>
                    <option>RANDBOT</option>
                    <option>KILLBOT</option>
                    <option>PAWNPUSHERBOT</option>
                </select>
            </div>
            <button className="btn btn-primary" onClick={this.newGame}>New Game</button>

            </div>)
        }

        return (<div>
            <h2>New Game</h2> 
            <p>One must be a human</p>
            
            <button onClick={this.newGame}>New Game</button>

            {errorMsg}onSelectChange

            <div className="form-group">
                <label>White Player</label>
                <select className="form-control" defaultValue="HUMAN" onChange={this.onSelectChange.bind(this, 'white')} >
                    <option>HUMAN</option>
                    <option>RANDBOT</option>
                    <option>KILLBOT</option>
                    <option>PAWNPUSHERBOT</option>
                </select>
            </div>


            <div className="form-group">
                <label>Black Player</label>
                <select className="form-control" defaultValue="RANDBOT" onChange={this.onSelectChange.bind(this, 'black')}>
                    <option>HUMAN</option>
                    <option>RANDBOT</option>
                    <option>KILLBOT</option>
                    <option>PAWNPUSHERBOT</option>
                </select>
            </div>
                
             
        </div>)
    }
}

