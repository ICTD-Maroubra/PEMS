import React from "react";

export default class Toggle extends React.Component {

  render() {
    return (
      <div class="toggle-container">
      <div class="toggle-label">{this.props.title}</div>
        <label class="switch" for="checkbox">
            <input type="checkbox" id="checkbox" />
            <div class="slider round"></div>
        </label>
      </div>

    );
  }
}
