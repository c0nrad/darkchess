import React, { Component } from 'react';

import Board from '../components/Board';
import Graveyard from '../components/Graveyard';
import MoveList from '../components/MoveList';
import TurnSelector from '../components/TurnSelector';
import HistoryModel from '../models/HistoryModel';
import PositionModel from '../models/PositionModel';
import BASE_URL from '../Config';
import History from '../components/History';

export interface HistoryViewProps {
  match: any
}

class HistoryView extends Component<HistoryViewProps, any> {
  constructor(props : HistoryViewProps) {
    super(props);

  }

  render() {
    var turnCount = this.props.match.params.turnCount;
    var gameId = this.props.match.params.gameId;

      return <History gameId={gameId}></History>
  }
}

export default HistoryView;
