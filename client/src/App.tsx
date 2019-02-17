import React, { Component } from 'react';
import './App.css';
import HistoryView from './views/HistoryView';
import GameListView from './views/GameListView'
import HomeView from './views/HomeView';
import NewGameView from './views/NewGameView'
import { BrowserRouter as Router, Route, Link, HashRouter, Switch } from "react-router-dom";
import PlayGameView from './views/PlayGameView';
import ErrorBoundary from './components/ErrorBoundary';
import StoryLevelView from './views/StoryLevelView';

interface AppProps {
}

interface AppState {
  showNav: boolean
}

class App extends Component<AppProps, AppState> {

  constructor(props : AppProps) {
    super(props)

    this.state = {
      showNav: false
    }

    this.toggleMenu = this.toggleMenu.bind(this);
  }

  toggleMenu(){
    this.setState({ showNav: !this.state.showNav })
  }

  render() {

    const show = (this.state.showNav) ? "show" : "";

    return (
      <div id="container" className="app container">
        <div id="header">
        
          <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <a className="navbar-brand" href="#">Darkchess</a>

            <button className="navbar-toggler" type="button" onClick={ this.toggleMenu }>
              <span className="navbar-toggler-icon"></span>
            </button>

            <div className={"collapse navbar-collapse " + show} id="navbarNav">
              <div className="navbar-nav">
                  <a className="nav-item nav-link" href="/#/">Home</a>
                  <a className="nav-item nav-link" href="/#/play">Play</a>
              </div>
            </div>
          </nav>
        </div>

        <div id="body">
          <Switch>
            <Route path="/" exact component={HomeView} />
            <Route path="/list/" component={GameListView} />
            <Route path="/history/:gameId/:turnCount" component={HistoryView} />
            <Route path="/play" exact component={NewGameView} />
            <Route path="/play/:gameId" component={PlayGameView} />
            <Route path="/story/:level" component={StoryLevelView} />
          </Switch>
          </div>

          <div id="footer">
            <p> <small> Made with &hearts; by <a href="https://c0nrad.io">c0nrad.io</a>. </small></p>
          </div>
        </div>
      );
  }
}

export default App;
