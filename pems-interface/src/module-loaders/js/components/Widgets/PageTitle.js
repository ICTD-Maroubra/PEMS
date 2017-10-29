import React from "react";

export default class PageTitle extends React.Component {

  render() {
    return (
      <div class="pageTitle">
      <h1>{this.props.title}</h1>
      </div>
    );
  }
}
