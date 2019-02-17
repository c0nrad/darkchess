import React, { Component } from 'react';
import MoveModel from '../models/MoveModel';

export interface MoveListProps {
    moves : MoveModel[]
}

class MoveList extends Component<MoveListProps, {}> {
    render() {

        if (!this.props.moves) {
            return "No Data";
        } 

        var tbody = [
            <tr key='-1'><th>White</th><th>Black</th></tr>
        ];

        var row = [];
        for (var i = 0; i < this.props.moves.length; i++) {
            var move = this.props.moves[i];

            if (move.isHidden) {
                row.push(<td key={i}>Fog</td>);

            } else {
                row.push(<td key={i}>{move.from}->{move.to}</td>);
            }
            
            if (i%2 == 1) {
                tbody.push(<tr key={i}>{row}</tr>);
                row = [];
            }
        }

        if (row.length == 1) {
            row.push(<td key="1000"></td>);
            tbody.push(<tr key={i}>{row}</tr>);
        }

        return (
            <div> 
            <h3>Move List</h3>
            <table className="table">
                <tbody>{tbody}</tbody>
            </table>
            </div>
        )
    }
}

export default MoveList;