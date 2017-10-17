import React from "react";
import Title from "./Title";

export default class AppTitle extends React.Component {

  render() {
    return (
      <div class="appTitle">
      <img src="../../assets/images/edited/logo.png" alt="PMS" class="logo"></img>
      <Title />
      </div>
    );
  }
}
