import React from "react";

export default class SingleIconCard extends React.Component {

  render() {
    console.log(this.props);
    return (
<div class="card">
  <div class="card-icon">
    <img src={this.props.icon} />
  </div>
  <div class="card-icon-title">
    {this.props.title}
  </div>
</div>

    );
  }
}
