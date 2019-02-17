import React, { Component } from 'react';

import {RouteComponentProps, Redirect} from "react-router";

import { Link} from 'react-router-dom';

export interface TurnSelectorProps {
    turnCount : number, 
    maxTurnCount: number,
    gameId : string
}

export default class TurnSelector extends Component<TurnSelectorProps, {}> {

    render() {
        // that + is necessary..., little dissapointed with TS
        var next = +this.props.turnCount +1
        var prev = +this.props.turnCount -1

        return (
            <div>
                <h3>State {this.props.turnCount} / {this.props.maxTurnCount} </h3>
                <Link to={`/history/${this.props.gameId}/${prev}`}>Back</Link>
                <Link to={`/history/${this.props.gameId}/${next}`}>Forward</Link>
            </div>
        )
    }
}
