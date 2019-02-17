import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import assassinsdancepng from "../img/assassinsdance.png"

class HomeView extends Component {
  render() {
    return (
      <div>
      <h2> Darkchess <span style={{'fontSize':'20px'}}> Chess with Fog of War </span> </h2>
      <p> Darkchess is a variation of chess where you can't see your opponents peices unless they are in your line of sight. </p>

      <div className="card">
        <h5 className="card-header">Story Mode</h5>
        <div className="card-body">
          <div className="row">
            <div className="col-6">
              <img style={{width: '100%'}} src={assassinsdancepng}></img>
            </div>

            <div className="col-6">
              <p className="card-text">Defend your village and achieve glory through a set of 5 unique challenges.</p>
              <Link className="btn btn-primary float-right" to="/story/1">Play the story!</Link>
            </div>
          </div>

          
        </div>
      </div>

      <br></br>

      {/* <div className="card">
        <h5 className="card-header">Play against a bot</h5>
        <div className="card-body">
          <p className="card-text">Create a custom game against a set of bots.</p>
          <Link className="btn btn-primary float-right" to="/story/1">Play against Bot!</Link>
        </div>
      </div> */}


            
    </div>
    );
  }
}

export default HomeView;
