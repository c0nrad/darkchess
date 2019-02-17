import React, { Component } from 'react';

import {RouteComponentProps, Redirect} from "react-router";

import { Link} from 'react-router-dom';

export interface TurnSelectorProps {
    turnCount : string, 
    maxTurnCount: number,
    gameId : string
}

export default class TurnSelector extends Component<TurnSelectorProps, {}> {

    render() {
        console.log(typeof this.props.turnCount)
        return (
            <div>
                <h3>State {this.props.turnCount} / {this.props.maxTurnCount} </h3>
                <Link to={`/history/${this.props.gameId}/${parseInt(this.props.turnCount)-1}`}>Back</Link>
                <Link to={`/history/${this.props.gameId}/${parseInt(this.props.turnCount)+1}`}>Forward</Link>
            </div>
        )
    }
}
