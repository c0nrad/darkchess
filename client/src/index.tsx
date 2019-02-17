import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
 
import $ from 'jquery'; 

import 'bootstrap/dist/css/bootstrap.css';
import 'bootswatch/dist/darkly/bootstrap.css';
import { HashRouter } from 'react-router-dom';

ReactDOM.render(<HashRouter><App /></HashRouter>, document.getElementById('root'));
