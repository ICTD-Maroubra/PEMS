import React from "react";
import ReactDOM from "react-dom";
import {Router, Route, IndexRoute, hashHistory } from "react-router";

import Layout from "./components/Layout";
import Dashboard from "./pages/Dashboard";
import Emergency from "./pages/Emergency";
import Support from "./pages/Support";
import Users from "./pages/Users";

const app = document.getElementById('app');
ReactDOM.render(
  <Router history={hashHistory}>
    <Route path="/" component={Layout}>
      <IndexRoute component={Dashboard}></IndexRoute>
      <Route path="dashboard" component={Dashboard}></Route>
      <Route path="emergency" component={Emergency}></Route>
      <Route path="support" component={Support}></Route>
      <Route path="users" component={Users}></Route>
    </Route>
  </Router>, app
);
