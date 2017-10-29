import React from "react";

export default class Button extends React.Component {

  render() {
    return (
      <button type="button" class="btn-primary">{this.props.title}</button>
    );
  }
}
