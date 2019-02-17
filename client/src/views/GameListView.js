import React, { Component } from 'react';
import { Link } from "react-router-dom";
import BASE_URL from '../Config';

class GameListView extends Component {
  constructor(props) {
    super(props);

    this.state = {
      games: []
    }    
    
    this.loadGamesList = this.loadGamesList.bind(this); //add this to your constructor
  }

  loadGamesList() {
    fetch(`${BASE_URL}/api/v1/games`)
      .then( (response) => {
          return response.json()
      })
      .then( (json) => {
        console.log(json);
        this.setState({games: json})
      }
    ); 
  }

  componentDidMount() {
    this.loadGamesList()
  }

  render() {
    return (
      <div>
        {this.state.games.map( (game) =>
          <span><Link key={game.id} to={`/history/${game.id}/0`}>/history/{game.id}/0</Link><br/ ></span>
        )}
      </div>
    )
    ;
  }
}

export default GameListView;
