import React from "react";
import { Link } from "react-router";

export default class MenuPlaceholder extends React.Component {

  render() {

    return (
      <Link to={this.props.title} activeClassName="test">
      <div class={this.props.className}>
      <img src={this.props.icon} class="menuIcon"></img>
      <h2 class="menuItemTitle">{this.props.title}</h2>
      </div>
      </Link>
    );
  }
}
